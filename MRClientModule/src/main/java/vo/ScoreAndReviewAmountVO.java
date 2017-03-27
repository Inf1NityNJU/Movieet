package vo;

import java.util.List;

/**
 * Created by vivian on 2017/3/21.
 */
public class ScoreAndReviewAmountVO {
    //电影的评分
    List<Double> scores;

    //电影评论数量
    List<Integer> reviewAmounts;

    public ScoreAndReviewAmountVO(List<Double> scores, List<Integer> reviewAmounts) {
        this.scores = scores;
        this.reviewAmounts = reviewAmounts;
    }
}
