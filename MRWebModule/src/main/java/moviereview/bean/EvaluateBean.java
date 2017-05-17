package moviereview.bean;

import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vivian on 2017/5/16.
 */
public class EvaluateBean {
    /**
     * 给电影的评分
     */
    @Id
    private int score;

    /**
     * 给电影的标签
     */
    private List<String> tags;

    /**
     * 是否因为类型喜欢该电影
     */
    private boolean genre;

    /**
     * 是否因为导演喜欢该电影
     */
    private boolean director;

    /**
     * 是否因为演员喜欢该电影
     */
    private boolean actor;

    public EvaluateBean(int score, List<String> tags, boolean genre, boolean director, boolean actor) {
        this.score = score;
        this.tags = tags;
        this.genre = genre;
        this.director = director;
        this.actor = actor;
    }

    public EvaluateBean(int score, String tags, boolean genre, boolean director, boolean actor) {
        this.score = score;
        this.tags = stringToList(tags);
        this.genre = genre;
        this.director = director;
        this.actor = actor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isGenre() {
        return genre;
    }

    public void setGenre(boolean genre) {
        this.genre = genre;
    }

    public boolean isDirector() {
        return director;
    }

    public void setDirector(boolean director) {
        this.director = director;
    }

    public boolean isActor() {
        return actor;
    }

    public void setActor(boolean actor) {
        this.actor = actor;
    }

    private List<String> stringToList(String s) {
        String[] strings = s.split(",");
        return Arrays.asList(strings);
    }
}
