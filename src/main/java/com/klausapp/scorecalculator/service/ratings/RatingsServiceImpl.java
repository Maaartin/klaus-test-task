package com.klausapp.scorecalculator.service.ratings;

import com.klausapp.scorecalculator.dao.Rating;
import com.klausapp.scorecalculator.dao.RatingsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingsServiceImpl implements RatingsService {

    private final RatingsRepository ratingsRepository;

    public RatingsServiceImpl(RatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
    }

    @Override
    public Map<Integer, List<Rating>> findRatingsByTicketIdInPeriod(LocalDate periodStart, LocalDate periodEnd) {
        List<Rating> ratings = ratingsRepository.findRatingsForPeriod(periodStart, periodEnd.plusDays(1));
        return ratings.stream()
                .collect(Collectors.groupingBy(Rating::ticketId));
    }

}
