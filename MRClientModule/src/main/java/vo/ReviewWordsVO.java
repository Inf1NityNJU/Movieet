package vo;

import java.util.List;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/3.
 */
public class ReviewWordsVO {
    /**
     * 横坐标
     */
    List<String>  keys;

    /**
     * 评论数量
     */
    List<Integer> reviewAmounts;

    public ReviewWordsVO(List<String>  keys, List<Integer> reviewAmounts) {
        this.keys = keys;
        this.reviewAmounts = reviewAmounts;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String>  keys) {
        this.keys = keys;
    }

    public List<Integer> getReviewAmounts() {
        return reviewAmounts;
    }

    public void setReviewAmounts(List<Integer> reviewAmounts) {
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
