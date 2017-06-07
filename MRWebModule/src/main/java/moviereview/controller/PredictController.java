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
            params = {"genre", "director", "actor"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public PredictResultBean predictMovieWeka(@RequestParam(value = "genre") String genres,
                                              @RequestParam(value = "director") String directors,
                                              @RequestParam(value = "actor") String actors) {
        PredictResultBean predictResultBean = predictService.wekaPredict(constructPredictBean(genres, directors, actors));
        predictResultBean.setDescriptionEN(predictService.getPredictDescription(predictResultBean));
        return predictResultBean;
    }

    @ResponseBody
    @RequestMapping(
            value = "/estimate",
            params = {"genre", "director", "actor"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public EstimateResultBean estimateMovieInterval(@RequestParam(value = "genre") String genres,
                                                    @RequestParam(value = "director") String directors,
                                                    @RequestParam(value = "actor") String actors) {
        EstimateResultBean estimateResultBean = predictService.intervalEstimation(constructPredictBean(genres, directors, actors));
        estimateResultBean.setDescriptionEN(predictService.getEstimateDescription(estimateResultBean));
        return estimateResultBean;
    }

    private PredictBean constructPredictBean(String genres, String directors, String actors) {
        List<Integer> intGenres = new ArrayList<>();
        if (genres.contains(",")) {
            for (String g : genres.split(",")) {
                intGenres.add(Integer.parseInt(g));
            }
        } else {
            intGenres.add(Integer.parseInt(genres));
        }
        List<Integer> intDirectors = new ArrayList<>();
        if (directors.contains(",")) {
            for (String d : directors.split(",")) {
                intDirectors.add(Integer.parseInt(d));
            }
        } else {
            intDirectors.add(Integer.parseInt(directors));
        }
        List<Integer> intActors = new ArrayList<>();
        if (actors.contains(",")) {
            for (String a : actors.split(",")) {
                intActors.add(Integer.parseInt(a));
            }
        } else {
            intActors.add(Integer.parseInt(actors));
        }
        return new PredictBean(intActors, intDirectors, intGenres);
    }
}
