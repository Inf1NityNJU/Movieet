package vo;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/3.
 */
public class ReviewCountDayVO {

    /**
     * 横坐标
     */
    String[] keys;


    /**
     * 评论数量
     */
    int[] reviewAmounts;

    public ReviewCountDayVO(String[] keys, int[] reviewAmounts) {
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
        if (o instanceof ReviewCountDayVO) {
            ReviewCountDayVO reviewCountDayVO = (ReviewCountDayVO) o;
            return compareData(reviewCountDayVO);
        }
        return false;
    }

    private boolean compareData(ReviewCountDayVO reviewCountDayVO) {
        return judgeEqual(keys, reviewCountDayVO.getKeys())
                && judgeEqual(reviewAmounts, reviewCountDayVO.getReviewAmounts());
    }
}
