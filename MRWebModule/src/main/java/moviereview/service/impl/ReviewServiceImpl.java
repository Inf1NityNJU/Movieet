package moviereview.service.impl;

import moviereview.model.Movie;
import moviereview.model.Page;
import moviereview.model.ReviewIMDB;
import moviereview.repository.MovieRepository;
import moviereview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Kray on 2017/5/16.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * 通过电影 ID 寻找该电影的分页 IMDB 评论
     *
     * @param movieid 电影 ID
     * @return 评论 list
     */
    public Page<ReviewIMDB> findIMDBReviewByMovieId(String movieid, int page, String sortType, boolean asc){
        Movie movie = movieRepository.findMovieByID(movieid);
//        Sort sort = new Sort(sortType, asc);
//        ArrayList<Review> reviews = (ArrayList<Review>) reviewDao.findIMDBReviewByMovieId(productId, -1);   //-1代表返回本地所有评论
//        if (reviews == null) {
//            return new Page<Review>();
//        }
//        reviews.sort(ReviewComparatorFactory.sortReviewsBySortType(sort.toString()));
//
//        String count = reviewDao.findIMDBReviewCountByMovieId(productId);
//        if (count.equals("-1") || count.equals("") || count == null) {
//            return new Page<Review>();
//        }
//        int totalImdbReviewCount = Integer.parseInt(count);
//
//        if (page * 10 > totalImdbReviewCount) {
//            return new Page<Review>();
//        } else {
//            return new Page<Review>(
//                    page,
//                    10,
//                    sort.getOrder(),
//                    sort.getAsc(),
//                    count,
//                    //在线
////                    reviews);
//                    //本地
//                    reviews.subList(page * 10, Math.min((page + 1) * 10, reviews.size())));
//        }
        return null;
    }
}
