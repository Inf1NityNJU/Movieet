package bl;

import data.DataServiceFactory;
import dataservice.ReviewDataService;
import po.ReviewPO;
import po.WordPO;
import util.LimitedHashMap;
import vo.ReviewCountVO;
import vo.ReviewWordsVO;
import vo.UserVO;
import vo.WordVO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by vivian on 2017/3/9.
 */
class User {
    private ReviewDataService reviewDataService = DataServiceFactory.getJsonService();
    //        private ReviewDataService reviewDataService = new ReviewDataServiceStub();
    private List<ReviewPO> reviewPOList;
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);

    public UserVO findUserById(String userId) {
        getReviewPOList(userId);

        if (reviewPOList.size() == 0) {
            return null;
        }

        TreeSet<LocalDate> dates = new TreeSet<>();
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            dates.add(date);
        }
        String firstReviewDate = dates.first().toString();
        String lastReviewDate = dates.last().toString();

        return new UserVO(userId, reviewPOList.get(0).getProfileName(), reviewPOList.size(), firstReviewDate, lastReviewDate);
    }

    /**
     * 根据 userId 获得评论文字长度分布
     *
     * @param userId 用户ID
     * @return ReviewWordsVO
     */
    public ReviewWordsVO getReviewWordsLengthVO(String userId) {
        getReviewPOList(userId);

        //横坐标
        int maxWords = 200;
        int step = 20;
        int size = maxWords / step + 1;
        List<String> keys = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            keys.add(i * step + "");
        }

        //纵坐标
        List<Integer> reviewAmounts = new ArrayList<>(keys.size());
        for (int i = 0; i < size; i++) {
            reviewAmounts.add(0);
        }
        for (ReviewPO reviewPO : reviewPOList) {
            String review = reviewPO.getText();
            if (review != null && review.trim().length() != 0) {
                int count = 1;
                for (int i = 0; i < review.length(); i++) {
                    if (review.charAt(i) == ' ' && i > 0 && review.charAt(i - 1) != ' ') {
                        count++;
                    }
                }
                int words = count;
                int index = words / 20;
                index = index > size - 1 ? size - 1 : index;
                reviewAmounts.set(index, reviewAmounts.get(index) + 1);
            }
        }
        return new ReviewWordsVO(keys, reviewAmounts);
    }

    public ReviewCountVO[] findYearCountByUserId(String userId, String startYear, String endYear) {
        getReviewPOList(userId);


        DateChecker dateChecker = new YearDateChecker();
        DateFormatter dateFormatter = new YearDateFormatter();
        ReviewCountVO[] reviewCountVOs = getReviewCountVOs(startYear, endYear, "Year", dateFormatter);

        if (reviewPOList.size() != 0) {
            reviewCountVOs = getNotEmptyCountVOs(reviewCountVOs, dateChecker, dateFormatter);
        }
        return reviewCountVOs;
    }

    public ReviewCountVO[] findMonthCountByUserId(String userId, String startMonth, String endMonth) {
        getReviewPOList(userId);

        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateFormatter dateFormatter = new MonthDateFormatter();
        ReviewCountVO[] reviewCountVOs = getReviewCountVOs(startMonth, endMonth, "Month", dateFormatter);

        if (reviewPOList.size() != 0) {
            reviewCountVOs = getNotEmptyCountVOs(reviewCountVOs, dateChecker, dateFormatter);
        }
        return reviewCountVOs;
    }

    public ReviewCountVO[] findDayCountByUserId(String userId, String startDate, String endDate) {
        getReviewPOList(userId);

        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateFormatter dateFormatter = new DayDateFormatter();
        ReviewCountVO[] reviewCountVOs = getReviewCountVOs(startDate, endDate, "Day", dateFormatter);

        if (reviewPOList.size() != 0) {
            reviewCountVOs = getNotEmptyCountVOs(reviewCountVOs, dateChecker, dateFormatter);
        }

        return reviewCountVOs;
    }

    public WordVO findWordsByUserId(String userId) {
        WordPO wordPO = reviewDataService.findWordsByUserId(userId);
        //如果是错误的movie id
        if (wordPO == null) {
            return null;
        }
        return new WordVO(wordPO.getTopWords());
    }

    private List<ReviewPO> getReviewPOList(String userId) {
        if (!reviewPOLinkedHashMap.containsKey(userId)) {
            reviewPOList = reviewDataService.findReviewsByUserId(userId);
            if (reviewPOList.size() != 0) {
                reviewPOLinkedHashMap.put(userId, reviewPOList);
            } else {
                System.out.println("There is no reviews matching the userId.");
                return Collections.emptyList();
            }
        } else {
            reviewPOList = reviewPOLinkedHashMap.get(userId);
        }
        return reviewPOList;
    }

    private ReviewCountVO[] getReviewCountVOs(String start, String end, String dateStyle, DateFormatter dateFormatter) {
        ArrayList<String> keys = new ArrayList<>();
        LocalDate startDate;
        LocalDate endDate;

        if (dateStyle == "Year") {
            startDate = LocalDate.parse(start + "-01-01");
            endDate = LocalDate.parse(end + "-01-01");
        } else if (dateStyle == "Month") {
            startDate = LocalDate.parse(start + "-01");
            endDate = LocalDate.parse(end + "-01");
        } else {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
        }


        while (!startDate.isAfter(endDate)) {
            keys.add(dateFormatter.format(startDate));

            if (dateStyle == "Year") {
                startDate = startDate.plusYears(1);
            } else if (dateStyle == "Month") {
                startDate = startDate.plusMonths(1);
            } else {
                startDate = startDate.plusDays(1);
            }
        }


        ReviewCountVO[] reviewCountVOs = new ReviewCountVO[6];
        for (int i = 0; i < reviewCountVOs.length; i++) {
            ArrayList<Integer> reviewAmounts = new ArrayList<>();
            for (int j = 0; j < keys.size(); j++) {
                reviewAmounts.add(0);
            }
            reviewCountVOs[i] = new ReviewCountVO(keys, reviewAmounts);
        }

        return reviewCountVOs;
    }

    private ReviewCountVO[] getNotEmptyCountVOs(ReviewCountVO[] reviewCountVOs, DateChecker dateChecker, DateFormatter dateFormatter) {
        List<String> keys = reviewCountVOs[0].getKeys();

        // count 用于记录当前日期在list中的位置
        int count = 0;
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            if (dateChecker.check(date)) {
                for (int i = 0; i < keys.size(); i++) {
                    if (dateFormatter.format(date).equals(keys.get(i))) {
                        count = i;
                        break;
                    }
                }

                int score = reviewPO.getScore();
                int tempReviewAmounts = reviewCountVOs[score].getReviewAmounts().get(count);
                tempReviewAmounts = tempReviewAmounts + 1;
                reviewCountVOs[score].getReviewAmounts().set(count, tempReviewAmounts);
            }
        }

        //根据各个分数的评分数量计算总的评分数量
        for (int i = 0; i < reviewCountVOs[0].getKeys().size(); i++) {
            int sum = reviewCountVOs[1].getReviewAmounts().get(i) + reviewCountVOs[2].getReviewAmounts().get(i)
                    + reviewCountVOs[3].getReviewAmounts().get(i) + reviewCountVOs[4].getReviewAmounts().get(i)
                    + reviewCountVOs[5].getReviewAmounts().get(i);
            reviewCountVOs[0].getReviewAmounts().set(i, sum);
        }

        return reviewCountVOs;
    }
}
