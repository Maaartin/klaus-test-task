package com.klausapp.scorecalculator.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingsRowMapper implements RowMapper<Rating> {

    private static final RatingsRowMapper INSTANCE = new RatingsRowMapper();

    public static RatingsRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Rating(
                rs.getLong("id"),
                rs.getInt("rating"),
                rs.getLong("ticket_id"),
                rs.getInt("rating_category_id"));
    }

}
