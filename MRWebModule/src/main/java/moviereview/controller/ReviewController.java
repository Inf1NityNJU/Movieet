package moviereview.controller;

import moviereview.model.Page;
import moviereview.model.ReviewIMDB;
import moviereview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kray on 2017/5/16.
 */

@Controller
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @ResponseBody
    @RequestMapping(
            value = "",
            params = {"id", "page", "orderBy", "order"},
            method = RequestMethod.GET)
    public Page<ReviewIMDB> findIMDBReviewByMovieId(@PathVariable(value = "id") String id,
                                                    @RequestParam(value = "page") int pageNum,
                                                    @RequestParam(value = "orderBy") String sortType,
                                                    @RequestParam(value = "order") boolean asc) {
        return reviewService.findIMDBReviewByMovieId(id, pageNum, sortType, asc);
    }
}
