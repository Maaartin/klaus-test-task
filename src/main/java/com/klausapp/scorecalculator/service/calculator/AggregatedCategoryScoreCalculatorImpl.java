package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.dao.Rating;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AggregatedCategoryScoreCalculatorImpl implements AggregatedCategoryScoreCalculator {

    private final ScoreCalculator scoreCalculator;

    public AggregatedCategoryScoreCalculatorImpl(ScoreCalculator scoreCalculator) {
        this.scoreCalculator = scoreCalculator;
    }

    @Override
    public int calculateAggregatedCategoryScore(Integer categoryId, List<Rating> ratings) {
        List<Integer> scores = new ArrayList<>();
        for (Rating rating : ratings) {
            scores.add(scoreCalculator.calculateScore(categoryId, rating.rating()).intValue());
        }
        return (int) Math.round(scores.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0));
    }

}
