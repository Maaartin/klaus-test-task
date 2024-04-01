package com.klausapp.scorecalculator.service.calculator;

import java.math.BigDecimal;

public interface CategoryScoreCalculator {

    BigDecimal calculateCategoryScore(Integer categoryId, Integer rating);

}
