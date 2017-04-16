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
     * 第一条评论的日期
     */
    public String firstReviewDate;

    /**
     * 最后一条评论的日期
     */
    public String lastReviewDate;

    public MovieStatisticsVO(int amountOfReviewFromAmazon, int amountOfReviewFromImdb, double averageScore, String firstReviewDate, String lastReviewDate) {
        this.amountOfReviewFromAmazon = amountOfReviewFromAmazon;
        this.amountOfReviewFromImdb = amountOfReviewFromImdb;
        this.averageScore = averageScore;
        this.firstReviewDate = firstReviewDate;
        this.lastReviewDate = lastReviewDate;
    }
}
