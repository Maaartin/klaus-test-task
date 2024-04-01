package com.klausapp.scorecalculator.service.scorechange;

import java.time.LocalDate;

public interface ScoreChangeCalculator {

    int calculateScoreChange(LocalDate firstPeriodStartDate, LocalDate firstPeriodEndDate,
                             LocalDate secondPeriodStartDate, LocalDate secondPeriodEndDate);

}
