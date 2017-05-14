package moviereview.service;

import moviereview.model.Movie;
import moviereview.model.Page;

import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
public interface MovieService {

    /**
     * @param keyword   关键字
     * @param orderBy   按什么排序
     * @param sortType  asc 还是 desc
     * @param size      每页大小
     * @param page      第几页
     * @return  Movie 分页列表
     */
    public Page<Movie> findMoviesByKeyword(String keyword, String orderBy, String sortType, int size, int page);

//    /**
//     * 根据通过搜索电影分类tag得到按照时间排序的相关电影列表
//     *
//     * @param tags          电影分类tag
//     * @param movieSortType 决定时间按由近到远还是由远到近排序
//     * @return 如果属于该电影分类tag的电影存在，返回该分类按照时间排序的movieVO列表
//     * 否则返回null
//     */
//    public Page<Movie> findMoviesByTags(String[] tags, int page, String movieSortType, boolean asc);

}
