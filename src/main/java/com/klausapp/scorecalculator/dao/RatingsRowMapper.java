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
                rs.getInt("id"),
                rs.getInt("rating"),
                rs.getInt("ticket_id"),
                rs.getInt("rating_category_id"),
                rs.getDate("created_at"));
    }

}
