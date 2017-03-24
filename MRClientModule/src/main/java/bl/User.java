package bl;

import bl.date.*;
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
    //            private ReviewDataService reviewDataService = new ReviewDataServiceStub();
    private List<ReviewPO> reviewPOList;
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);
    private CommonGetVOHandler commonGetVOHandler;

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

        return new UserVO(userId, reviewPOList.get(0).getUserName(), reviewPOList.size(), firstReviewDate, lastReviewDate);
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

        DateUtil dateUtil = new YearDateUtil();
        DateChecker dateChecker = new YearDateChecker();
        DateFormatter dateFormatter = new YearDateFormatter();
        commonGetVOHandler = new CommonGetVOHandler(reviewPOList, startYear, endYear, dateUtil, dateChecker, dateFormatter);

        return commonGetVOHandler.getReviewCountVOs();
    }

    public ReviewCountVO[] findMonthCountByUserId(String userId, String startMonth, String endMonth) {
        getReviewPOList(userId);

        DateUtil dateUtil = new MonthDateUtil();
        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateFormatter dateFormatter = new MonthDateFormatter();
        commonGetVOHandler = new CommonGetVOHandler(reviewPOList, startMonth, endMonth, dateUtil, dateChecker, dateFormatter);

        return commonGetVOHandler.getReviewCountVOs();
    }

    public ReviewCountVO[] findDayCountByUserId(String userId, String startDate, String endDate) {
        getReviewPOList(userId);

        DateUtil dateUtil = new DayDateUtil();
        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateFormatter dateFormatter = new DayDateFormatter();
        commonGetVOHandler = new CommonGetVOHandler(reviewPOList, startDate, endDate, dateUtil, dateChecker, dateFormatter);

        return commonGetVOHandler.getReviewCountVOs();
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

}
