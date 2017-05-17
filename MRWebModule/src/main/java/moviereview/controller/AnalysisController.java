package moviereview.controller;

import moviereview.bean.GenreInfo;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by SilverNarcissus on 2017/5/17.
 */
@Controller
@RequestMapping("/api/analysis")
public class AnalysisController {
    @Autowired
    MovieService movieService;

    @ResponseBody
    @RequestMapping(
            value = "/genrecount",
            params = {"genre"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public GenreInfo findGenreInfo(@RequestParam(value = "genre") String genre) {
        return movieService.findGenreInfo(genre);
    }

}
