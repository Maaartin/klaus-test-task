package com.klausapp.scorecalculator.service.calculator;

import java.math.BigDecimal;

public interface ScoreCalculator {

    BigDecimal calculateScore(Integer categoryId, Integer rating);

}
