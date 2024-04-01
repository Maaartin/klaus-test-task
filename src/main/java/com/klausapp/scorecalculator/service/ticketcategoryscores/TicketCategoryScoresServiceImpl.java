package com.klausapp.scorecalculator.service.ticketcategoryscores;

import com.klausapp.scorecalculator.dao.Rating;
import com.klausapp.scorecalculator.dao.RatingsRepository;
import com.klausapp.scorecalculator.service.calculator.TicketCategoryScoreCalculator;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@GrpcService
public class TicketCategoryScoresServiceImpl extends TicketCategoryScoresServiceGrpc.TicketCategoryScoresServiceImplBase {

    private final RatingsRepository ratingsRepository;
    private final TicketCategoryScoreCalculator ticketCategoryScoreCalculator;

    public TicketCategoryScoresServiceImpl(RatingsRepository ratingsRepository, TicketCategoryScoreCalculator ticketCategoryScoreCalculator) {
        this.ratingsRepository = ratingsRepository;
        this.ticketCategoryScoreCalculator = ticketCategoryScoreCalculator;
    }

    @Override
    public void getTicketCategoryScores(TicketCategoryScoresRequest request, StreamObserver<TicketCategoryScoresResponse> responseObserver) {
        //TODO Duplicate code in OverallScoreCalculatorImpl
        List<Rating> ratings = ratingsRepository.findRatingsForPeriod(LocalDate.parse(request.getPeriodStartDate()), LocalDate.parse(request.getPeriodEndDate()).plusDays(1));
        Map<Integer, List<Rating>> ratingsByTicket = ratings.stream()
                .collect(Collectors.groupingBy(Rating::ticketId));

        TicketCategoryScoresResponse.Builder responseBuilder = TicketCategoryScoresResponse.newBuilder();
        for (Map.Entry<Integer, List<Rating>> entry : ratingsByTicket.entrySet()) {
            Map<Integer, BigDecimal> ticketScoresByCategory = ticketCategoryScoreCalculator.getTicketScoresByCategory(entry.getValue());

            List<CategoryScore> categoryScores = new ArrayList<>();
            for (Map.Entry<Integer, BigDecimal> score : ticketScoresByCategory.entrySet()) {
                CategoryScore categoryScore = CategoryScore.newBuilder()
                        .setCategoryId(score.getKey())
                        .setCategoryScore(score.getValue().intValue())
                        .build();
                categoryScores.add(categoryScore);
            }

            responseBuilder.addTicketCategoryScores(
                    TicketCategoryScore.newBuilder()
                            .setTicketId(entry.getKey())
                            .addAllCategoryScores(categoryScores)
                            .build());
        }
        TicketCategoryScoresResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
