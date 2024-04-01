package com.klausapp.scorecalculator.dao;

import java.util.Date;

public record Rating(Integer id, Integer rating, Integer ticketId, Integer ratingCategoryId, Date createdAt) {
}
