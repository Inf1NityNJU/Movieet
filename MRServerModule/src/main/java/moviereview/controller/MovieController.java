package moviereview.controller;

import moviereview.model.Movie;
import moviereview.model.Review;
import moviereview.service.MovieService;
import moviereview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Movie findMovieByMovieId(@PathVariable("id") String id) {
        return movieService.findMovieByMovieId(id);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}/imdb", method = RequestMethod.GET)
    public Map<String, Object> findIMDBJsonStringByMovieId(@PathVariable("id") String id) {
        return movieService.findIMDBJsonStringByMovieId(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String movie() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/{id}/review", method = RequestMethod.GET)
    public List<Review> findReviewByMovieId(@PathVariable("id") String id) {
        return reviewService.findReviewsByMovieId(id);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}/word", method = RequestMethod.GET)
    public Map<String, Integer> findWordCountByMovieId(@PathVariable("id") String id) {
        return reviewService.findWordCountByMovieId(id);
    }
}
