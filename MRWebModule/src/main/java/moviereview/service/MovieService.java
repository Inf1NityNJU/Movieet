package moviereview.service;

import moviereview.bean.MovieFull;
import moviereview.model.Page;

import java.util.List;

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
    public Page<MovieFull> findMoviesByKeyword(String keyword, String orderBy, String sortType, int size, int page);

    public Page<MovieFull> findMoviesByGenre(String Genre, String orderBy, String sortType, int size, int page);

    public Page<MovieFull> findMoviesByActor(String actor, String orderBy, String sortType, int size, int page);

    public Page<MovieFull> findMoviesByDirector(String Director, String orderBy, String sortType, int size, int page);

    /**
     * 根据最新时间查找电影
     *
     * @param limit 限定数量
     * @return 查询到的电影
     */
    public List<MovieFull> findLatestMovies(int limit);
}
