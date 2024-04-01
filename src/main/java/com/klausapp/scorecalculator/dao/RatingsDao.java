package com.klausapp.scorecalculator.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RatingsDao implements RatingsRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RatingsDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Rating> findRatingsForPeriod(LocalDate periodStart, LocalDate periodEnd) {
        String sql = """
                SELECT id, rating, ticket_id, rating_category_id, created_at
                FROM ratings
                WHERE created_at >= :periodStart
                  AND created_at < :periodEnd
                ORDER BY created_at
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("periodStart", periodStart);
        params.put("periodEnd", periodEnd);

        return namedParameterJdbcTemplate.query(sql, params, RatingsRowMapper.getInstance());
    }

}
