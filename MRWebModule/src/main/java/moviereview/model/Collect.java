package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by vivian on 2017/5/15.
 * 用户收藏的电影
 */
@Entity
@Table(name = "collect")
public class Collect {
    @Id
    private int collectId;

    private int userId;

    private String movieId;

    private String time;

    public Collect() {
    }

    public Collect(int userId, String movieId, String time) {
        this.userId = userId;
        this.movieId = movieId;
        this.time = time;
    }

    public Collect(int collectId, int userId, String movieId, String time) {
        this.collectId = collectId;
        this.userId = userId;
        this.movieId = movieId;
        this.time = time;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
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
}
