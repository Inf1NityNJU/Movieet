package bl;

import datastub.ReviewDataServiceStub;
import po.MoviePO;
import po.ReviewPO;
import util.LimitedHashMap;
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
//    private static HashMap<String, List<ReviewPO>> reviewPOListHashMap = new HashMap<String, List<ReviewPO>>();
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);

    public Movie(ReviewDataServiceStub reviewDataServiceStub) {
        this.reviewDataServiceStub = reviewDataServiceStub;
    }

    /**
     * 根据 movieId 查找电影
     *
     * @param movieId 电影ID
     * @return
     */
    public MovieVO findMovieById(String movieId) {
        MoviePO moviePO = reviewDataServiceStub.findMovieByMovieId(movieId);
        getReviewPOList(movieId);

        float scoreSum = 0;
        float scoreSquareSum = 0;

        //计算评分平方和、评分均值
        for (int i = 0; i < reviewPOList.size(); i++) {
            scoreSum = scoreSum + reviewPOList.get(i).getScore();
            scoreSquareSum = scoreSquareSum + (float) Math.pow(reviewPOList.get(i).getScore(), 2);
        }
        float averageScore = scoreSum / reviewPOList.size();

        //计算评分方差
        float variance = scoreSquareSum - (float) (Math.pow(scoreSum, 2) / reviewPOList.size());

        MovieVO movieVO = new MovieVO(movieId, moviePO.getName(), reviewPOList.size(), averageScore, variance);
        return movieVO;
    }

    /**
     * 根据电影 movieId 查找评价分布
     *
     * @param movieId 电影ID
     * @return
     */
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        getReviewPOList(movieId);

        int[] reviewAmounts = {0, 0, 0, 0, 0};
        for (int i = 0; i < reviewPOList.size(); i++) {
            reviewAmounts[(int) Math.floor(reviewPOList.get(i).getScore()) - 1]++;
        }
        ScoreDistributionVO scoreDistributionVO = new ScoreDistributionVO(reviewPOList.size(), reviewAmounts);
        return scoreDistributionVO;
    }

    /**
     * 根据电影 movieId 查找每月评论数量
     *
     * @param movieId 电影ID
     * @return
     */
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId) {
        getReviewPOList(movieId);

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

    private List<ReviewPO> getReviewPOList(String movieId) {
        if (!reviewPOLinkedHashMap.containsKey(movieId)) {
            reviewPOList = reviewDataServiceStub.findReviewsByMovieId(movieId);
            reviewPOLinkedHashMap.put(movieId, reviewPOList);

        } else {
            reviewPOList = reviewPOLinkedHashMap.get(movieId);
        }
        return reviewPOList;
    }
}
