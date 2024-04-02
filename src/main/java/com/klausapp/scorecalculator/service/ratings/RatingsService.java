package com.klausapp.scorecalculator.service.ratings;

import com.klausapp.scorecalculator.dao.Rating;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RatingsService {

    Map<Integer, List<Rating>> findRatingsByTicketIdInPeriod(LocalDate periodStart, LocalDate periodEnd);

    Map<Integer, List<Rating>> findRatingsByCategoryIdInPeriod(LocalDate periodStart, LocalDate periodEnd);

}
