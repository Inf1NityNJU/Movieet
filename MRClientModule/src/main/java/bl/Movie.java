package bl;

import data.ReviewDataFromJsonServiceImpl;
import dataservice.ReviewDataService;
import datastub.ReviewDataServiceStub;
import po.MoviePO;
import po.ReviewPO;
import util.LimitedHashMap;
import vo.MovieVO;
import vo.ReviewCountVO;
import vo.ScoreDistributionVO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by vivian on 2017/3/4.
 */
public class Movie {
    //    private ReviewDataService reviewDataService = new ReviewDataFromJsonServiceImpl();
    private ReviewDataService reviewDataService = new ReviewDataFromJsonServiceImpl();
    private List<ReviewPO> reviewPOList;
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);
    private VOGetter voGetter;


    /**
     * 根据 movieId 查找电影
     *
     * @param movieId 电影ID
     * @return MovieVO
     */
    public MovieVO findMovieById(String movieId) {
        MoviePO moviePO = reviewDataService.findMovieByMovieId(movieId);
        getReviewPOList(movieId);

        if (reviewPOList.size() == 0) {
            return null;
        }

        double scoreSum = 0;
        double scoreSquareSum = 0;

        //计算评分平方和、评分均值
        for (int i = 0; i < reviewPOList.size(); i++) {
            scoreSum = scoreSum + reviewPOList.get(i).getScore();
            scoreSquareSum = scoreSquareSum + (double) Math.pow(reviewPOList.get(i).getScore(), 2);
        }
        double averageScore = scoreSum / reviewPOList.size();

        //计算评分方差
        double variance = (scoreSquareSum - (double) (Math.pow(scoreSum, 2) / reviewPOList.size())) / reviewPOList.size();

        //第一条评论日期和最后一条评论日期
        TreeSet<LocalDate> dates = new TreeSet<>();
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            dates.add(date);
        }
        String firstReviewDate = dates.first().toString();
        String lastReviewDate = dates.last().toString();

        return new MovieVO(movieId, moviePO.getName(), reviewPOList.size(), averageScore, variance, firstReviewDate, lastReviewDate);
    }

    /**
     * 根据电影 movieId 查找评价分布
     *
     * @param movieId 电影ID
     * @return ScoreDistributionVO
     */
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        getReviewPOList(movieId);

        int[] reviewAmounts = {0, 0, 0, 0, 0};
        for (int i = 0; i < reviewPOList.size(); i++) {
            reviewAmounts[(int) Math.floor(reviewPOList.get(i).getScore()) - 1]++;
        }
        ScoreDistributionVO scoreDistributionVO = new ScoreDistributionVO(reviewPOList.size(), reviewAmounts);
        return scoreDistributionVO;
    }


    /**
     * 根据电影 movieId 查找每年评论数量
     *
     * @param movieId 电影ID
     * @return ReviewCountYearVO
     */
    public ReviewCountVO[] findYearCountByMovieId(String movieId) {
        reviewPOList = getReviewPOList(movieId);
        DateChecker dateChecker = new YearDateChecker();
        DateUnitedHandler dateUnitedHandler = new YearDateUnitedHandler();
        DateFormatter dateFormatter = new YearDateFormatter();
        voGetter = new VOGetter(dateChecker, dateUnitedHandler, dateFormatter);
        return voGetter.getVO(reviewPOList, dateChecker, dateUnitedHandler, dateFormatter);
    }


    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param movieId    电影ID
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return ReviewCountMonthVO
     */
    public ReviewCountVO[] findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        reviewPOList = getReviewPOList(movieId);
        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateUnitedHandler dateUnitedHandler = new MonthDateUnitedHandler();
        DateFormatter dateFormatter = new MonthDateFormatter();
        voGetter = new VOGetter(dateChecker, dateUnitedHandler, dateFormatter);
        return voGetter.getVO(reviewPOList, dateChecker, dateUnitedHandler, dateFormatter);
    }

    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量
     *
     * @param movieId   电影ID
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO[] findDayCountByMovieId(String movieId, String startDate, String endDate) {
        reviewPOList = getReviewPOList(movieId);
        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateUnitedHandler dateUnitedHandler = new DayDateUnitedHandler();
        DateFormatter dateFormatter = new DayDateFormatter();
        voGetter = new VOGetter(dateChecker, dateUnitedHandler, dateFormatter);
        return voGetter.getVO(reviewPOList, dateChecker, dateUnitedHandler, dateFormatter);
    }

    private List<ReviewPO> getReviewPOList(String movieId) {
        if (!reviewPOLinkedHashMap.containsKey(movieId)) {
            reviewPOList = reviewDataService.findReviewsByMovieId(movieId);
            if (reviewPOList.size() != 0) {
                reviewPOLinkedHashMap.put(movieId, reviewPOList);
            } else {
                System.out.println("There is no reviews matching the movieId.");
                return Collections.emptyList();
            }
        } else {
            reviewPOList = reviewPOLinkedHashMap.get(movieId);
        }
        return reviewPOList;
    }

}
