package com.klausapp.scorecalculator.service.overallscore;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDate;

@GrpcService
public class OverallScoreServiceImpl extends OverallScoreServiceGrpc.OverallScoreServiceImplBase {

    private final OverallScoreCalculator overallScoreCalculator;

    public OverallScoreServiceImpl(OverallScoreCalculator overallScoreCalculator) {
        this.overallScoreCalculator = overallScoreCalculator;
    }

    @Override
    public void getOverallScore(OverallScoreRequest request, StreamObserver<OverallScoreResponse> responseObserver) {
        int score = overallScoreCalculator
                .calculateOverallScoreForPeriod(
                        LocalDate.parse(request.getPeriodStartDate()), LocalDate.parse(request.getPeriodEndDate()));
        OverallScoreResponse response = OverallScoreResponse.newBuilder().setScorePercentage(score).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
