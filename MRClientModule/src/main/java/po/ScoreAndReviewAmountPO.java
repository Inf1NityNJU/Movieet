package po;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/21.
 */
public class ScoreAndReviewAmountPO {
    /**
     * 电影的评分
     */
    private List<Double> scores;

    /**
     * 电影评论数量
     */
    private List<Integer> reviewAmounts;

    public ScoreAndReviewAmountPO(List<Double> scores, List<Integer> reviewAmounts) {
        this.scores = scores;
        this.reviewAmounts = reviewAmounts;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
    }

    public List<Integer> getReviewAmounts() {
        return reviewAmounts;
    }

    public void setReviewAmounts(List<Integer> reviewAmounts) {
        this.reviewAmounts = reviewAmounts;
    }
}
