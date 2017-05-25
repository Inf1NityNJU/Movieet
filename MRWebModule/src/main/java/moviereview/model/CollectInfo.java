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
public class CollectInfo {
    @Id
    private int collectId;

    private int userId;

    private int movieId;

    private String time;

    public CollectInfo() {
    }

    public CollectInfo(int userId, int movieId, String time) {
        this.userId = userId;
        this.movieId = movieId;
        this.time = time;
    }

    public CollectInfo(int collectId, int userId, int movieId, String time) {
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
}
