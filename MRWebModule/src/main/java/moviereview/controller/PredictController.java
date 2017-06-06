package moviereview.controller;

import moviereview.bean.EstimateResultBean;
import moviereview.bean.PredictBean;
import moviereview.bean.PredictResultBean;
import moviereview.service.PredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kray on 2017/6/2.
 */

@Controller
@RequestMapping("/api/movie")
public class PredictController {

    @Autowired
    private PredictService predictService;

    @ResponseBody
    @RequestMapping(
            value = "/predict",
            params = {"genre", "director", "actor", "keyword"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public PredictResultBean predictMovieWeka(@RequestParam(value = "genre") String genres,
                                              @RequestParam(value = "director") String directors,
                                              @RequestParam(value = "actor") String actors,
                                              @RequestParam(value = "keyword") String keywords) {
        List<Integer> intGenres = new ArrayList<>();
        for (String g : genres.split(",")) {
            intGenres.add(Integer.parseInt(g));
        }
        List<Integer> intDirectors = new ArrayList<>();
        for (String d : directors.split(",")) {
            intDirectors.add(Integer.parseInt(d));
        }
        List<Integer> intActors = new ArrayList<>();
        for (String a : actors.split(",")) {
            intActors.add(Integer.parseInt(a));
        }
        PredictBean predictBean = new PredictBean(intGenres, intDirectors, intActors);
        return predictService.wekaPredict(predictBean);
    }

    @ResponseBody
    @RequestMapping(
            value = "/estimate",
            params = {"genre", "director", "actor", "keyword"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public EstimateResultBean estimateMovieInterval(@RequestParam(value = "genre") String genres,
                                                    @RequestParam(value = "director") String directors,
                                                    @RequestParam(value = "actor") String actors,
                                                    @RequestParam(value = "keyword") String keywords) {
        List<Integer> intGenres = new ArrayList<>();
        for (String g : genres.split(",")) {
            intGenres.add(Integer.parseInt(g));
        }
        List<Integer> intDirectors = new ArrayList<>();
        for (String d : directors.split(",")) {
            intDirectors.add(Integer.parseInt(d));
        }
        List<Integer> intActors = new ArrayList<>();
        for (String a : actors.split(",")) {
            intActors.add(Integer.parseInt(a));
        }
        PredictBean predictBean = new PredictBean(intGenres, intDirectors, intActors);
        return predictService.intervalEstimation(predictBean);
    }
}
