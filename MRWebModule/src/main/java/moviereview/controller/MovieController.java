package moviereview.controller;

import moviereview.bean.MovieFull;
import moviereview.bean.MovieMini;
import moviereview.model.Page;
import moviereview.service.MovieService;
import moviereview.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Kray on 2017/3/26.
 */

@Controller
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RecommendService recommendService;

    /**
     * @param keyword  关键字
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/search",
            params = {"keyword", "orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Page<MovieMini> findMoviesByKeyword(@RequestParam(value = "keyword") String keyword,
                                               @RequestParam(value = "orderBy") String orderBy,
                                               @RequestParam(value = "order") String sortType,
                                               @RequestParam(value = "size") int size,
                                               @RequestParam(value = "page") int page) {
        return movieService.findMoviesByKeyword(keyword, orderBy, sortType, size, page);
    }

    /**
     * @param actor    演员
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/search",
            params = {"actor", "orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Page<MovieMini> findMoviesByActor(@RequestParam(value = "actor") String actor,
                                             @RequestParam(value = "orderBy") String orderBy,
                                             @RequestParam(value = "order") String sortType,
                                             @RequestParam(value = "size") int size,
                                             @RequestParam(value = "page") int page) {
        return movieService.findMoviesByActor(actor, orderBy, sortType, size, page);
    }

    /**
     * @param genre    类型
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/search",
            params = {"genre", "orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Page<MovieMini> findMoviesByGenre(@RequestParam(value = "genre") String genre,
                                             @RequestParam(value = "orderBy") String orderBy,
                                             @RequestParam(value = "order") String sortType,
                                             @RequestParam(value = "size") int size,
                                             @RequestParam(value = "page") int page) {
        return movieService.findMoviesByGenre(genre, orderBy, sortType, size, page);
    }

    /**
     * @param director 导演
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/search",
            params = {"director", "orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Page<MovieMini> findMoviesByDirector(@RequestParam(value = "director") String director,
                                                @RequestParam(value = "orderBy") String orderBy,
                                                @RequestParam(value = "order") String sortType,
                                                @RequestParam(value = "size") int size,
                                                @RequestParam(value = "page") int page) {
        return movieService.findMoviesByDirector(director, orderBy, sortType, size, page);
    }

    /**
     * @param limit 限制几个
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/latest",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public List<MovieMini> findLatestMovies(@RequestParam(value = "size") int limit) {
        return movieService.findLatestMovies(limit);
    }

    /**
     * @param movieid 电影 ID
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/",
            params = {"id"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public MovieFull findMovieByMovieID(@RequestParam(value = "id") String movieid) {
        return movieService.findMovieByMovieID(Integer.parseInt(movieid));
    }


    /**
     * 寻找相似电影
     *
     * @param movieid 指定电影id
     * @param limit 需要的数量
     * @return 相似电影列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/similar",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public List<MovieMini> findSimilarMovie(@PathVariable("id") String movieid,
                                        @RequestParam(value = "size") int limit) {
        return recommendService.findSimilarMovie(Integer.parseInt(movieid), limit);
    }
}
