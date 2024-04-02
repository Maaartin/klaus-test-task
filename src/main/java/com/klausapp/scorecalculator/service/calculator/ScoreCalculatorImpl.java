package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.service.ratingcategories.RatingCategoriesCacheService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ScoreCalculatorImpl implements ScoreCalculator {

    private static final BigDecimal MAX_RATING = new BigDecimal(5);
    private static final BigDecimal HUNDRED = new BigDecimal(100);

    private final RatingCategoriesCacheService ratingCategoriesCacheService;

    public ScoreCalculatorImpl(RatingCategoriesCacheService ratingCategoriesCacheService) {
        this.ratingCategoriesCacheService = ratingCategoriesCacheService;
    }

    @Override
    public BigDecimal calculateScore(Integer categoryId, Integer rating) {
        BigDecimal weight = ratingCategoriesCacheService.getRatingCategoryWeights().get(categoryId);
        if (BigDecimal.ZERO.equals(weight)) return BigDecimal.ZERO;
        return calculateScore(rating, weight);
    }

    private static BigDecimal calculateScore(Integer rating, BigDecimal weight) {
        BigDecimal score = new BigDecimal(rating)
                .multiply(weight)
                .divide(MAX_RATING, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return HUNDRED.min(score);
    }

}
