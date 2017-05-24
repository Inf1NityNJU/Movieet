package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/14.
 */
@Entity
@Table(name = "tmdb_actor")
public class Actor implements Serializable {

    @Id
    /**
     * 人物 id
     */
    private int tmdbpeopleid;

    /**
     * 演员名字
     */
    private String name;

    /**
     * 海报后缀
     */
    private String profile;

    public Actor() {
    }

    /**
     * 人物热度
     */

    private double popularity;

    @ManyToMany(mappedBy = "tmdb_actor")
    private List<Movie> movies = new ArrayList<>();


    public int getTmdbpeopleid() {
        return tmdbpeopleid;
    }

    public void setTmdbpeopleid(int tmdbpeopleid) {
        this.tmdbpeopleid = tmdbpeopleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
