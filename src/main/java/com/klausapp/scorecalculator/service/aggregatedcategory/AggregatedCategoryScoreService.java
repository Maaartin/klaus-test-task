package com.klausapp.scorecalculator.service.aggregatedcategory;

import java.time.LocalDate;
import java.util.List;

public interface AggregatedCategoryScoreService {

    List<AggregatedCategoryScores> calculateAggregatedCategoryScoresForPeriod(LocalDate periodStart, LocalDate periodEnd);

}
