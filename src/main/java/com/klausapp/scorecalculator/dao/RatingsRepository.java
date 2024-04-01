package com.klausapp.scorecalculator.dao;

import java.time.LocalDate;
import java.util.List;

public interface RatingsRepository {

    List<Rating> findRatingsForPeriod(LocalDate periodStart, LocalDate periodEnd);

}
