package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
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

    private String movieId;

    private String time;

    private double score;

    private String genre;

    private String director;

    private String actor;

    public EvaluateInfo() {
    }

    public EvaluateInfo(int userId, String movieId, String time, double score, String genre, String director, String actor) {
        this.userId = userId;
        this.movieId = movieId;
        this.time = time;
        this.score = score;
        this.genre = genre;
        this.director = director;
        this.actor = actor;
    }

    public EvaluateInfo(int userId, String movieId, String time, double score, List<Genre> genres, List<Director> directors, List<Actor> actors) {
        this.userId = userId;
        this.movieId = movieId;
        this.time = time;
        this.score = score;
        List<String> result = new ArrayList<>();
        for (Genre genre : genres) {
            result.add(genre.getIdgenre());
        }
        this.genre = listToStirng(result);

        result.clear();
        for (Director director : directors) {
            result.add(director.getIddirector());
        }
        this.director = listToStirng(result);
        result.clear();

        for (Actor actor : actors) {
            result.add(actor.getIdactor());
        }
        this.actor = listToStirng(result);

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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
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
        for (int i = 0; i < list.size() - 1; i++) {
            result = result + list.get(i) + ",";
        }
        result = result + list.get(list.size());
        return result;
    }
}
