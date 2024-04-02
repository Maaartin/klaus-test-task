package com.klausapp.scorecalculator.dao;

public record Rating(Integer id, Integer rating, Integer ticketId, Integer ratingCategoryId, java.time.LocalDateTime createdAt) {
}
