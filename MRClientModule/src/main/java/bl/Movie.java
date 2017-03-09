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
     * @param movieId    电影ID
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return ReviewCountMonthVO
     */
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        getReviewPOList(movieId);

        //统计该电影有评论的月份（不重复）以及每月的评论数量
        String[] startYearAndMonth = startMonth.split("-");
        String[] endYearAndMonth = endMonth.split("-");
        Map<String, Integer> monthsAndAmounts = new HashMap<>();
        for (int i = 0; i < reviewPOList.size(); i++) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPOList.get(i).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            if (date.getYear() >= Integer.parseInt(startYearAndMonth[0]) && date.getYear() <= Integer.parseInt(endYearAndMonth[0])
                    && date.getMonthValue() >= Integer.parseInt(startYearAndMonth[1]) && date.getMonthValue() <= Integer.parseInt(endYearAndMonth[1])) {
                String key = date.getYear() + "-" + date.getMonthValue();
                if (date.getMonthValue() < 10) {
                    key = date.getYear() + "-0" + date.getMonthValue();
                }
                if (monthsAndAmounts.containsKey(key)) {
                    int newValue = monthsAndAmounts.get(key) + 1;
                    monthsAndAmounts.put(key, newValue);
                } else {
                    monthsAndAmounts.put(key, 1);
                }
            }
        }

        //将月份排序
        List<String> orderMonths = new ArrayList<>();
        for (Map.Entry entry : monthsAndAmounts.entrySet()) {
            String key = (String) entry.getKey();
            orderMonths.add(key);
        }
        Collections.sort(orderMonths, new MonthComparator());

        //将统计结果整理为横纵坐标
        String[] months = new String[orderMonths.size()];
        for (int i = 0; i < months.length; i++) {
            months[i] = orderMonths.get(i);
        }

        int[] reviewAmounts = new int[months.length];
        for (int i = 0; i < reviewAmounts.length; i++) {
            reviewAmounts[i] = monthsAndAmounts.get(months[i]);
        }

        return new ReviewCountMonthVO(months, reviewAmounts);
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
        getReviewPOList(movieId);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        //统计指定日期内该电影的评论数量
        Map<LocalDate, Integer> datesAndAmounts = new HashMap<>();
        for (int i = 0; i < reviewPOList.size(); i++) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPOList.get(i).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if ((date.isAfter(start) && date.isBefore(end)) || date.isEqual(start) || date.isEqual(end)) {
                if (datesAndAmounts.containsKey(date)) {
                    int newValue = datesAndAmounts.get(date) + 1;
                    datesAndAmounts.put(date, newValue);
                } else {
                    datesAndAmounts.put(date, 1);
                }
            }

        }

        //将日期排序
        List<LocalDate> orderDates = new ArrayList<>();
        for (Map.Entry entry : datesAndAmounts.entrySet()) {
            LocalDate key = (LocalDate) entry.getKey();
            orderDates.add(key);
        }
        Collections.sort(orderDates, new DayComparator());

        //将统计结果整理为横纵坐标
        String[] dates = new String[orderDates.size()];
        for (int i = 0; i < dates.length; i++) {
            dates[i] = orderDates.get(i).toString();
        }

        int[] reviewAmounts = new int[dates.length];
        for (int i = 0; i < reviewAmounts.length; i++) {
            reviewAmounts[i] = datesAndAmounts.get(LocalDate.parse(dates[i]));
        }
        return new ReviewCountDayVO(dates, reviewAmounts);
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

    //将诸如"2017-03"这样的时间格式按时间先后排序
    static class MonthComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;
            s1.replace("-", "");
            s2.replace("-", "");
            Integer date1 = Integer.parseInt(s1);
            Integer date2 = Integer.parseInt(s2);
            return date1.compareTo(date2);
        }
    }


    //将诸如"2017-03-09"这样的时间格式按时间先后排序
    static class DayComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            LocalDate localDate1 = (LocalDate) o1;
            LocalDate localDate2 = (LocalDate) o2;
            if (localDate1.isBefore(localDate2)) {
                return -1;
            } else if (localDate1.isEqual(localDate2)) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
