package vo;

/**
 * Created by vivian on 2017/3/24.
 */
public class MovieStatisticsVO {
    /**
     * 评价数量
     */
    public int amountOfReview;

    /**
     * 平均分数
     */
    public double averageScore;

    /**
     * 第一条评论的日期
     */
    public String firstReviewDate;

    /**
     * 最后一条评论的日期
     */
    public String lastReviewDate;

    public MovieStatisticsVO(int amountOfReview, double averageScore, String firstReviewDate, String lastReviewDate) {
        this.amountOfReview = amountOfReview;
        this.averageScore = averageScore;
        this.firstReviewDate = firstReviewDate;
        this.lastReviewDate = lastReviewDate;
    }
}
