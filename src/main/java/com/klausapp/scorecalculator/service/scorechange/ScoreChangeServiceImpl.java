package com.klausapp.scorecalculator.service.scorechange;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDate;

@GrpcService
public class ScoreChangeServiceImpl extends ScoreChangeServiceGrpc.ScoreChangeServiceImplBase {

    private final ScoreChangeCalculator scoreChangeCalculator;

    public ScoreChangeServiceImpl(ScoreChangeCalculator scoreChangeCalculator) {
        this.scoreChangeCalculator = scoreChangeCalculator;
    }

    @Override
    public void getScoreChange(ScoreChangeRequest request, StreamObserver<ScoreChangeResponse> responseObserver) {
        int scoreChangePercentage = scoreChangeCalculator.calculateScoreChange(
                LocalDate.parse(request.getFirstPeriodStartDate()),
                LocalDate.parse(request.getFirstPeriodEndDate()),
                LocalDate.parse(request.getSecondPeriodStartDate()),
                LocalDate.parse(request.getSecondPeriodEndDate()));
        ScoreChangeResponse response = ScoreChangeResponse.newBuilder()
                .setScoreChangePercentage(scoreChangePercentage)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
