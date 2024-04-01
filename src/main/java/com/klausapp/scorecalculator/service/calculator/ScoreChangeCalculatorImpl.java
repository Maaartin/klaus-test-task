package com.klausapp.scorecalculator.service.calculator;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class ScoreChangeCalculatorImpl implements ScoreChangeCalculator {

    private final OverallScoreCalculator overallScoreCalculator;

    public ScoreChangeCalculatorImpl(OverallScoreCalculator overallScoreCalculator) {
        this.overallScoreCalculator = overallScoreCalculator;
    }

    @Override
    public int calculateScoreChange(LocalDate firstPeriodStartDate, LocalDate firstPeriodEndDate,
                                    LocalDate secondPeriodStartDate, LocalDate secondPeriodEndDate) {
        int firstPeriodScore = overallScoreCalculator.calculateOverallScoreForPeriod(firstPeriodStartDate, firstPeriodEndDate);
        int secondPeriodScore = overallScoreCalculator.calculateOverallScoreForPeriod(secondPeriodStartDate, secondPeriodEndDate);
        return calculateChange(BigDecimal.valueOf(firstPeriodScore), BigDecimal.valueOf(secondPeriodScore))
                .intValue();
    }

    private static BigDecimal calculateChange(BigDecimal first, BigDecimal second) {
        return second.subtract(first)
                .divide(first, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

}
