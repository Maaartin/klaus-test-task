package com.klausapp.scorecalculator.dao;

public record Rating(Long id, Integer rating, Long ticketId, Integer ratingCategoryId) {
}
