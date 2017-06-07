package moviereview.service;

import moviereview.bean.GenreCountBean;
import moviereview.bean.MovieFull;
import moviereview.bean.MovieMini;
import moviereview.model.Page;

import java.util.List;

/**
 * Created by Kray on 2017/3/7.
 */
public interface MovieService {

    /**
     * @param keyword  关键字
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    public Page<MovieMini> findMoviesByKeyword(String keyword, String orderBy, String sortType, int size, int page);

    /**
     * @param Genre    类别
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    public Page<MovieMini> findMoviesByGenre(String Genre, String orderBy, String sortType, int size, int page);

    /**
     * @param actor    演员
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    public Page<MovieMini> findMoviesByActor(String actor, String orderBy, String sortType, int size, int page);

    /**
     * @param Director 导演
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    public Page<MovieMini> findMoviesByDirector(String Director, String orderBy, String sortType, int size, int page);

    public List<MovieMini> findMoviesByDirector(String Director);

    public List<MovieMini> findMoviesByActor(String actor);

    /**
     * 根据最新时间查找电影
     *
     * @param limit 限定数量
     * @return 查询到的电影
     */
    public List<MovieMini> findLatestMovies(int limit);

//    public List<Actor> findActorsByIdMovie(String idmovie);
//
//    public List<Director> findDirectorsByIdMovie(String idmovie);
//
//    public List<GenreBean> findGenreIdByIdMovie(String idmovie);

    /**
     * 根据 id 查找电影
     *
     * @param movieid
     * @return 完整电影信息
     */
    public MovieFull findMovieFullByMovieID(int movieid);

    /**
     * 根据 id 查找电影
     *
     * @param movieid
     * @return 简明电影信息
     */
    public MovieMini findMovieMiniByMovieID(int movieid);
//    /**
//     * 得到类型信息
//     *
//     * @return
//     */
//    public GenreInfo findGenreInfo(String genre, int startYear);

    /**
     * 获得电影排名（根据豆瓣电影）
     *
     * @param size 返回的电影个数
     * @return电影列表
     */
    public Page<MovieMini> getMovieRankCN(int size);

    /**
     * 获得电影排名（根据imdb电影）
     *
     * @param size 返回的电影个数
     * @return电影列表
     */
    public Page<MovieMini> getMovieRankFR(int size);

    /**
     * 类型数量图(foreugn/domestic)
     * @return 类型数量信息列表
     */
    public List<GenreCountBean> genreCount();
}
