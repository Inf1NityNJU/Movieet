package vo;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/3.
 */
public class ReviewWordsVO {
    /**
     * 横坐标
     */
    String[] keys;

    /**
     * 评论数量
     */
    int[] reviewAmounts;

    public ReviewWordsVO(String[] keys, int[] reviewAmounts) {
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
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReviewWordsVO) {
            ReviewWordsVO reviewWordsVO = (ReviewWordsVO) o;
            return compareData(reviewWordsVO);
        }
        return false;
    }

    private boolean compareData(ReviewWordsVO reviewWordsVO) {
        return judgeEqual(keys, reviewWordsVO.getKeys())
                && judgeEqual(reviewAmounts,reviewWordsVO.getReviewAmounts());
    }
}
