package moviereview.controller;

import moviereview.model.Movie;
import moviereview.model.Review;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 17/3/7.
 */
@Controller
@RequestMapping("/api")
public class JsonController {

    @Autowired
    private MovieService movieService;

    @ResponseBody
    @RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
    public Movie findMovieByMovieId(@PathVariable("id") String id) {
        return movieService.findMovieByMovieId(id);
    }

    @ResponseBody
    @RequestMapping(value = "/review/user/{id}", method = RequestMethod.GET)
    public List<Review> findReviewsByUserId(@PathVariable("id") String id) {
        return movieService.findReviewsByUserId(id);
    }

    @ResponseBody
    @RequestMapping(value = "/review/movie/{id}", method = RequestMethod.GET)
    public List<Review> findReviewByMovieId(@PathVariable("id") String id) {
        return movieService.findReviewsByMovieId(id);
    }

    @ResponseBody
    @RequestMapping(value = "/movie/{id}/word", method = RequestMethod.GET)
    public Map<String, Integer> findWordCountByMovieId(@PathVariable("id") String id) {
        return movieService.findWordCountByMovieId(id);
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public String movie() {
        return "index";
    }

}
