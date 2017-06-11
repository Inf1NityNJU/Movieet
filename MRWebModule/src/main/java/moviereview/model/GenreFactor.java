package moviereview.model;

import moviereview.util.MovieGenre;

import javax.persistence.*;
import java.lang.reflect.Field;

/**
 * Created by SilverNarcissus on 2017/5/8.
 */
@Entity
@Table(name = "user_genre_factor")
public class GenreFactor implements Comparable<GenreFactor> {
    /**
     * id
     */
    @javax.persistence.Id
    @GeneratedValue
    private int id;
    /**
     * 潜在因子
     */

    private double factor;
    /**
     * 电影类型
     */
    @Column(name = "genre")
    private int movieGenre;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GenreFactor() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public GenreFactor(double factor, int movieGenre, User user) {
        this.factor = factor;
        this.movieGenre = movieGenre;
        this.user = user;
    }

    public int getMovieGenre() {

        return movieGenre;
    }

    public void setMovieGenre(int movieGenre) {
        this.movieGenre = movieGenre;
    }

    @Override
    public int compareTo(GenreFactor o) {
        if (factor > o.factor) {
            return -1;
        } else if (factor == o.factor) {
            return 0;
        }
        return 1;
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
                //
                if (field.getName().equals("user")){
                    continue;
                }
                //
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
