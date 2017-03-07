package moviereview.controller;

import moviereview.model.Movie;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Movie movieInfo(@PathVariable("id") String id) {
        return movieService.getMovieByID(id);
    }

}
