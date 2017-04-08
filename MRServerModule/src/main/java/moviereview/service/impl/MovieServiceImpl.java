package moviereview.service.impl;

import moviereview.dao.MovieDao;
import moviereview.model.Movie;
import moviereview.model.Page;
import moviereview.util.MovieSortType;
import moviereview.util.ReviewSortType;
import moviereview.util.Sort;
import moviereview.util.comparator.MovieComparatorFactory;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

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
     * @param keyword 电影关键字
     * @param sortType
     *@param asc @return 如果电影名称存在，返回具有相同名称的movieVO列表
     * 否则返回null
     */
    public Page<Movie> findMoviesByKeyword(String keyword, int page, String sortType, boolean asc) {
        Sort sort = new Sort(sortType, asc);
        List<Movie> movies = (ArrayList<Movie>) movieDao.findMoviesByKeyword(keyword);
        movies.sort(MovieComparatorFactory.sortMoviesBySortType(sort.toString()));
        return new Page<Movie>(
                page,
                10,
                sort.getOrder(),
                sort.getAsc(),
                movies.size() + "",
                movies.subList(page * 10, Math.min((page + 1) * 10, movies.size())));
    }

    /**
     * 根据通过搜索电影分类tag得到按照时间排序的相关电影列表
     *
     * @param tag            电影分类tag
     * @param reviewSortType 决定时间按由近到远还是由远到近排序
     * @return 如果属于该电影分类tag的电影存在，返回该分类按照时间排序的movieVO列表
     * 否则返回null
     */
    public Page findMoviesByTagInPage(String tag, ReviewSortType reviewSortType, int page) {
        //TODO
        return null;
    }
}
