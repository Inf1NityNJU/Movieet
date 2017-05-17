package moviereview.service.impl;

import moviereview.util.DataConst;
import moviereview.model.Movie;
import moviereview.model.Page;
import moviereview.model.ReviewIMDB;
import moviereview.repository.MovieRepository;
import moviereview.service.ReviewService;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
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
        Movie movie = movieRepository.findMovieByID(movieid);

        StringBuilder sb = new StringBuilder();
        for (String s : movie.getTitle().split(" ")) {
            sb.append(s);
            sb.append("+");
        }
        String movieStr = sb.toString().substring(0, sb.toString().length() - 1);
        String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.FilePath +
                "MovieIMDBReviewGetter.py " + movieStr + " " + movie.getYear() + " " + page);

        JSONObject jsonObject = new JSONObject(jsonString);
        Map<String, Object> m = jsonObject.toMap();
        JSONArray jsonArray = new JSONArray(m.get("list"));
        List<ReviewIMDB> imdbList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            imdbList.add(GsonUtil.parseJson(jsonArray.get(i).toString(), ReviewIMDB.class));
        }

        return new Page<ReviewIMDB>(
                page,
                imdbList.size(),
                sortType,
                asc,
                Integer.parseInt(m.get("size").toString()),
                imdbList);
    }
}
