package com.klausapp.scorecalculator.grpc;

import com.klausapp.scorecalculator.dao.Rating;
import com.klausapp.scorecalculator.service.calculator.TicketCategoryScoreCalculator;
import com.klausapp.scorecalculator.service.ratings.RatingsService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@GrpcService
public class TicketCategoryScoresServiceImpl extends TicketCategoryScoresServiceGrpc.TicketCategoryScoresServiceImplBase {

    private final RatingsService ratingsService;
    private final TicketCategoryScoreCalculator ticketCategoryScoreCalculator;

    public TicketCategoryScoresServiceImpl(RatingsService ratingsService, TicketCategoryScoreCalculator ticketCategoryScoreCalculator) {
        this.ratingsService = ratingsService;
        this.ticketCategoryScoreCalculator = ticketCategoryScoreCalculator;
    }

    @Override
    public void getTicketCategoryScores(TicketCategoryScoresRequest request, StreamObserver<TicketCategoryScoresResponse> responseObserver) {
        Map<Integer, List<Rating>> ratingsByTicket = ratingsService.findRatingsByTicketIdInPeriod(LocalDate.parse(request.getPeriodStartDate()), LocalDate.parse(request.getPeriodEndDate()));

        TicketCategoryScoresResponse.Builder responseBuilder = TicketCategoryScoresResponse.newBuilder();
        for (Map.Entry<Integer, List<Rating>> entry : ratingsByTicket.entrySet()) {
            Map<Integer, BigDecimal> ticketScoresByCategoryId = ticketCategoryScoreCalculator.calculateTicketCategoryScoresByCategoryId(entry.getValue());

            List<CategoryScore> categoryScores = new ArrayList<>();
            for (Map.Entry<Integer, BigDecimal> score : ticketScoresByCategoryId.entrySet()) {
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
