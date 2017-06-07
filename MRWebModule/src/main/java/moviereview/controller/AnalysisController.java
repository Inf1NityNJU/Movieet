package moviereview.controller;

import moviereview.bean.MovieMini;
import moviereview.bean.PeopleMini;
import moviereview.model.Page;
import moviereview.service.MovieService;
import moviereview.service.PeopleService;
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

    @Autowired
    PeopleService peopleService;

    @ResponseBody
    @RequestMapping(
            value = "/rank/movie",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<MovieMini> getMovieRank(@RequestParam(value = "size") int size) {
        return movieService.getMovieRankCN(size);
    }

    @ResponseBody
    @RequestMapping(
            value = "/rank/director",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<PeopleMini> getDirectorRank(@RequestParam(value = "size") int size) {
        return peopleService.getDirectorRank(size);
    }

    @ResponseBody
    @RequestMapping(
            value = "/rank/actor",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<PeopleMini> getActorRank(@RequestParam(value = "size") int size) {
        return peopleService.getActorRank(size);
    }
}
