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
@Table(name = "genre")
public class Genre implements Serializable {

    @Id
    private String idgenre;

    @ManyToMany(mappedBy = "genre")
    private List<Movie> movies = new ArrayList<>();

    public String getIdgenre() {
        return idgenre;
    }

    public void setIdgenre(String idgenre) {
        this.idgenre = idgenre;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
