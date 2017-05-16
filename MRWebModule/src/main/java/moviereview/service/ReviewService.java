package moviereview.service;

import moviereview.model.Page;
import moviereview.model.ReviewIMDB;

/**
 * Created by Kray on 2017/5/16.
 */
public interface ReviewService {
    /**
     * 通过电影 ID 寻找该电影的分页 IMDB 评论
     *
     * @param movieid 电影 ID
     * @return 评论 list
     */
    public Page<ReviewIMDB> findIMDBReviewByMovieId(String movieid, int page, String sortType, String asc);
}
