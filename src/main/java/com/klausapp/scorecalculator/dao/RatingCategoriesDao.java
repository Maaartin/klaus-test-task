package com.klausapp.scorecalculator.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingCategoriesDao implements RatingCategoriesRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RatingCategoriesDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<RatingCategory> findAllRatingCategories() {
        String sql = """
                SELECT id, name, weight FROM rating_categories
                """;
        return namedParameterJdbcTemplate.query(sql, RatingCategoriesRowMapper.getInstance());
    }

}
