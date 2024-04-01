package com.klausapp.scorecalculator.service.ratingcategories;

import java.math.BigDecimal;
import java.util.Map;

public interface RatingCategoriesCacheService {

    Map<Integer, String> getRatingCategoryNames();

    Map<Integer, BigDecimal> getRatingCategoryWeights();

}
