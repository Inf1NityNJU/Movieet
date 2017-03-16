package bl;

import data.ReviewDataFromJsonServiceImpl;
import dataservice.ReviewDataService;
import po.ReviewPO;
import util.LimitedHashMap;
import vo.ReviewCountVO;
import vo.ReviewWordsVO;
import vo.UserVO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by vivian on 2017/3/9.
 */
public class User {
    private ReviewDataService reviewDataService = new ReviewDataFromJsonServiceImpl();
    private List<ReviewPO> reviewPOList;
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);
    private VOGetter voGetter;

    public UserVO findUserById(String userId) {
        getReviewPOList(userId);

        TreeSet<LocalDate> dates = new TreeSet<>();
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            dates.add(date);
        }
        String firstReviewDate = dates.first().toString();
        String lastReviewDate = dates.last().toString();

        return new UserVO(userId, reviewPOList.size(), firstReviewDate, lastReviewDate);
    }

    /**
     * 根据 userId 获得评论文字长度分布
     *
     * @param userId 用户ID
     * @return ReviewWordsVO
     */
    public ReviewWordsVO getReviewWordsVO(String userId) {
        getReviewPOList(userId);

        //横坐标
        int maxWords = 200;
        int step = 20;
        String[] keys = new String[maxWords / step + 1];
        for (int i = 0; i < keys.length - 1; i++) {
            keys[i] = i * step + "-" + (i + 1) * step;
        }
        keys[keys.length - 1] = maxWords + "+";

        //纵坐标
        int[] reviewAmounts = new int[keys.length];
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
//                int words = reviewPO.getWords();
                reviewAmounts[words / 20]++;
            }
        }
        return new ReviewWordsVO(keys, reviewAmounts);
    }

    public ReviewCountVO[] findYearCountByUserId(String userId) {
        getReviewPOList(userId);

        if (reviewPOList.size() == 0) {
            return null;
        }

        DateChecker dateChecker = new YearDateChecker();
        DateUnitedHandler dateUnitedHandler = new YearDateUnitedHandler();
        DateFormatter dateFormatter = new YearDateFormatter();
        voGetter = new VOGetter(dateChecker, dateUnitedHandler, dateFormatter);
        return voGetter.getVO(reviewPOList, dateChecker, dateUnitedHandler, dateFormatter);
    }

    public ReviewCountVO[] findMonthCountByUserId(String userId, String startMonth, String endMonth) {
        getReviewPOList(userId);

        if (reviewPOList.size() == 0) {
            return null;
        }

        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateUnitedHandler dateUnitedHandler = new MonthDateUnitedHandler();
        DateFormatter dateFormatter = new MonthDateFormatter();
        voGetter = new VOGetter(dateChecker, dateUnitedHandler, dateFormatter);
        return voGetter.getVO(reviewPOList, dateChecker, dateUnitedHandler, dateFormatter);

    }

    public ReviewCountVO[] findDayCountByUserId(String userId, String startDate, String endDate) {
        getReviewPOList(userId);
        if (reviewPOList.size() == 0) {
            return null;
        }
        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateUnitedHandler dateUnitedHandler = new DayDateUnitedHandler();
        DateFormatter dateFormatter = new DayDateFormatter();
        voGetter = new VOGetter(dateChecker, dateUnitedHandler, dateFormatter);
        return voGetter.getVO(reviewPOList, dateChecker, dateUnitedHandler, dateFormatter);
    }

    private List<ReviewPO> getReviewPOList(String userId) {
        if (!reviewPOLinkedHashMap.containsKey(userId)) {
            reviewPOList = reviewDataService.findReviewsByUserId(userId);
            if (reviewPOList.size() != 0) {
                reviewPOLinkedHashMap.put(userId, reviewPOList);
            } else {
                System.out.println("There is no reviews matching the movieId.");
                return Collections.emptyList();
            }
        } else {
            reviewPOList = reviewPOLinkedHashMap.get(userId);
        }
        return reviewPOList;
    }
}
