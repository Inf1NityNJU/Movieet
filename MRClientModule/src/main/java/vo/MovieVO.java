package vo;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/3.
 */
public class MovieVO {
    /**
     * id
     */
    private String id;

    /**
     * 电影名称
     */
    private String name;

    /**
     * 评价数量
     */
    private int amountOfReview;

    /**
     * 平均分数
     */
    private double averageScore;

    /**
     * 评分方差
     */
    private double variance;

    /**
     * 第一条评论的日期
     */
    private String firstReviewDate;

    /**
     * 最后一条评论的日期
     */
    private String lastReviewDate;

    //    ScoreDistributionVO scoreDistributionVO;
//    ReviewCountDayVO reviewCountHourVO;
//    ReviewCountMonthVO reviewCountMonthVO;

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

    public MovieVO(String id, String name, int amountOfReview, double averageScore, double variance, String firstReviewDate, String lastReviewDate) {
        this.id = id;
        this.name = name;
        this.amountOfReview = amountOfReview;
        this.averageScore = averageScore;
        this.variance = variance;
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

    public int getAmountOfReview() {
        return amountOfReview;
    }

    public void setAmountOfReview(int amountOfReview) {
        this.amountOfReview = amountOfReview;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MovieVO) {
            MovieVO movieVO = (MovieVO) o;
            return compareData(movieVO);
        }
        return false;
    }

    private boolean compareData(MovieVO movieVO) {
        return judgeEqual(id, movieVO.getId())
                && judgeEqual(name, movieVO.getName())
                && judgeEqual(amountOfReview, movieVO.getAmountOfReview())
                && judgeEqual(averageScore, movieVO.getAverageScore())
                && judgeEqual(variance, movieVO.getVariance())
                && judgeEqual(firstReviewDate, movieVO.getFirstReviewDate())
                &&judgeEqual(lastReviewDate, movieVO.getLastReviewDate());
    }


}
