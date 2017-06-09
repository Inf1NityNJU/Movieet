package moviereview.service;

import moviereview.bean.*;
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
     * @return 电影列表
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
     * 得到漏斗图的数据
     *
     * @return 漏斗图的数据
     */
    public List<ScorePyramid> getScorePyramid();

    /*
     * 类型数量图(foreign/domestic)
     * @return 类型数量信息列表
     */
    public List<GenreCountBean> genreCount();


    /**
     * 某类型随着年份的所占比和平均分
     *
     * @param genreId 类型ID
     * @return GenreYearBean列表
     */
    public List<GenreYearBean> genreInYear(int genreId);

    /**
     * 得到国家根据年份不同的分数列表
     *
     * @param countryid 国家 id
     * @return
     */
    public List<CountryScoreInYearBean> getCountryScoreInYearOfCountry(int countryid);

    /**
     * 得到国家高于/低于豆瓣/IMDB 的分数列表
     *
     * @param countryid
     * @return
     */
    public List<CountryCountBean> getCountryCountOfCountry(int countryid);

}
