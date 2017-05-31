package moviereview.bean;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/5/31.
 */
public class PredictBean {
    /**
     * 演员id
     */
    private List<Integer> actors;
    /**
     * 导演id
     */
    private List<Integer> directors;
    /**
     * 类型id
     */
    private List<Integer> genres;

    public PredictBean(List<Integer> actors, List<Integer> directors, List<Integer> genres) {
        this.actors = actors;
        this.directors = directors;
        this.genres = genres;
    }

    public List<Integer> getActors() {
        return actors;
    }

    public void setActors(List<Integer> actors) {
        this.actors = actors;
    }

    public List<Integer> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Integer> directors) {
        this.directors = directors;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
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
