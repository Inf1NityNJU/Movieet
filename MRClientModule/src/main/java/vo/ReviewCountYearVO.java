package vo;

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
}
