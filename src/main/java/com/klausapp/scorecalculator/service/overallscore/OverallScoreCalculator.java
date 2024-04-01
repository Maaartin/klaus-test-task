package com.klausapp.scorecalculator.service.overallscore;

import java.time.LocalDate;

public interface OverallScoreCalculator {

    int calculateOverallScoreForPeriod(LocalDate periodStart, LocalDate periodEnd);

}
