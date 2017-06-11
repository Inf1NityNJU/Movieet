package moviereview.bean;

import moviereview.model.Actor;
import moviereview.model.Director;

import java.util.List;

/**
 * Created by vivian on 2017/5/28.
 */
public class PeopleFull {
    private int id;

    private String name;

    /**
     * director or actor
     */
    private String type;

    private String photo;

    private double popularity;

    private List<MovieMini> movies;

    public PeopleFull() {
    }

    public PeopleFull(Director director, List<MovieMini> movies) {
        this.id = director.getTmdbpeopleid();
        this.name = director.getName();
        this.type = "director";
        if(director.getProfile()!=null) {
            this.photo = "https://image.tmdb.org/t/p/w500" + director.getProfile();
        }else {
            this.photo = null;
        }
        this.popularity = director.getPopularity();
        this.movies = movies;
    }

    public PeopleFull(Actor actor, List<MovieMini> movies) {
        this.id = actor.getTmdbpeopleid();
        this.name = actor.getName();
        this.type = "director";
        if(actor.getProfile()!=null) {
            this.photo = "https://image.tmdb.org/t/p/w500" + actor.getProfile();
        }else {
            this.photo = null;
        }
        this.popularity = actor.getPopularity();
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public List<MovieMini> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieMini> movies) {
        this.movies = movies;
    }
}
