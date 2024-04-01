package com.klausapp.scorecalculator.service.calculator;

import java.time.LocalDate;

public interface ScoreChangeCalculator {

    int calculateScoreChange(LocalDate firstPeriodStartDate, LocalDate firstPeriodEndDate,
                             LocalDate secondPeriodStartDate, LocalDate secondPeriodEndDate);

}
