package bl;

import bl.date.DateChecker;
import bl.date.DateFormatter;
import bl.date.DateUtil;
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
public class CommonReviewCountVOGetter {
    ReviewCountVO[] reviewCountVOs;

    List<ReviewPO> reviewPOList;
    String startDate;
    String endDate;
    DateChecker dateChecker;
    DateFormatter dateFormatter;
    DateUtil dateUtil;
    //用于区分来自imdb和amazon的评论
    int maxScore;

    public CommonReviewCountVOGetter(List<ReviewPO> reviewPOList, String startDate, String endDate, DateUtil dateUtil, DateChecker dateChecker, DateFormatter dateFormatter, int maxScore) {
        this.reviewPOList = reviewPOList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateUtil = dateUtil;
        this.dateChecker = dateChecker;
        this.dateFormatter = dateFormatter;
        this.maxScore = maxScore;
    }

    public ReviewCountVO[] getReviewCountVOs() {
        reviewCountVOs = getEmptyReviewCountVOs();
        if (reviewPOList.size() == 0) {
            return reviewCountVOs;
        } else {
            return getNotEmptyCountVOs();
        }
    }

    private ReviewCountVO[] getEmptyReviewCountVOs() {
        ReviewCountVO[] reviewCountVOs = new ReviewCountVO[maxScore+1];

        ArrayList<String> keys = new ArrayList<>();
        LocalDate startDate = dateFormatter.parse(this.startDate);
        LocalDate endDate = dateFormatter.parse(this.endDate);

        while (!startDate.isAfter(endDate)) {
            keys.add(dateFormatter.format(startDate));
            startDate = dateUtil.plus(startDate, 1);
        }


        for (int i = 0; i < reviewCountVOs.length; i++) {
            ArrayList<Integer> reviewAmounts = new ArrayList<>();
            for (int j = 0; j < keys.size(); j++) {
                reviewAmounts.add(0);
            }
            reviewCountVOs[i] = new ReviewCountVO(keys, reviewAmounts);
        }
        return reviewCountVOs;
    }

    private ReviewCountVO[] getNotEmptyCountVOs() {
        List<String> keys = reviewCountVOs[0].getKeys();

        LocalDate startDate = dateFormatter.parse(this.startDate);

        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            if (dateChecker.check(date)) {
                int index = dateUtil.between(startDate, date);
                int score = reviewPO.getScore();
                if (maxScore == 5){
                    score = score/2;
                }
                int tempReviewAmounts = reviewCountVOs[score].getReviewAmounts().get(index);
                tempReviewAmounts = tempReviewAmounts + 1;
                reviewCountVOs[score].getReviewAmounts().set(index, tempReviewAmounts);
            }
        }

        //根据各个分数的评分数量计算总的评分数量
        for (int i = 0; i < reviewCountVOs[0].getKeys().size(); i++) {
            int sum = 0;
            for (int j=1;j<=maxScore;j++){
                sum = sum + reviewCountVOs[j].getReviewAmounts().get(i);
            }
//            int sum = reviewCountVOs[1].getReviewAmounts().get(i) + reviewCountVOs[2].getReviewAmounts().get(i)
//                    + reviewCountVOs[3].getReviewAmounts().get(i) + reviewCountVOs[4].getReviewAmounts().get(i)
//                    + reviewCountVOs[5].getReviewAmounts().get(i);
            reviewCountVOs[0].getReviewAmounts().set(i, sum);
        }

        return reviewCountVOs;
    }
}

