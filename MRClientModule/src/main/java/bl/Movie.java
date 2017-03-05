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
import java.util.List;

/**
 * Created by vivian on 2017/3/4.
 */
public class Movie {
    private ReviewDataServiceStub reviewDataServiceStub;
    private List<ReviewPO> reviewPOList;

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
        MoviePO moviePO = reviewDataServiceStub.findMovieByMovieId(id);
        this.reviewPOList = reviewDataServiceStub.findReviewsByMovieId(id);

        float scoreSum = 0;
        float socreSquareSum = 0;

        //计算评分平方和、评分均值
        for (int i = 0; i < reviewPOList.size(); i++) {
            scoreSum = scoreSum + reviewPOList.get(i).getScore();
            socreSquareSum = socreSquareSum + (float) Math.pow(reviewPOList.get(i).getScore(), 2);
        }
        float averageScore = scoreSum / reviewPOList.size();

        //计算评分方差
        float variance = socreSquareSum - (float) (Math.pow(scoreSum, 2) / reviewPOList.size());

        MovieVO movieVO = new MovieVO(id, moviePO.getName(), reviewPOList.size(), averageScore, variance);
        return movieVO;
    }

    /**
     * 根据电影 id 查找评价分布
     *
     * @param movieId
     * @return
     */
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        reviewPOList = reviewDataServiceStub.findReviewsByMovieId(movieId);
        int[] reviewAmounts = {0, 0, 0, 0, 0};
        for (int i = 0; i < reviewPOList.size(); i++) {
            reviewAmounts[(int) Math.floor(reviewPOList.get(i).getScore()) - 1]++;
        }
        ScoreDistributionVO scoreDistributionVO = new ScoreDistributionVO(reviewPOList.size(), reviewAmounts);
        return scoreDistributionVO;
    }

    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param movieId
     * @return
     */
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId) {
        reviewPOList = reviewDataServiceStub.findReviewsByMovieId(movieId);
        String[] keys = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int[] reviewAmounts = new int[12];

        for (int i = 0; i < reviewPOList.size(); i++) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPOList.get(i).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            reviewAmounts[date.getMonthValue() - 1]++;
        }
        ReviewCountMonthVO reviewCountMonthVO = new ReviewCountMonthVO(keys, reviewAmounts);
        return reviewCountMonthVO;
    }
}
