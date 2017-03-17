package bl;

import po.ReviewPO;
import vo.ReviewCountVO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/3/18.
 */
public class CommonGetVOHandler {
    List<ReviewPO> reviewPOList;
    String startDate;
    String endDate;
    DateChecker dateChecker;
    DateFormatter dateFormatter;
    String dateStyle;

    public CommonGetVOHandler(List<ReviewPO> reviewPOList, String startDate, String endDate, String dateStyle, DateChecker dateChecker, DateFormatter dateFormatter) {
        this.reviewPOList = reviewPOList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateStyle = dateStyle;
        this.dateChecker = dateChecker;
        this.dateFormatter = dateFormatter;
    }

    public ReviewCountVO[] getReviewCountVOs() {
        if (reviewPOList.size() == 0) {
            return getEmptyReviewCountVOs(startDate, endDate, dateStyle, dateFormatter);
        } else {
            ReviewCountVO[] reviewCountVOs = getEmptyReviewCountVOs(startDate, endDate, dateStyle, dateFormatter);
            return getNotEmptyCountVOs(reviewCountVOs, dateChecker, dateFormatter);
        }
    }

    private ReviewCountVO[] getEmptyReviewCountVOs(String start, String end, String dateStyle, DateFormatter dateFormatter) {
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

