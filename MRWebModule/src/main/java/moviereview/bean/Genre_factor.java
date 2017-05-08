package moviereview.bean;

import moviereview.util.MovieGenre;

/**
 * Created by SilverNarcissus on 2017/5/8.
 *
 */
public class Genre_factor {
    /**
     * 潜在因子
     */
    private double factor;

    /**
     * 电影类型
     */
    private MovieGenre movieGenre;

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

    public Genre_factor(double factor, MovieGenre movieGenre) {
        this.factor = factor;
        this.movieGenre = movieGenre;
    }
}
