package com.klausapp.scorecalculator.service.ticket;

import com.klausapp.scorecalculator.dao.Rating;
import com.klausapp.scorecalculator.service.ratingcategories.RatingCategoriesCacheService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketScoreCalculatorImpl implements TicketScoreCalculator {

    private final RatingCategoriesCacheService ratingCategoriesCacheService;

    public TicketScoreCalculatorImpl(RatingCategoriesCacheService ratingCategoriesCacheService) {
        this.ratingCategoriesCacheService = ratingCategoriesCacheService;
    }

    @Override
    public int calculateTicketScore(List<Rating> ratings) {
        List<BigDecimal> scores = new ArrayList<>();
        for (Rating rating : ratings) {
            scores.add(calculateCategoryRatingScore(rating));
        }
        return (int) Math.round(scores.stream()
                .mapToInt(BigDecimal::intValue)
                .average()
                .orElse(0.0));
    }

    private BigDecimal calculateCategoryRatingScore(Rating rating) {
        BigDecimal weight = ratingCategoriesCacheService.getRatingCategoryWeights().get(rating.ratingCategoryId());
        if (BigDecimal.ZERO.equals(weight)) return BigDecimal.ZERO;
        return calculateScore(rating.rating(), weight);
    }

    private static BigDecimal calculateScore(Integer rating, BigDecimal weight) {
        BigDecimal maxRating = BigDecimal.valueOf(5).multiply(weight);
        return new BigDecimal(rating)
                .multiply(weight)
                .divide(maxRating, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

}
