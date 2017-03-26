package moviereview.controller;

import moviereview.dao.impl.DataConst;
import moviereview.model.Movie;
import moviereview.model.MovieJson;
import moviereview.model.Review;
import moviereview.service.MovieService;
import moviereview.service.ReviewService;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
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

    @ResponseBody
    @RequestMapping(value = "/{id}/review", method = RequestMethod.GET)
    public List<Review> findReviewsByUserId(@PathVariable("id") String id) {
        return reviewService.findReviewsByUserId(id);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}/word", method = RequestMethod.GET)
    public Map<String, Integer> findWordCountByUserId(@PathVariable("id") String id) {
        return reviewService.findWordCountByUserId(id);
    }

}
