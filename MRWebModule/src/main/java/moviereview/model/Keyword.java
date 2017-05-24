package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Kray on 2017/5/14.
 */
@Entity
@Table(name = "keyword")
public class Keyword implements Serializable {

    @Id
    private String idkeyword;

//    @ManyToMany(mappedBy = "keyword")
//    private List<Movie> movies = new ArrayList<>();

    public String getIdkeyword() {
        return idkeyword;
    }

    public void setIdkeyword(String idkeyword) {
        this.idkeyword = idkeyword;
    }

//    public List<Movie> getMovies() {
//        return movies;
//    }

//    public void setMovies(List<Movie> movies) {
//        this.movies = movies;
//    }

    public Keyword() {
    }
}
