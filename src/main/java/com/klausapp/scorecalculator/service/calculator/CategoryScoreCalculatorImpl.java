package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.service.ratingcategories.RatingCategoriesCacheService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CategoryScoreCalculatorImpl implements CategoryScoreCalculator {

    private final RatingCategoriesCacheService ratingCategoriesCacheService;

    public CategoryScoreCalculatorImpl(RatingCategoriesCacheService ratingCategoriesCacheService) {
        this.ratingCategoriesCacheService = ratingCategoriesCacheService;
    }

    @Override
    public BigDecimal calculateCategoryScore(Integer categoryId, Integer rating) {
        BigDecimal weight = ratingCategoriesCacheService.getRatingCategoryWeights().get(categoryId);
        if (BigDecimal.ZERO.equals(weight)) return BigDecimal.ZERO;
        return calculateScore(rating, weight);
    }

    private static BigDecimal calculateScore(Integer rating, BigDecimal weight) {
        BigDecimal maxRating = BigDecimal.valueOf(5).multiply(weight);
        return new BigDecimal(rating)
                .multiply(weight)
                .divide(maxRating, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

}
