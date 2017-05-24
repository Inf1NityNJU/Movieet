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

    /**
     * 中文名字
     */
    private String tmdbgenre_cn;

    @ManyToMany(mappedBy = "tmdb_genre")
    private List<Movie> movies = new ArrayList<>();

}
