package moviereview.model;

import moviereview.bean.EvaluateBean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by vivian on 2017/5/16.
 * 用户看过的电影
 */

@Entity
@Table(name = "evaluate")
public class EvaluateInfo {
    @Id
    private int evaluateId;

    private int userId;

    private int movieId;

    private String time;

    private double score;

    private String keyword;

    private String genre;

    private String director;

    private String actor;

    public EvaluateInfo() {
    }

    public EvaluateInfo(int userId, int movieId, EvaluateBean evaluateBean) {
        this.userId = userId;
        this.movieId = movieId;
        this.time = LocalDateTime.now().withNano(0).toString();
        this.score = evaluateBean.getScore();
        this.keyword = intListToString(evaluateBean.getKeywords());
        this.genre = intListToString(evaluateBean.getGenre());
        this.director = intListToString(evaluateBean.getDirector());
        this.actor = intListToString(evaluateBean.getActor());
    }

    public int getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    private String listToStirng(List<String> list) {
        String result = "";
        if (list == null || list.size() == 0) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            result = result + list.get(i) + ",";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private String intListToString(List<Integer> integers) {
        String result = "";
        if (integers != null) {
            for (Integer i : integers) {
                result = result + i + ",";
            }
            return result.substring(0, result.length() - 1);
        }
        return result;
    }
}
