package moviereview.service.impl;

import moviereview.dao.MovieDao;
import moviereview.dao.ReviewDao;
import moviereview.model.Movie;
import moviereview.model.MovieGenre;
import moviereview.model.Page;
import moviereview.model.ScoreAndReviewAmount;
import moviereview.service.ReviewService;
import moviereview.util.MovieSortType;
import moviereview.util.ReviewSortType;
import moviereview.util.Sort;
import moviereview.util.comparator.MovieComparatorFactory;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private ReviewDao reviewDao;

    /**
     * 通过电影ID寻找指定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public Movie findMovieByMovieId(String productId) {
        return movieDao.findMovieByMovieId(productId);
    }


    /**
     * 通过电影 ID 寻找该电影在 IMDB 上的 JSON 串
     *
     * @param productId 电影 ID
     * @return JSON 形式的 String
     */
    public Map<String, Object> findIMDBJsonStringByMovieId(String productId) {
        return movieDao.findIMDBJsonStringByMovieId(productId);
    }

    /**
     * 根据通过搜索电影名称得到相关电影列表
     *
     * @param keyword  电影关键字
     * @param sortType
     * @param asc      @return 如果电影名称存在，返回具有相同名称的movieVO列表
     *                 否则返回null
     */
    public Page<Movie> findMoviesByKeyword(String keyword, int page, String sortType, boolean asc) {
        Sort sort = new Sort(sortType, asc);
        ArrayList<Movie> movies = (ArrayList<Movie>) movieDao.findMoviesByKeyword(keyword);
        movies.sort(MovieComparatorFactory.sortMoviesBySortType(sort.toString()));

        if (page * 10 > movies.size()) {
            return new Page<Movie>();
        } else {
            return new Page<Movie>(
                    page,
                    10,
                    sort.getOrder(),
                    sort.getAsc(),
                    movies.size() + "",
                    movies.subList(page * 10, Math.min((page + 1) * 10, movies.size())));
        }
    }

    /**
     * 根据通过搜索电影分类tag得到按照时间排序的相关电影列表
     *
     * @param tags          电影分类tag
     * @param movieSortType 决定时间按由近到远还是由远到近排序
     * @return 如果属于该电影分类tag的电影存在，返回该分类按照时间排序的movieVO列表
     * 否则返回null
     */
    public Page<Movie> findMoviesByTags(String[] tags, int page, String movieSortType, boolean asc) {
        Sort sort = new Sort(movieSortType, asc);
        Set<Movie> tempSet = new HashSet<>();
        for (String tag : tags) {
            tempSet.addAll(movieDao.findMoviesByTag(tag.toUpperCase()));
        }

        ArrayList<Movie> movies = new ArrayList<>();
        movies.addAll(tempSet);
        movies.sort(MovieComparatorFactory.sortMoviesBySortType(sort.toString()));

        if (page * 10 > movies.size()) {
            return new Page<Movie>();
        } else {
            return new Page<Movie>(
                    page,
                    10,
                    sort.getOrder(),
                    sort.getAsc(),
                    movies.size() + "",
                    movies.subList(page * 10, Math.min((page + 1) * 10, movies.size())));
        }
    }

    /**
     * 根据 tags 得到电影评分和评论数量的关系
     *
     * @param tags
     * @return
     */
    public ScoreAndReviewAmount findScoreAndReviewAmountByTags(String[] tags) {
        return movieDao.findScoreAndReviewAmountByTags(tags);
    }

    /**
     * 得到电影分类和电影数量的关系
     *
     * @return
     */
    public MovieGenre findMovieGenreCount() {
        return movieDao.findMovieGenreCount();
    }
}
