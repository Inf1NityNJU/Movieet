package bl;

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
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by vivian on 2017/3/4.
 */
public class Movie {
    private ReviewDataService reviewDataService = new ReviewDataServiceStub();
    private List<ReviewPO> reviewPOList;
    //    private static HashMap<String, List<ReviewPO>> reviewPOListHashMap = new HashMap<String, List<ReviewPO>>();
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);

//    public Movie(ReviewDataServiceStub reviewDataServiceStub) {
//        this.reviewDataServiceStub = reviewDataServiceStub;
//    }

    /**
     * 根据 movieId 查找电影
     *
     * @param movieId 电影ID
     * @return MovieVO
     */
    public MovieVO findMovieById(String movieId) {
        MoviePO moviePO = reviewDataService.findMovieByMovieId(movieId);
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
        DateChecker dateChecker = new YearDateChecker();
        DateUnitedHandler dateUnitedHandler = new YearDateUnitedHandler();
        DateFormatter dateFormatter = new YearDateFormatter();

        return getVO(movieId, dateChecker, dateUnitedHandler, dateFormatter);
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
        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateUnitedHandler dateUnitedHandler = new MonthDateUnitedHandler();
        DateFormatter dateFormatter = new MonthDateFormatter();

        return getVO(movieId, dateChecker, dateUnitedHandler, dateFormatter);
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
        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateUnitedHandler dateUnitedHandler = new DayDateUnitedHandler();
        DateFormatter dateFormatter = new DayDateFormmatter();

        return getVO(movieId, dateChecker, dateUnitedHandler, dateFormatter);
    }

    /**
     * 根据不同的判断条件和数据格式分别返回按年、月、日分布的评论数量
     *
     * @param movieId   电影ID
     * @param checker   日期判断方式
     * @param formatter 日期显示格式
     * @return 按年、月、日分布的评论数量VO
     */
    private ReviewCountVO[] getVO(String movieId, DateChecker checker, DateUnitedHandler dateUnitedHandler, DateFormatter formatter) {
        //不同评分的reviewCountVO，0表示全部
        ReviewCountVO[] reviewCountVOs = new ReviewCountVO[6];
        for (int i=0;i<reviewCountVOs.length;i++){
            reviewCountVOs[i] = new ReviewCountVO();
        }

        getReviewPOList(movieId);

        TreeSet<DateIntPair> dateIntPairs = new TreeSet<>();

        //根据筛选条件筛选出符合条件的DateIntPair，无重复
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            if (checker.check(date)) {
                DateIntPair dateIntPair = new DateIntPair(date);
                if (!dateIntPairs.add(dateIntPair)) {
                    dateIntPairs.ceiling(dateIntPair).increase(reviewPO.getScore()-1);
                }else {
                    dateIntPair.increase(reviewPO.getScore()-1);
                    dateIntPairs.add(dateIntPair);
                }
            }
        }

        //将相同年份或月份的数据归并到一个，去除重复
        Object[] tempArray = dateIntPairs.toArray();
        DateIntPair[] dateIntPairsArray = new DateIntPair[tempArray.length];
        int count = 0;
        for (Object o : tempArray) {
            dateIntPairsArray[count] = (DateIntPair)o;
            count++;
        }
        dateIntPairsArray = dateUnitedHandler.getUnitedArray(dateIntPairsArray);

//        初始化ReviewCountVO的keys和reviewAmounts
        for (ReviewCountVO reviewCountVO:reviewCountVOs){
            String[] tempKeys = new String[dateIntPairsArray.length];
            Arrays.fill(tempKeys, "");
            int[] tempItems = new int[tempKeys.length];
            reviewCountVO.setKeys(tempKeys);
            reviewCountVO.setReviewAmounts(tempItems);
        }

        count = 0;
        for (DateIntPair dateIntPair : dateIntPairsArray) {

            //所有评分的ReviewCountVO
            reviewCountVOs[0].getKeys()[count] = formatter.format(dateIntPair.getDate());
            reviewCountVOs[0].getReviewAmounts()[count] = dateIntPair.getTotalAmountOfReview();

            //根据评分返回的ReviewCountVO
            for (int i=1; i<reviewCountVOs.length;i++){
                reviewCountVOs[i].getKeys()[count] = reviewCountVOs[0].getKeys()[count];
                reviewCountVOs[i].getReviewAmounts()[count] = dateIntPair.count[i-1];
            }

            count++;
        }

        return reviewCountVOs;
    }

    private List<ReviewPO> getReviewPOList(String movieId) {
        if (!reviewPOLinkedHashMap.containsKey(movieId)) {
            reviewPOList = reviewDataService.findReviewsByMovieId(movieId);
            reviewPOLinkedHashMap.put(movieId, reviewPOList);

        } else {
            reviewPOList = reviewPOLinkedHashMap.get(movieId);
        }
        return reviewPOList;
    }

}
