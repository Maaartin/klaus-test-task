package com.klausapp.scorecalculator.service.ratingcategories;

import com.klausapp.scorecalculator.dao.RatingCategoriesRepository;
import com.klausapp.scorecalculator.dao.RatingCategory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingCategoriesCacheServiceImpl implements RatingCategoriesCacheService {

    private final RatingCategoriesRepository ratingCategoriesRepository;
    private final Map<Long, String> categoryNameById;
    private final Map<Long, BigDecimal> categoryWeightById;

    public RatingCategoriesCacheServiceImpl(RatingCategoriesRepository ratingCategoriesRepository) {
        this.ratingCategoriesRepository = ratingCategoriesRepository;
        this.categoryNameById = new HashMap<>();
        this.categoryWeightById = new HashMap<>();
    }

    @PostConstruct
    private void cacheRatingCategories() {
        List<RatingCategory> allRatingCategories = ratingCategoriesRepository.findAllRatingCategories();
        allRatingCategories.forEach(rc -> {
            categoryNameById.put(rc.id(), rc.name());
            categoryWeightById.put(rc.id(), rc.weight());
        });
    }

    @Override
    public Map<Long, String> getRatingCategoryNames() {
        return categoryNameById;
    }

    @Override
    public Map<Long, BigDecimal> getRatingCategoryWeights() {
        return categoryWeightById;
    }
}
