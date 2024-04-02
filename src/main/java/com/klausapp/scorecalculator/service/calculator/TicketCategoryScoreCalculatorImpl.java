package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.dao.Rating;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketCategoryScoreCalculatorImpl implements TicketCategoryScoreCalculator {

    private final ScoreCalculator scoreCalculator;

    public TicketCategoryScoreCalculatorImpl(ScoreCalculator scoreCalculator) {
        this.scoreCalculator = scoreCalculator;
    }

    @Override
    public Map<Integer, BigDecimal> getTicketScoresByCategory(List<Rating> ticketRatings) {
        Map<Integer, BigDecimal> scoresByCategory = new HashMap<>();
        for (Rating rating : ticketRatings) {
            scoresByCategory.put(rating.ratingCategoryId(),
                    scoreCalculator.calculateScore(rating.ratingCategoryId(), rating.rating()));
        }
        return scoresByCategory;
    }

}
