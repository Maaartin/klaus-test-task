package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.dao.Rating;
import com.klausapp.scorecalculator.service.ratings.RatingsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OverallScoreCalculatorImpl implements OverallScoreCalculator {

    private final RatingsService ratingsService;
    private final TicketScoreCalculator ticketScoreCalculator;

    public OverallScoreCalculatorImpl(RatingsService ratingsService, TicketScoreCalculator ticketScoreCalculator) {
        this.ratingsService = ratingsService;
        this.ticketScoreCalculator = ticketScoreCalculator;
    }

    @Override
    public int calculateOverallScoreForPeriod(LocalDate periodStart, LocalDate periodEnd) {
        Map<Integer, List<Rating>> ratingsByTicket = ratingsService.findRatingsByTicketIdInPeriod(periodStart, periodEnd);
        return calculateOverallScore(ratingsByTicket);
    }

    private int calculateOverallScore(Map<Integer, List<Rating>> ratingsByTicket) {
        List<Integer> ticketScores = new ArrayList<>();
        for (Map.Entry<Integer, List<Rating>> entry : ratingsByTicket.entrySet()) {
            ticketScores.add(ticketScoreCalculator.calculateTicketScore(entry.getValue()));
        }
        return (int) Math.round(ticketScores.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0));
    }

}
