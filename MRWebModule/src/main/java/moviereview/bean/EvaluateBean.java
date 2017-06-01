package moviereview.bean;

import moviereview.model.EvaluateInfo;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
    private List<Integer> keyword;

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

    public EvaluateBean(EvaluateInfo evaluateInfo) {
        this.score = (int)evaluateInfo.getScore();
        this.keyword = integerStringToList(evaluateInfo.getKeyword());
        this.genre = integerStringToList(evaluateInfo.getGenre());
        this.director = integerStringToList(evaluateInfo.getDirector());
        this.actor = integerStringToList(evaluateInfo.getActor());
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Integer> getKeywords() {
        return keyword;
    }

    public void setKeyword(List<Integer> keyword) {
        this.keyword = keyword;
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

    private List<Integer> integerStringToList(String s) {
        List<Integer> integers = new ArrayList<>();
        if (s==null || s.equals("")) {
            return integers;
        }
        String[] string = s.split(",");
        for (String str : string) {
            integers.add(Integer.parseInt(str));
        }
        return integers;
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
