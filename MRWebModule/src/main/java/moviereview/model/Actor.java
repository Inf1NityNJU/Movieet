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
@Table(name = "tmdb_actor")
public class Actor implements Serializable {

    @Id
    /**
     * 人物 id
     */
    private int tmdbpeopleid;

    /**
     * 演员名字
     */
    private String name;

    /**
     * 海报后缀
     */
    private String profile;

    /**
     * 人物热度
     */
    private double popularity;

    @ManyToMany(mappedBy = "tmdb_actor")
    private List<Movie> movies = new ArrayList<>();


}
