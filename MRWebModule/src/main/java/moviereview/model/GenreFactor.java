package moviereview.model;

import moviereview.util.MovieGenre;

import javax.persistence.*;

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
    private int id;
    /**
     * 潜在因子
     */

    private double factor;
    /**
     * 电影类型
     */
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private MovieGenre movieGenre;

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

    public GenreFactor(double factor, MovieGenre movieGenre) {
        this.factor = factor;
        this.movieGenre = movieGenre;
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

    public MovieGenre getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(MovieGenre movieGenre) {
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
}
