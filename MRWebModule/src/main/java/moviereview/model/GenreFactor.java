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
     * Id
     */
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    /**
     * 潜在因子
     */

    private double factor;
    /**
     * 电影类型
     */
    @Column(name = "genre")
    private MovieGenre movieGenre;

    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public GenreFactor() {

    }

    public GenreFactor(double factor, MovieGenre movieGenre) {
        this.factor = factor;
        this.movieGenre = movieGenre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
