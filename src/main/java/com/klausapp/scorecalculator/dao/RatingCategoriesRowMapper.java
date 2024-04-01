package com.klausapp.scorecalculator.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingCategoriesRowMapper implements RowMapper<RatingCategory> {

    private static final RatingCategoriesRowMapper INSTANCE = new RatingCategoriesRowMapper();

    public static RatingCategoriesRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public RatingCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RatingCategory(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("weight")
        );
    }

}
