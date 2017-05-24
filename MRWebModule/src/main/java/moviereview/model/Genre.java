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
@Table(name = "tmdb_genre")
public class Genre implements Serializable {

    @Id
    /**
     * 种类 id
     */
    private int idgenre;

    /**
     * 英文名字
     */
    private String tmdbgenre_en;

    public int getIdgenre() {
        return idgenre;
    }

    public void setIdgenre(int idgenre) {
        this.idgenre = idgenre;
    }

    public String getTmdbgenre_en() {
        return tmdbgenre_en;
    }

    public void setTmdbgenre_en(String tmdbgenre_en) {
        this.tmdbgenre_en = tmdbgenre_en;
    }

    public String getTmdbgenre_cn() {
        return tmdbgenre_cn;
    }

    public void setTmdbgenre_cn(String tmdbgenre_cn) {
        this.tmdbgenre_cn = tmdbgenre_cn;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Genre() {
    }

    /**

     * 中文名字
     */
    private String tmdbgenre_cn;

    @ManyToMany(mappedBy = "tmdb_genre")
    private List<Movie> movies = new ArrayList<>();

}
