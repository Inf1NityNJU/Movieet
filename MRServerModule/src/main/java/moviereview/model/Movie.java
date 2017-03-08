package moviereview.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Kray on 2017/3/7.
 */


//@Entity
//@Table(name = "Movie")
public class Movie {


    /**
     * 电影序列号
     */

//    @Id
//    @GeneratedValue
//    @Column(name = "id", unique = true, nullable = false)
    private String id;

    /**
     * 电影名称
     */
    private String name;


    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
