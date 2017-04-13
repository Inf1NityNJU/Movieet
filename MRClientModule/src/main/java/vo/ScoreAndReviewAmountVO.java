package vo;

import java.util.List;

/**
 * Created by vivian on 2017/3/21.
 */
public class ScoreAndReviewAmountVO {
    //电影的评分
    public List<Double> scores;

    //电影评论数量
    public List<Integer> reviewAmounts;

    public ScoreAndReviewAmountVO(List<Double> scores, List<Integer> reviewAmounts) {
        this.scores = scores;
        this.reviewAmounts = reviewAmounts;
    }
}
