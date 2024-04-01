package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.dao.Rating;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketScoreCalculatorImpl implements TicketScoreCalculator {

    private final CategoryScoreCalculator categoryScoreCalculator;

    public TicketScoreCalculatorImpl(CategoryScoreCalculator categoryScoreCalculator) {
        this.categoryScoreCalculator = categoryScoreCalculator;
    }

    @Override
    public int calculateTicketScore(List<Rating> ratings) {
        List<BigDecimal> scores = new ArrayList<>();
        for (Rating rating : ratings) {
            scores.add(categoryScoreCalculator.calculateCategoryScore(rating.ratingCategoryId(), rating.rating()));
        }
        return (int) Math.round(scores.stream()
                .mapToInt(BigDecimal::intValue)
                .average()
                .orElse(0.0));
    }

}
