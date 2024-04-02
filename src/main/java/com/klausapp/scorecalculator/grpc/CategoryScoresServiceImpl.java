package com.klausapp.scorecalculator.grpc;

import com.klausapp.scorecalculator.service.aggregatedcategory.AggregatedCategoryScoreService;
import com.klausapp.scorecalculator.service.aggregatedcategory.AggregatedCategoryScores;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@GrpcService
public class CategoryScoresServiceImpl extends CategoryScoresServiceGrpc.CategoryScoresServiceImplBase {

    private final AggregatedCategoryScoreService aggregatedCategoryScoreService;

    public CategoryScoresServiceImpl(AggregatedCategoryScoreService aggregatedCategoryScoreService) {
        this.aggregatedCategoryScoreService = aggregatedCategoryScoreService;
    }

    @Override
    public void getCategoryScores(CategoryScoresRequest request, StreamObserver<CategoryScoresResponse> responseObserver) {
        List<AggregatedCategoryScores> aggregatedCategoryScores =
                aggregatedCategoryScoreService.calculateAggregatedCategoryScoresForPeriod(
                        LocalDate.parse(request.getPeriodStartDate()), LocalDate.parse(request.getPeriodEndDate()));

        CategoryScoresResponse response = buildResponse(aggregatedCategoryScores);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private static CategoryScoresResponse buildResponse(List<AggregatedCategoryScores> aggregatedCategoryScores) {
        CategoryScoresResponse.Builder responseBuilder = CategoryScoresResponse.newBuilder();
        for (AggregatedCategoryScores scores : aggregatedCategoryScores) {
            CategoryScores.Builder categoryScoresBuilder = CategoryScores.newBuilder();
            categoryScoresBuilder
                    .setCategoryName(scores.categoryName())
                    .setNumberOfRatings(scores.numberOfRatings());
            List<TimeUnitScore> timeUnitScores = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : scores.scoreByTimeUnit().entrySet()) {
                timeUnitScores.add(TimeUnitScore.newBuilder()
                        .setTimeUnit(entry.getKey())
                        .setScore(entry.getValue())
                        .build());
            }
            categoryScoresBuilder.addAllTimeUnitScores(timeUnitScores);
            responseBuilder.addCategoryScores(categoryScoresBuilder.build());
        }
        return responseBuilder.build();
    }

}
