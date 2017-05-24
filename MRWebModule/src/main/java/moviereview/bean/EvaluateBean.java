package moviereview.bean;

import javax.persistence.Id;
import java.lang.reflect.Field;
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
    private List<Integer> keywords;

    /**
     * 是否因为类型喜欢该电影
     */
    private List<Integer> genre;

    /**
     * 是否因为导演喜欢该电影
     */
    private List<Integer> director;

    /**
     * 是否因为演员喜欢该电影
     */
    private List<Integer> actor;

    public EvaluateBean() {
    }

    public EvaluateBean(int score, List<Integer> keywords, List<Integer> genre, List<Integer> director, List<Integer> actor) {
        this.score = score;
        this.keywords = keywords;
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

    public List<Integer> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Integer> keywords) {
        this.keywords = keywords;
    }

    public List<Integer> getGenre() {
        return genre;
    }

    public void setGenre(List<Integer> genre) {
        this.genre = genre;
    }

    public List<Integer> getDirector() {
        return director;
    }

    public void setDirector(List<Integer> director) {
        this.director = director;
    }

    public List<Integer> getActor() {
        return actor;
    }

    public void setActor(List<Integer> actor) {
        this.actor = actor;
    }

    private List<String> stringToList(String s) {
        String[] strings = s.split(",");
        return Arrays.asList(strings);
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator", "\n");

        StringBuilder result = new StringBuilder();
        result.append("----------")
                .append(this.getClass().getName())
                .append("----------")
                .append(lineSeparator);
        //
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                result.append(field.getName());
                if (field.get(this) == null) {
                    result.append(": null    ");
                } else {
                    result.append(": ").append(field.get(this).toString()).append("    ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.append(lineSeparator).append("--------------------").append(lineSeparator);

        return result.toString();
    }
}
