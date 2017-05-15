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
@Table(name = "date")
public class ReleaseDate implements Serializable {

    @Id
    private String iddate;

    @ManyToMany(mappedBy = "releaseDate")
    private List<Movie> movies = new ArrayList<>();

    public String getIddate() {
        return iddate;
    }

    public void setIddate(String iddate) {
        this.iddate = iddate;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
