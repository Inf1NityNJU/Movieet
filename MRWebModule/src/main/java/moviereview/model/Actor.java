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
@Table(name = "actor")
public class Actor implements Serializable {

    @Id
    private String idactor;

    @ManyToMany(mappedBy = "actor")
    private List<Movie> movies = new ArrayList<>();

    public String getIdactor() {
        return idactor;
    }

    public void setIdactor(String idactor) {
        this.idactor = idactor;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
