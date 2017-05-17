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

    /**
     * 分析类型信息
     *
     * @param genre 需要分析的类型
     * @param startYear 起始分析年份
     * @return 分析结果
     */
    @ResponseBody
    @RequestMapping(
            value = "/genrecount",
            params = {"genre", "start"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public GenreInfo findGenreInfo(@RequestParam(value = "genre") String genre, @RequestParam(value = "start") int startYear) {
        return movieService.findGenreInfo(genre, startYear);
    }

}
