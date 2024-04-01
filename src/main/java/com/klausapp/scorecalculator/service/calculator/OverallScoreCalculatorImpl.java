package com.klausapp.scorecalculator.service.calculator;

import com.klausapp.scorecalculator.dao.Rating;
import com.klausapp.scorecalculator.dao.RatingsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OverallScoreCalculatorImpl implements OverallScoreCalculator {

    private final RatingsRepository ratingsRepository;
    private final TicketScoreCalculator ticketScoreCalculator;

    public OverallScoreCalculatorImpl(RatingsRepository ratingsRepository,
                                      TicketScoreCalculator ticketScoreCalculator) {
        this.ratingsRepository = ratingsRepository;
        this.ticketScoreCalculator = ticketScoreCalculator;
    }

    @Override
    public int calculateOverallScoreForPeriod(LocalDate periodStart, LocalDate periodEnd) {
        List<Rating> ratings = ratingsRepository.findRatingsForPeriod(periodStart, periodEnd.plusDays(1));
        Map<Integer, List<Rating>> ratingsByTicket = ratings.stream()
                .collect(Collectors.groupingBy(Rating::ticketId));
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
