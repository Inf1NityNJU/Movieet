package bl;

import po.ReviewPO;
import vo.ReviewCountVO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by vivian on 2017/3/16.
 */
public class VOGetter {
    DateChecker dateChecker;
    DateUnitedHandler dateUnitedHandler;
    DateFormatter dateFormatter;

    public VOGetter(DateChecker dateChecker, DateUnitedHandler dateUnitedHandler, DateFormatter dateFormatter) {
        this.dateChecker = dateChecker;
        this.dateUnitedHandler = dateUnitedHandler;
        this.dateFormatter = dateFormatter;
    }

    public ReviewCountVO[] getVO(List<ReviewPO> reviewPOList, DateChecker checker, DateUnitedHandler dateUnitedHandler, DateFormatter formatter) {
        //不同评分的reviewCountVO，0表示全部
        ReviewCountVO[] reviewCountVOs = new ReviewCountVO[6];
        for (int i = 0; i < reviewCountVOs.length; i++) {
            reviewCountVOs[i] = new ReviewCountVO();
        }

        TreeSet<DateIntPair> dateIntPairs = new TreeSet<>();

        //根据筛选条件筛选出符合条件的DateIntPair，无重复
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            if (checker.check(date)) {
                DateIntPair dateIntPair = new DateIntPair(date);
                dateIntPair.increase(reviewPO.getScore() - 1);
                if (!dateIntPairs.add(dateIntPair)) {
                    dateIntPairs.ceiling(dateIntPair).increase(reviewPO.getScore() - 1);
                }
            }
        }

        //将相同年份或月份的数据归并到一个
        DateIntPair[] dateIntPairsArray = dateUnitedHandler.getUnitedArray(
                dateIntPairs.toArray(new DateIntPair[dateIntPairs.size()]));

        //初始化ReviewCountVO的keys和reviewAmounts
        ArrayList<String> keys = new ArrayList<String>(dateIntPairsArray.length);
        for (ReviewCountVO reviewCountVO : reviewCountVOs) {
            reviewCountVO.setKeys(keys);
            reviewCountVO.setReviewAmounts(new ArrayList<Integer>(dateIntPairsArray.length));
        }

        for (int i = 0; i < dateIntPairsArray.length; i++) {

            DateIntPair dateIntPair = dateIntPairsArray[i];
            //所有评分的ReviewCountVO
            keys.add(formatter.format(dateIntPair.getDate()));
            reviewCountVOs[0].getReviewAmounts().add(dateIntPair.getTotalAmountOfReview());

            //根据评分返回的ReviewCountVO
            for (int j = 1; j < reviewCountVOs.length; j++) {
                reviewCountVOs[j].getReviewAmounts().add(dateIntPair.count[j - 1]);
            }

        }

        return reviewCountVOs;
    }
}
