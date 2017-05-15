package moviereview.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/14.
 */

@Entity
@Table(name = "director")
public class Director implements Serializable{

    /**
     * 导演 id
     */
    @Id
    private String iddirector;

    @ManyToMany(mappedBy = "director")
    private List<Movie> movies = new ArrayList<>();

    public String getIddirector() {
        return iddirector;
    }

    public void setIddirector(String iddirector) {
        this.iddirector = iddirector;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
