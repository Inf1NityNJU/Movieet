package moviereview.controller;

import moviereview.model.Page;
import moviereview.model.Review;
import moviereview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 17/3/7.
 */
@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Example
     * <p>
     * xxx/api/user/A16NHMMS2S0Z3F/allreviews
     *
     * @param id 用户 id
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/allreviews",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public List<Review> findAllReviewByUserId(@PathVariable("id") String id) {
        return reviewService.findAllReviewByUserId(id);
    }

    /**
     * Example
     * <p>
     * xxx/api/user/A16NHMMS2S0Z3F/review?page=2&order=date&asc=true
     *
     * @param id       用户 id
     * @param pageNum  起始页码，从0开始
     * @param sortType 排序类型
     * @param asc      是否升序
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/review",
            params = {"page", "order", "asc"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Page<Review> findReviewsByUserId(@PathVariable("id") String id,
                                            @RequestParam(value = "page") int pageNum,
                                            @RequestParam(value = "order") String sortType,
                                            @RequestParam(value = "asc") boolean asc) {
        return reviewService.findAmazonReviewByUserId(id, pageNum, sortType, asc);
    }

    /**
     * Example
     * <p>
     * xxx/api/user/A16NHMMS2S0Z3F/word
     *
     * @param id 用户 id
     * @return
     */
    @ResponseBody
    @RequestMapping(
            value = "/{id}/word",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Map<String, Integer> findWordCountByUserId(@PathVariable("id") String id) {
        return reviewService.findWordCountByUserId(id);
    }

}
