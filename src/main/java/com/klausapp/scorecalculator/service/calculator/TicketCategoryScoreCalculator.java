package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.dao.Rating;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TicketCategoryScoreCalculator {

    Map<Integer, BigDecimal> calculateTicketCategoryScoresByCategoryId(List<Rating> ticketRatings);

}
