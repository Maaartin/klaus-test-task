package com.klausapp.scorecalculator.dao;

import java.math.BigDecimal;

public record RatingCategory(Long id, String name, BigDecimal weight) {
}
