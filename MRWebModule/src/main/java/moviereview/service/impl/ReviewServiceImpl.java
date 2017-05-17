package moviereview.service.impl;

import moviereview.util.DataConst;
import moviereview.model.Movie;
import moviereview.model.Page;
import moviereview.model.ReviewIMDB;
import moviereview.repository.MovieRepository;
import moviereview.service.ReviewService;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
import moviereview.util.URLStringConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/5/16.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * 通过电影 ID 寻找该电影的分页 IMDB 评论
     *
     * @param movieid 电影 ID
     * @return 评论 list
     */
    public Page<ReviewIMDB> findIMDBReviewByMovieId(String movieid, int page, String sortType, String asc) {
        Movie movie = movieRepository.findMovieById(movieid);

        String movieStr = URLStringConverter.convertToURLString(movie.getTitle());

        String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.FilePath +
                "MovieIMDBReviewGetter.py " + movieStr + " " + movie.getYear() + " " + page);

        JSONArray jsonArray = new JSONArray(jsonString);
        List<ReviewIMDB> imdbList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            imdbList.add(GsonUtil.parseJson(jsonArray.get(i).toString(), ReviewIMDB.class));
        }

        //评论数
        String number = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.FilePath +
                "MovieIMDBReviewCountGetter.py " + movieStr + " " + movie.getYear()).trim();

        return new Page<ReviewIMDB>(
                page,
                imdbList.size(),
                sortType,
                asc,
                Integer.parseInt(number),
                imdbList);
    }
}
