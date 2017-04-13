package vo;

import java.util.List;

/**
 * Created by vivian on 2017/3/21.
 */
public class ScoreAndReviewAmountVO {
    /**
     * 电影名字
     */
    public List<String> names;

    /**
     * 电影的评分
     */
    public List<Double> scores;

    /**
     * 电影评论数量
     */
    public List<Integer> reviewAmounts;

    public ScoreAndReviewAmountVO(List<String> names, List<Double> scores, List<Integer> reviewAmounts) {
        this.names = names;
        this.scores = scores;
        this.reviewAmounts = reviewAmounts;
    }
}
