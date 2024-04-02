package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.dao.Rating;

import java.util.List;

public interface AggregatedCategoryScoreCalculator {

    int calculateAggregatedCategoryScore(Integer categoryId, List<Rating> ratings);

}
