package moviereview.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/14.
 */

@Entity
@Table(name = "director")
public class Director {

    /**
     * 导演 id
     */
    @Id
    private String iddirector;

    private ArrayList<Movie> movies = new ArrayList<>();

    public String getIddirector() {
        return iddirector;
    }

    public void setIddirector(String iddirector) {
        this.iddirector = iddirector;
    }

    /**
     * 电影
     */
    @ManyToMany(mappedBy = "director")
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
