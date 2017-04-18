package vo;

/**
 * Created by vivian on 2017/3/24.
 */
public class MovieStatisticsVO {
    /**
     * Amazon评价数量
     */
    public int amountOfReviewFromAmazon;

    /**
     * Imdb评价数量
     */
    public int amountOfReviewFromImdb;

    /**
     * 平均分数
     */
    public double averageScore;

    /**
     * 评分方差
     */
    public double variance;

    /**
     * 第一条评论的日期
     */
    public String firstReviewDate;

    /**
     * 最后一条评论的日期
     */
    public String lastReviewDate;

    public MovieStatisticsVO(int amountOfReviewFromAmazon, int amountOfReviewFromImdb, double averageScore, double variance, String firstReviewDate, String lastReviewDate) {
        this.amountOfReviewFromAmazon = amountOfReviewFromAmazon;
        this.amountOfReviewFromImdb = amountOfReviewFromImdb;
        this.averageScore = averageScore;
        this.variance = variance;
        this.firstReviewDate = firstReviewDate;
        this.lastReviewDate = lastReviewDate;
    }
}
