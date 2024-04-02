package com.klausapp.scorecalculator.service.aggregatedcategory;

import java.util.Map;

public record AggregatedCategoryScores(String categoryName, Integer numberOfRatings, Map<String, Integer> scoreByTimeUnit) {
}
