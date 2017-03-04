package vo;

/**
 * Created by vivian on 2017/3/3.
 */
public class MovieVO {
    /**
     * id
     */
    String id;

    /**
     * 电影名称
     */
    String name;

    /**
     * 评价数量
     */
    int amountOfReview;

    /**
     * 平均分数
     */
    float averageScore;

    /**
     * 评分方差
     */
    float variance;

    //    ScoreDistributionVO scoreDistributionVO;
//    ReviewCountHourVO reviewCountHourVO;
//    ReviewCountMonthVO reviewCountMonthVO;

    public MovieVO(String id, String name, int amountOfReview, float averageScore, float variance) {
        this.id = id;
        this.name = name;
        this.amountOfReview = amountOfReview;
        this.averageScore = averageScore;
        this.variance = variance;
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

    public float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public float getVariance() {
        return variance;
    }

    public void setVariance(float variance) {
        this.variance = variance;
    }
}
