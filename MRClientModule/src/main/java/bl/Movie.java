package bl;

import bl.date.*;
import dataservice.ReviewDataService;
import datastub.ReviewDataServiceStub;
import po.*;
import util.LimitedHashMap;
import util.MovieSortType;
import vo.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by vivian on 2017/3/4.
 */
class Movie {
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);
//    private ReviewDataService reviewDataService = DataServiceFactory.getJsonService();
        private ReviewDataService reviewDataService = new ReviewDataServiceStub();
    private List<ReviewPO> reviewPOList;

    //电影和用户公用的获得ReviewCountVO的方法类
    private CommonGetVOHandler commonGetVOHandler;


    /**
     * 根据 movieId 查找电影
     *
     * @param movieId 电影ID
     * @return MovieVO
     */
    public MovieVO findMovieById(String movieId) {
        MoviePO moviePO = reviewDataService.findMovieByMovieId(movieId);


        return new MovieVO(movieId, moviePO.getName(), "2017-03-01", null);
    }

    /**
     * 根据电影 movieId 查找评价分布
     *
     * @param movieId 电影ID
     * @return ScoreDistributionVO
     */
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        getReviewPOList(movieId);

        if (reviewPOList.size() == 0) {
            return null;
        }

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
    public ReviewCountVO[] findYearCountByMovieId(String movieId, String startYear, String endYear) {
        getReviewPOList(movieId);

        DateUtil dateUtil = new YearDateUtil();
        DateChecker dateChecker = new YearDateChecker();
        DateFormatter dateFormatter = new YearDateFormatter();
        commonGetVOHandler = new CommonGetVOHandler(reviewPOList, startYear, endYear, dateUtil, dateChecker, dateFormatter);

        return commonGetVOHandler.getReviewCountVOs();
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
        getReviewPOList(movieId);

        DateUtil dateUtil = new MonthDateUtil();
        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateFormatter dateFormatter = new MonthDateFormatter();
        commonGetVOHandler = new CommonGetVOHandler(reviewPOList, startMonth, endMonth, dateUtil, dateChecker, dateFormatter);

        return commonGetVOHandler.getReviewCountVOs();
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
        getReviewPOList(movieId);

        DateUtil dateUtil = new DayDateUtil();
        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateFormatter dateFormatter = new DayDateFormatter();
        commonGetVOHandler = new CommonGetVOHandler(reviewPOList, startDate, endDate, dateUtil, dateChecker, dateFormatter);

        return commonGetVOHandler.getReviewCountVOs();
    }


    public WordVO findWordsByMovieId(String movieId) {
        WordPO wordPO = reviewDataService.findWordsByMovieId(movieId);
        //如果是错误的movie id
        if (wordPO == null) {
            return null;
        }
        return new WordVO(wordPO.getTopWords());
    }

    //迭代二

    public PageVO findMoviesByKeywordInPage(String keyword, int page) {
        PagePO pagePO = reviewDataService.findMoviesByKeywordInPage(keyword, page);
//        PageVO pageVO = new PageVO(pagePO.pagePO.getResult())
        return null;
    }

    public PageVO findMoviesByTagInPage(String tag, MovieSortType movieSortType, int page) {
        return null;
    }

    public MovieStatisticsVO findMovieStatisticsVOByMovieId(String movieId) {
        getReviewPOList(movieId);

        if (reviewPOList.size() == 0) {
            return null;
        }

        double scoreSum = 0;

        //计算评分均值
        for (int i = 0; i < reviewPOList.size(); i++) {
            scoreSum = scoreSum + reviewPOList.get(i).getScore();
        }
        double averageScore = scoreSum / reviewPOList.size();

        //第一条评论日期和最后一条评论日期
        TreeSet<LocalDate> dates = new TreeSet<>();
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            dates.add(date);
        }
        String firstReviewDate = dates.first().toString();
        String lastReviewDate = dates.last().toString();
        return new MovieStatisticsVO(reviewPOList.size(), averageScore, firstReviewDate, lastReviewDate);
    }

    public PageVO findReviewsByMovieIdInPage(String movieId, MovieSortType movieSortType, int page) {
        return null;
    }

    public MovieGenreVO findMovieGenre() {
        MovieGenrePO movieGenrePO = reviewDataService.findMovieGenre();
        return new MovieGenreVO(movieGenrePO.getTags(), movieGenrePO.getAmounts());
    }

    public ScoreAndReviewAmountVO findRelationBetweenScoreAndReviewAmount() {
        ScoreAndReviewAmountPO scoreAndReviewAmountPO = reviewDataService.findRelationBetweenScoreAndReviewAmount();
        return new ScoreAndReviewAmountVO(scoreAndReviewAmountPO.getScores(), scoreAndReviewAmountPO.getReviewAmounts());
    }

    public ScoreDateVO findScoreDateByMonth(String Id, String startMonth, String endMonth) {
        getReviewPOList(Id);

        if (reviewPOList.size() == 0) {
            return null;
        }

        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateFormatter dateFormatter = new MonthDateFormatter();
        DateUtil dateUtil = new MonthDateUtil();

        List<String> dates = new ArrayList<>();
        LocalDate startDate = dateFormatter.parse(startMonth);
        LocalDate endDate = dateFormatter.parse(endMonth);

        while (!startDate.isAfter(endDate)) {
            dates.add(dateFormatter.format(startDate));
            startDate = dateUtil.plus(startDate, 1);
        }

        /**
         * 由于要求是：展示电影综合评分随着时间的变化，因此每一个时间节点都需要一个List来记录这个时间所有的评分，
         * 再根据所有的评分计算这个时间节点的综合评分。
         * 所以此处用了两个list嵌套，外层的list用于表示改时间段内所有的时间节点，内层list表示每一个时间节点对应的一组评分
         */
        List<ArrayList<Integer>> allScoresOnCertainDates = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < dates.size(); i++) {
            ArrayList<Integer> tempScores = new ArrayList<>();
            allScoresOnCertainDates.add(tempScores);
        }

        startDate = dateFormatter.parse(startMonth);
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            if (dateChecker.check(date)) {
                int index = dateUtil.between(startDate, date);
                int score = reviewPO.getScore();
                ArrayList<Integer> tempScores = allScoresOnCertainDates.get(index);
                tempScores.add(score);
                allScoresOnCertainDates.set(index, tempScores);
            }
        }

        //计算各个时间节点的综合评分,若没有评分，默认0分
        List<Double> scores = new ArrayList<>();
        for (int i=0;i<allScoresOnCertainDates.size();i++){
            if (allScoresOnCertainDates.get(i).size()==0){
                scores.add(null);
            }else {
                Double score = 0.0;
                List<Integer> currentScores = allScoresOnCertainDates.get(i);
                for (int j=0; j<currentScores.size();j++){
                    score += currentScores.get(j);
                }
                score = score/currentScores.size();
                scores.add(score);
            }
        }

        return new ScoreDateVO(dates, scores);
    }

    public ScoreDateVO findScoreDateByDay(String Id, String startDate, String endDate) {
        return null;
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