package vo;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/3.
 */
public class ReviewCountMonthVO {

    /**
     * 横坐标
     */
    String[] keys;


    /**
     * 评论数量
     */
    int[] reviewAmounts;

    public ReviewCountMonthVO(String[] keys, int[] reviewAmounts) {
        this.keys = keys;
        this.reviewAmounts = reviewAmounts;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public int[] getReviewAmounts() {
        return reviewAmounts;
    }

    public void setReviewAmounts(int[] reviewAmounts) {
        this.reviewAmounts = reviewAmounts;
    }

    @Override
    public int hashCode() {
        return reviewAmounts[0];
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReviewCountMonthVO) {
            ReviewCountMonthVO reviewCountMonthVO = (ReviewCountMonthVO) o;
            return compareData(reviewCountMonthVO);
        }
        return false;
    }

    private boolean compareData(ReviewCountMonthVO reviewCountMonthVO) {
        return judgeEqual(keys, reviewCountMonthVO.getKeys())
                && judgeEqual(reviewAmounts, reviewCountMonthVO.getReviewAmounts());
    }
}
