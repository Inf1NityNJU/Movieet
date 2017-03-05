package bl;

import dataservice.ReviewDataServiceStub;
import po.MoviePO;
import po.ReviewPO;
import vo.MovieVO;
import vo.ReviewCountMonthVO;
import vo.ScoreDistributionVO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;

/**
 * Created by vivian on 2017/3/4.
 */
public class Movie {
    private ReviewDataServiceStub reviewDataServiceStub;
    private int totalAmountOfReviews = 0;
    private Iterator<ReviewPO> reviewPOIterator;
    private String id;

    public Movie(ReviewDataServiceStub reviewDataServiceStub) {
        this.reviewDataServiceStub = reviewDataServiceStub;
    }

    /**
     * 根据 id 查找电影
     *
     * @param id
     * @return
     */
    public MovieVO findMovieById(String id) {
        this.id = id;
        MoviePO moviePO = reviewDataServiceStub.findMovieByMovieId(id);
        reviewPOIterator = reviewDataServiceStub.findReviewsByMovieId(id);

        float scoreSum = 0;
        float socreSquareSum = 0;

        //计算评论总数、评分平方和、评分均值
        while (reviewPOIterator.hasNext()) {
            ReviewPO reviewPO = reviewPOIterator.next();
            scoreSum = scoreSum + reviewPO.getScore();
            socreSquareSum = socreSquareSum + (float) Math.pow(reviewPO.getScore(), 2);
            totalAmountOfReviews++;
        }
        float averageScore = scoreSum / totalAmountOfReviews;

        //计算评分方差
        float variance = socreSquareSum - (float) (Math.pow(scoreSum, 2) / totalAmountOfReviews);

        MovieVO movieVO = new MovieVO(id, moviePO.getName(), totalAmountOfReviews, averageScore, variance);
        return movieVO;
    }

    /**
     * 根据电影 id 查找评价分布
     *
     * @param movieId
     * @return
     */
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        reviewPOIterator = reviewDataServiceStub.findReviewsByMovieId(id);
        int[] reviewAmounts = {0, 0, 0, 0, 0};
        while (reviewPOIterator.hasNext()) {
            ReviewPO reviewPO = reviewPOIterator.next();
            reviewAmounts[(int) Math.floor(reviewPO.getScore()) - 1]++;
        }
        ScoreDistributionVO scoreDistributionVO = new ScoreDistributionVO(totalAmountOfReviews, reviewAmounts);
        return scoreDistributionVO;
    }

    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param movieId
     * @return
     */
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId) {
        reviewPOIterator = reviewDataServiceStub.findReviewsByMovieId(id);
        String[] keys = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int[] reviewAmounts = new int[12];
        while (reviewPOIterator.hasNext()) {
            ReviewPO reviewPO = reviewPOIterator.next();
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            reviewAmounts[date.getMonthValue() - 1]++;
        }

        ReviewCountMonthVO reviewCountMonthVO = new ReviewCountMonthVO(keys, reviewAmounts);
        return reviewCountMonthVO;
    }
}
