package com.klausapp.scorecalculator.dao;

import java.math.BigDecimal;

public record RatingCategory(Integer id, String name, BigDecimal weight) {
}
