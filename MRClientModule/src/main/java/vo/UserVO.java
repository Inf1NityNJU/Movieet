package vo;

import javafx.scene.image.Image;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/3.
 */
public class UserVO {
    /**
     * 用户ID
     */
    public String id;

    /**
     * 用户姓名
     */
    public String name;

    /**
     * 用户头像
     */
    public Image avatar;

    /**
     * 用户评论数量
     */
    public int reviewAmounts;

    /**
     * 第一条评论的日期
     */
    public String firstReviewDate;

    /**
     * 最后一条评论的日期
     */
    public String lastReviewDate;

    public UserVO(String id, String name, Image avatar, int reviewAmounts, String firstReviewDate, String lastReviewDate) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
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
        return judgeEqual(id, userVO.id)
                && judgeEqual(reviewAmounts, userVO.reviewAmounts)
                && judgeEqual(firstReviewDate, userVO.firstReviewDate)
                && judgeEqual(lastReviewDate, userVO.lastReviewDate);
    }
}
