package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.dao.Rating;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class TicketScoreCalculatorImpl implements TicketScoreCalculator {

    private final TicketCategoryScoreCalculator ticketCategoryScoreCalculator;

    public TicketScoreCalculatorImpl(TicketCategoryScoreCalculator ticketCategoryScoreCalculator) {
        this.ticketCategoryScoreCalculator = ticketCategoryScoreCalculator;
    }

    @Override
    public int calculateTicketScore(List<Rating> ticketRatings) {
        Map<Integer, BigDecimal> ticketScoresByCategory = ticketCategoryScoreCalculator.getTicketScoresByCategory(ticketRatings);
        return (int) Math.round(ticketScoresByCategory.values().stream()
                .mapToInt(BigDecimal::intValue)
                .average()
                .orElse(0.0));
    }

}
