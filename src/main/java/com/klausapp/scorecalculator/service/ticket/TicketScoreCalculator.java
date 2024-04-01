package com.klausapp.scorecalculator.service.ticket;

import com.klausapp.scorecalculator.dao.Rating;

import java.util.List;

public interface TicketScoreCalculator {

    int calculateTicketScore(List<Rating> ratings);

}
