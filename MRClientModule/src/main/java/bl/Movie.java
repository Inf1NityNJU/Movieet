package bl;

import datastub.ReviewDataServiceStub;
import po.MoviePO;
import po.ReviewPO;
import util.LimitedHashMap;
import vo.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by vivian on 2017/3/4.
 */
public class Movie {
    private ReviewDataServiceStub reviewDataServiceStub;
    private List<ReviewPO> reviewPOList;
    //    private static HashMap<String, List<ReviewPO>> reviewPOListHashMap = new HashMap<String, List<ReviewPO>>();
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);

    public Movie(ReviewDataServiceStub reviewDataServiceStub) {
        this.reviewDataServiceStub = reviewDataServiceStub;
    }

    /**
     * 根据 movieId 查找电影
     *
     * @param movieId 电影ID
     * @return MovieVO
     */
    public MovieVO findMovieById(String movieId) {
        MoviePO moviePO = reviewDataServiceStub.findMovieByMovieId(movieId);
        getReviewPOList(movieId);

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
                    Instant.ofEpochMilli(reviewPO.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
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
    public ReviewCountVO findYearCountByMovieId(String movieId) {
        DateChecker dateChecker = new YearDateChecker();
        DateFormatter dateFormatter = new YearDateFormatter();

        return getVO(movieId, dateChecker, dateFormatter);
    }


    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param movieId    电影ID
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return ReviewCountMonthVO
     */
    public ReviewCountVO findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateFormatter dateFormatter = new MonthDateFormatter();

        return getVO(movieId, dateChecker, dateFormatter);
    }

    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量
     *
     * @param movieId   电影ID
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO findDayCountByMovieId(String movieId, String startDate, String endDate) {
        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateFormatter dateFormatter = new DayDateFormmatter();

        return getVO(movieId, dateChecker, dateFormatter);
    }

    /**
     * 根据不同的判断条件和数据格式分别返回按年、月、日分布的评论数量
     * @param movieId 电影ID
     * @param checker 日期判断方式
     * @param formatter 日期显示格式
     * @return 按年、月、日分布的评论数量VO
     */
    private ReviewCountVO getVO(String movieId, DateChecker checker, DateFormatter formatter) {
        getReviewPOList(movieId);

        TreeSet<DateIntPair> dateIntPairs = new TreeSet<>();

        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            if (checker.check(date)) {
                DateIntPair dateIntPair = new DateIntPair(date);
                if (!dateIntPairs.add(dateIntPair)) {
                    dateIntPairs.ceiling(dateIntPair).increase();
                }
            }
        }

        String[] key = new String[dateIntPairs.size()];
        int[] items = new int[key.length];
        int count = 0;
        for (DateIntPair dateIntPair : dateIntPairs) {
            key[count] = formatter.format(dateIntPair.date);
            items[count] = dateIntPair.count;
            count++;
        }

        return new ReviewCountVO(key, items);
    }

    private List<ReviewPO> getReviewPOList(String movieId) {
        if (!reviewPOLinkedHashMap.containsKey(movieId)) {
            reviewPOList = reviewDataServiceStub.findReviewsByMovieId(movieId);
            reviewPOLinkedHashMap.put(movieId, reviewPOList);

        } else {
            reviewPOList = reviewPOLinkedHashMap.get(movieId);
        }
        return reviewPOList;
    }

}
