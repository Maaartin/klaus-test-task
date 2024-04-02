package com.klausapp.scorecalculator.service.aggregatedcategory;

import com.klausapp.scorecalculator.dao.Rating;
import com.klausapp.scorecalculator.service.calculator.AggregatedCategoryScoreCalculator;
import com.klausapp.scorecalculator.service.ratingcategories.RatingCategoriesCacheService;
import com.klausapp.scorecalculator.service.ratings.RatingsService;
import com.klausapp.scorecalculator.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregatedCategoryScoreServiceImpl implements AggregatedCategoryScoreService {

    private final AggregatedCategoryScoreCalculator aggregatedCategoryScoreCalculator;
    private final RatingCategoriesCacheService ratingCategoriesCacheService;
    private final RatingsService ratingsService;

    public AggregatedCategoryScoreServiceImpl(AggregatedCategoryScoreCalculator aggregatedCategoryScoreCalculator,
                                              RatingCategoriesCacheService ratingCategoriesCacheService,
                                              RatingsService ratingsService) {
        this.aggregatedCategoryScoreCalculator = aggregatedCategoryScoreCalculator;
        this.ratingCategoriesCacheService = ratingCategoriesCacheService;
        this.ratingsService = ratingsService;
    }

    @Override
    public List<AggregatedCategoryScores> calculateAggregatedCategoryScoresForPeriod(LocalDate periodStart, LocalDate periodEnd) {
        List<AggregatedCategoryScores> aggregatedCategoryScores = new ArrayList<>();
        final boolean weeklyAggregation = DateUtil.getMonthsBetween(periodStart, periodEnd) > 0;
        Map<Integer, List<Rating>> ratingsByCategoryId = ratingsService.findRatingsByCategoryIdInPeriod(periodStart, periodEnd);
        for (Map.Entry<Integer, List<Rating>> entry : ratingsByCategoryId.entrySet()) {
            Integer categoryId = entry.getKey();
            List<Rating> categoryRatings = entry.getValue();
            String categoryName = ratingCategoriesCacheService.getRatingCategoryNames().get(categoryId);

            Map<String, Integer> scoreByDate = getScoresByDates(periodStart, periodEnd, categoryId, categoryRatings);
            if (weeklyAggregation) {
                Map<String, Integer> scoreByWeek = getScoresByWeeks(periodStart, periodEnd, scoreByDate);
                aggregatedCategoryScores.add(new AggregatedCategoryScores(categoryName, categoryRatings.size(), scoreByWeek));
            } else {
                aggregatedCategoryScores.add(new AggregatedCategoryScores(categoryName, categoryRatings.size(), scoreByDate));
            }

        }
        return aggregatedCategoryScores;
    }

    private Map<String, Integer> getScoresByWeeks(LocalDate periodStart, LocalDate periodEnd, Map<String, Integer> scoreByDate) {
        Map<Integer, List<Integer>> dailyScoresByWeekNumber = getDailyScoresByWeekNumber(scoreByDate);
        Map<String, Integer> scoreByWeek = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : dailyScoresByWeekNumber.entrySet()) {
            Integer weekNumber = entry.getKey();
            List<Integer> dailyScores = entry.getValue();
            int weeklyAverage = (int) Math.round(dailyScores.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0));
            scoreByWeek.put("Week " + weekNumber, weeklyAverage);
        }
        return scoreByWeek;
    }

    private Map<String, Integer> getScoresByDates(LocalDate periodStart, LocalDate periodEnd, Integer categoryId, List<Rating> ratings) {
        Map<String, Integer> scoreByDate = new HashMap<>();
        Map<String, List<Rating>> ratingsByDate = ratings.stream()
                .collect(Collectors.groupingBy(r -> DateUtil.convertLocalDateTimeToDateString(r.createdAt())));
        LocalDate currentDate = periodStart;
        while (!currentDate.isAfter(periodEnd)) {
            List<Rating> ratingsOnDate = ratingsByDate.get(currentDate.toString());
            if (ratingsOnDate == null || ratingsOnDate.isEmpty()) {
                scoreByDate.put(currentDate.toString(), -1);
            } else {
                int aggregatedScore = aggregatedCategoryScoreCalculator.calculateAggregatedCategoryScore(categoryId, ratingsOnDate);
                scoreByDate.put(currentDate.toString(), aggregatedScore);
            }
            currentDate = currentDate.plusDays(1);
        }
        return scoreByDate;
    }

    private static Map<Integer, List<Integer>> getDailyScoresByWeekNumber(Map<String, Integer> scoreByDate) {
        Map<Integer, List<Integer>> dailyScoresByWeekNumber = new HashMap<>();
        for (Map.Entry<String, Integer> entry : scoreByDate.entrySet()) {
            String date = entry.getKey();
            Integer score = entry.getValue();
            int weekNumber = LocalDate.parse(date).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            List<Integer> scores = dailyScoresByWeekNumber.get(weekNumber);
            if (scores == null) {
                scores = new ArrayList<>();
            }
            scores.add(score);
            dailyScoresByWeekNumber.put(weekNumber, scores);
        }
        return dailyScoresByWeekNumber;
    }

}
