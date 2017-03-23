package moviereview.service.impl;

import moviereview.dao.MovieDao;
import moviereview.model.Movie;
import moviereview.service.PageService;
import moviereview.util.MovieComparator;
import moviereview.service.MovieService;
import moviereview.util.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService, PageService<Movie> {

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
     * @return  JSON 形式的 String
     */
    public Map<String, Object> findIMDBJsonStringByMovieId(String productId){
        return movieDao.findIMDBJsonStringByMovieId(productId);
    }

    /**
     * 根据特定的条件比较电影
     * @param sortType 排序选项
     */
    public void sortMoviesByComparator(List<Movie> movies, SortType sortType) {
        movies.sort(MovieComparator.sortMoviesBySortType(sortType));
    }

    /**
     * 根据通过搜索电影名称得到相关电影列表
     *
     * @param movieName 电影名称
     * @return 如果电影名称存在，返回具有相同名称的movieVO列表
     * 否则返回null
     */
    public List<Movie> findMoviesByName(String movieName){
        //TODO
        return null;
    }

    /**
     * 得到一共要分多少页
     *
     * @param list  总的列表
     * @return  分页数
     */
    public int getTotalPageNum(List<Movie> list){
        return list.size() / 10;
    }

    /**
     * 返回第 pageNum 页的数据
     *
     * @param list  总的列表
     * @param pageNum  第几页
     * @return
     */
    public List<Movie> getSubListOfPage(List<Movie> list, int pageNum){
        //TODO
        return null;
    }
}
