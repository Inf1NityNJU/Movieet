package vo;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/4.
 */
public class ReviewCountYearVO {

    /**
     * 横坐标
     */
    String[] keys;


    /**
     * 评论数量
     */
    int[] reviewAmounts;

    public ReviewCountYearVO(String[] keys, int[] reviewAmounts) {
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
        if (o instanceof ReviewCountYearVO) {
            ReviewCountYearVO reviewCountYearVO = (ReviewCountYearVO) o;
            return compareData(reviewCountYearVO);
        }
        return false;
    }

    private boolean compareData(ReviewCountYearVO reviewCountYearVO) {
        return judgeEqual(keys, reviewCountYearVO.getKeys())
                && judgeEqual(reviewAmounts, reviewCountYearVO.getReviewAmounts());
    }
}
