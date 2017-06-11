package moviereview.bean;

import moviereview.model.Director;

/**
 * Created by vivian on 2017/5/26.
 */
public class PeopleMini {
    private int id;

    private String name;

    private String photo;

    private double popularity;

    public PeopleMini() {
    }

    public PeopleMini(int id, String name, double popularity, String photo) {
        this.id = id;
        this.name = name;
        this.popularity = popularity;
        if (photo!=null) {
            this.photo = "https://image.tmdb.org/t/p/w500" + photo;
        }else {
            this.photo = null;
        }
    }

    public PeopleMini(Director director) {
        this.id = director.getTmdbpeopleid();
        this.name = director.getName();
        this.popularity = director.getPopularity();
        if (director.getProfile()!=null) {
            this.photo = "https://image.tmdb.org/t/p/w500" + director.getProfile();
        }else {
            this.photo = null;
        }
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}
