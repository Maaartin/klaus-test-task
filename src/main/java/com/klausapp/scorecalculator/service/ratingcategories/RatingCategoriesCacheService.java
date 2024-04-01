package com.klausapp.scorecalculator.service.ratingcategories;

import java.math.BigDecimal;
import java.util.Map;

public interface RatingCategoriesCacheService {

    Map<Long, String> getRatingCategoryNames();

    Map<Long, BigDecimal> getRatingCategoryWeights();

}
