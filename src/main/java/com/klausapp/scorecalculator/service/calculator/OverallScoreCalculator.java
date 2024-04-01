package com.klausapp.scorecalculator.service.calculator;

import java.time.LocalDate;

public interface OverallScoreCalculator {

    int calculateOverallScoreForPeriod(LocalDate periodStart, LocalDate periodEnd);

}
