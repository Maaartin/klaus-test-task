package com.klausapp.scorecalculator.service.aggregatedcategory;

import com.klausapp.scorecalculator.service.categoryscores.*;
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

        CategoryScoresResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
