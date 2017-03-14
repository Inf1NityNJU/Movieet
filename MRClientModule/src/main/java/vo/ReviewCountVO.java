package vo;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/10.
 */
public class ReviewCountVO {
    /**
     * 横坐标
     */
    String[] keys;


    /**
     * 评论数量
     */
    int[] reviewAmounts;

    public ReviewCountVO(){

    }
    public ReviewCountVO(String[] keys, int[] reviewAmounts) {
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
        if (o instanceof ReviewCountVO) {
            ReviewCountVO ReviewCountVO = (ReviewCountVO) o;
            return compareData(ReviewCountVO);
        }
        return false;
    }

    private boolean compareData(ReviewCountVO ReviewCountVO) {
        return judgeEqual(keys, ReviewCountVO.getKeys())
                && judgeEqual(reviewAmounts, ReviewCountVO.getReviewAmounts());
    }
}
