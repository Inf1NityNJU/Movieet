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
     * @return
     */
    public MovieVO findMovieById(String movieId) {
        MoviePO moviePO = reviewDataServiceStub.findMovieByMovieId(movieId);
        getReviewPOList(movieId);

        float scoreSum = 0;
        float scoreSquareSum = 0;

        //计算评分平方和、评分均值
        for (int i = 0; i < reviewPOList.size(); i++) {
            scoreSum = scoreSum + reviewPOList.get(i).getScore();
            scoreSquareSum = scoreSquareSum + (float) Math.pow(reviewPOList.get(i).getScore(), 2);
        }
        float averageScore = scoreSum / reviewPOList.size();

        //计算评分方差
        float variance = (scoreSquareSum - (float) (Math.pow(scoreSum, 2) / reviewPOList.size())) / reviewPOList.size();

        MovieVO movieVO = new MovieVO(movieId, moviePO.getName(), reviewPOList.size(), averageScore, variance);
        return movieVO;
    }

    /**
     * 根据电影 movieId 查找评价分布
     *
     * @param movieId 电影ID
     * @return
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
     * @return
     */
    public ReviewCountYearVO findYearCountByMovieId(String movieId) {
        getReviewPOList(movieId);

        //统计该电影有评论的年份（不重复）以及每年的评论数量
        Map<Integer, Integer> yearsAndAmounts = new HashMap<>();
        for (int i = 0; i < reviewPOList.size(); i++) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPOList.get(i).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if (yearsAndAmounts.containsKey(date.getYear())) {
                int newValue = yearsAndAmounts.get(date.getYear()) + 1;
                yearsAndAmounts.put(date.getYear(), newValue);
            } else {
                yearsAndAmounts.put(date.getYear(), 1);
            }
        }

        //将年份排序
        List<Integer> orderYears = new ArrayList<>();
        for (Map.Entry entry : yearsAndAmounts.entrySet()) {
            int year = (Integer) entry.getKey();
            orderYears.add(year);
        }
        Collections.sort(orderYears);

        //将统计结果整理为横纵坐标
        String[] years = new String[orderYears.size()];
        for (int i = 0; i < years.length; i++) {
            years[i] = Integer.toString(orderYears.get(i));
        }

        int[] reviewAmounts = new int[years.length];
        for (int i = 0; i < reviewAmounts.length; i++) {
            reviewAmounts[i] = yearsAndAmounts.get(Integer.parseInt(years[i]));
        }

        return new ReviewCountYearVO(years, reviewAmounts);
    }

    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param movieId
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return
     */
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        getReviewPOList(movieId);

        String[] yearAndMonth = startMonth.split("-");
        for (int i = 0; i < reviewPOList.size(); i++) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPOList.get(i).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.getYear() >= Integer.parseInt(yearAndMonth[0]) && date.getYear() <= Integer.parseInt(yearAndMonth[1])) {

            }
        }

        String[] keys = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int[] reviewAmounts = new int[12];

        for (int i = 0; i < reviewPOList.size(); i++) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPOList.get(i).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            reviewAmounts[date.getMonthValue() - 1]++;
        }
        ReviewCountMonthVO reviewCountMonthVO = new ReviewCountMonthVO(keys, reviewAmounts);
        return reviewCountMonthVO;
    }

    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量
     *
     * @param movieId   电影ID
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountDayVO findDayCountByMovieId(String movieId, String startDate, String endDate) {
        return null;
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
