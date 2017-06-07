package moviereview.controller;

import moviereview.bean.*;
import moviereview.model.Page;
import moviereview.service.AnalysisService;
import moviereview.service.MovieService;
import moviereview.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @Autowired
    AnalysisService analysisService;

    @ResponseBody
    @RequestMapping(
            value = "/rank/movieFR",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<MovieMini> getMovieRankFR(@RequestParam(value = "size") int size) {
        return movieService.getMovieRankFR(size);
    }

    @ResponseBody
    @RequestMapping(
            value = "/rank/movieCN",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<MovieMini> getMovieRankCN(@RequestParam(value = "size") int size) {
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

    @ResponseBody
    @RequestMapping(
            value = "/countryscoreinyear",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public CountryScoreInYearBean getCountryScoreInYear() {
        return analysisService.getCountryScoreInYearOfCountry(1);
    }

    @ResponseBody
    @RequestMapping(
            value = "/genrecount",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public List<GenreCountBean> genreCount() {
        return movieService.genreCount();
    }

    @ResponseBody
    @RequestMapping(
            value = "/scorepyramid",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public List<ScorePyramid> getScorePyramid() {
        return movieService.getScorePyramid();
    }
}
