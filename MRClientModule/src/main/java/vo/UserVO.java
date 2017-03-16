package vo;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/3.
 */
public class UserVO {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户评论数量
     */
    private int reviewAmounts;

    /**
     * 第一条评论的日期
     */
    private String firstReviewDate;

    /**
     * 最后一条评论的日期
     */
    private String lastReviewDate;

    public UserVO(String id, int reviewAmounts, String firstReviewDate, String lastReviewDate) {
        this.id = id;
        this.reviewAmounts = reviewAmounts;
        this.firstReviewDate = firstReviewDate;
        this.lastReviewDate = lastReviewDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReviewAmounts() {
        return reviewAmounts;
    }

    public void setReviewAmounts(int reviewAmounts) {
        this.reviewAmounts = reviewAmounts;
    }

    public String getFirstReviewDate() {
        return firstReviewDate;
    }

    public void setFirstReviewDate(String firstReviewDate) {
        this.firstReviewDate = firstReviewDate;
    }

    public String getLastReviewDate() {
        return lastReviewDate;
    }

    public void setLastReviewDate(String lastReviewDate) {
        this.lastReviewDate = lastReviewDate;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserVO) {
            UserVO userVO = (UserVO) o;
            return compareData(userVO);
        }
        return false;
    }

    private boolean compareData(UserVO userVO) {
        return judgeEqual(id, userVO.getId())
                && judgeEqual(reviewAmounts, userVO.getReviewAmounts())
                && judgeEqual(firstReviewDate, userVO.getFirstReviewDate())
                && judgeEqual(lastReviewDate, userVO.getLastReviewDate());
    }
}
