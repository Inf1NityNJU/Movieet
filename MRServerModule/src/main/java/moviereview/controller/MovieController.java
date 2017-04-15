package moviereview.controller;

import moviereview.model.*;
import moviereview.service.MovieService;
import moviereview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/26.
 */

@Controller
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    /**
     * Example
     * <p>
     * xxx/api/movie/B0014ERKO0
     *
     * @param id 电影 ID
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)
    public Movie findMovieByMovieId(@PathVariable("id") String id) {
        return movieService.findMovieByMovieId(id);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/B0014ERKO0/imdb
     *
     * @param id 电影 ID
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/imdb",
            method = RequestMethod.GET)
    public Map<String, Object> findIMDBJsonStringByMovieId(@PathVariable("id") String id) {
        return movieService.findIMDBJsonStringByMovieId(id);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/B0014ERKO0/imdb/review?page=2&order=date&asc=true
     *
     * @param id       电影 id
     * @param pageNum  起始页码，从0开始
     * @param sortType 排序类型
     * @param asc      是否升序
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/imdb/review",
            params = {"page", "order", "asc"},
            method = RequestMethod.GET)
    public Page<Review> findIMDBReviewByMovieId(@PathVariable(value = "id") String id,
                                                @RequestParam(value = "page") int pageNum,
                                                @RequestParam(value = "order") String sortType,
                                                @RequestParam(value = "asc") boolean asc) {
        return reviewService.findIMDBReviewByMovieId(id, pageNum, sortType, asc);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/B0014ERKO0/review?page=2&order=date&asc=true
     *
     * @param id       电影 id
     * @param pageNum  起始页码，从0开始
     * @param sortType 排序类型
     * @param asc      是否升序
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/review",
            params = {"page", "order", "asc"},
            method = RequestMethod.GET)
    public Page<Review> findReviewByMovieId(@PathVariable("id") String id,
                                            @RequestParam(value = "page") int pageNum,
                                            @RequestParam(value = "order") String sortType,
                                            @RequestParam(value = "asc") boolean asc) {
        return reviewService.findAmazonReviewByMovieId(id, pageNum, sortType, asc);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/B0014ERKO0/allreviews/amazon
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/allreviews/amazon",
            method = RequestMethod.GET)
    public List<Review> findAllAmazonReviewByMovieId(@PathVariable("id") String id) {
        return reviewService.findAllAmazonReviewById(id);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/B0014ERKO0/allreviews/imdb
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/allreviews/imdb",
            method = RequestMethod.GET)
    public List<Review> findAllIMDBReviewByMovieId(@PathVariable("id") String id) {
        return reviewService.findAllIMDBReviewById(id);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/B0014ERKO0/word
     *
     * @param id 电影 id
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/word",
            method = RequestMethod.GET)
    public Map<String, Integer> findWordCountByMovieId(@PathVariable("id") String id) {
        return reviewService.findWordCountByMovieId(id);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/search/?keyword=test&page=1&order=date&asc=false
     *
     * @param keyword  关键字
     * @param pageNum  起始页码，从0开始
     * @param sortType 排序类型
     * @param asc      是否升序
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/search/",
            params = {"keyword", "page", "order", "asc"},
            method = RequestMethod.GET)
    public Page<Movie> findMoviesByKeyword(@RequestParam(value = "keyword") String keyword,
                                           @RequestParam(value = "page") int pageNum,
                                           @RequestParam(value = "order") String sortType,
                                           @RequestParam(value = "asc") boolean asc) {
        return movieService.findMoviesByKeyword(keyword, pageNum, sortType, asc);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/search/?tags=action,drama&page=1&order=date&asc=false
     *
     * @param tags     电影类别
     * @param pageNum  起始页码，从0开始
     * @param sortType 排序类型
     * @param asc      是否升序
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/search/",
            params = {"tags", "page", "order", "asc"},
            method = RequestMethod.GET)
    public Page<Movie> findMovieByTags(@RequestParam(value = "tags") String[] tags,
                                       @RequestParam(value = "page") int pageNum,
                                       @RequestParam(value = "order") String sortType,
                                       @RequestParam(value = "asc") boolean asc) {
        return movieService.findMoviesByTags(tags, pageNum, sortType, asc);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/scoreandreview/?tags=action,drama
     *
     * @param tags
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/scoreandreview/",
            params = {"tags"},
            method = RequestMethod.GET)
    public ScoreAndReviewAmount findScoreAndReviewAmountByTags(@RequestParam(value = "tags") String[] tags) {
        return movieService.findScoreAndReviewAmountByTags(tags);
    }

    /**
     * Example
     * <p>
     * xxx/api/movie/genre
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/genre",
            method = RequestMethod.GET
    )
    public MovieGenre findMovieGenreCount() {
        return movieService.findMovieGenreCount();
    }

}
