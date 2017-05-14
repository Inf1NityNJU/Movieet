package moviereview.controller;

import moviereview.model.Movie;
import moviereview.model.Page;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Kray on 2017/3/26.
 */

@Controller
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;


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
            value = "/search/",
            params = {"keyword", "orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Page<Movie> findMoviesByKeyword(@RequestParam(value = "keyword") String keyword,
                                           @RequestParam(value = "orderBy") String orderBy,
                                           @RequestParam(value = "order") String sortType,
                                           @RequestParam(value = "size") int size,
                                           @RequestParam(value = "page") int page) {
        return movieService.findMoviesByKeyword(keyword.toLowerCase(), orderBy, sortType, size, page);
    }

//    /**
//     * Example
//     * <p>
//     * xxx/api/movie/search/?tags=action,drama&page=1&order=date&asc=false
//     *
//     * @param tags     电影类别
//     * @param pageNum  起始页码，从0开始
//     * @param sortType 排序类型
//     * @param asc      是否升序
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(
//            value = "/search/",
//            params = {"tags", "page", "order", "asc"},
//            method = RequestMethod.GET,
//            produces = {"application/json; charset=UTF-8"})
//    public Page<Movie> findMovieByTags(@RequestParam(value = "tags") String[] tags,
//                                       @RequestParam(value = "page") int pageNum,
//                                       @RequestParam(value = "order") String sortType,
//                                       @RequestParam(value = "asc") boolean asc) {
//        return movieService.findMoviesByTags(tags, pageNum, sortType, asc);
//    }

}
