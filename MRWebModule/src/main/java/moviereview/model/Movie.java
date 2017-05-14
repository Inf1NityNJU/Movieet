package moviereview.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie implements Serializable{

    /**
     * 数据库里 id
     */
    @Id
    private String idmovie;

    /**
     * 电影标题
     */
    private String title;

    /**
     * 年份
     */
    private String year;

    /**
     * 种类
     */
    private String kind;

    /**
     * 导演
     */
    private ArrayList<Director> director = new ArrayList<>();

//    /**
//     * 电影海报
//     */
//    private String poster;
//
//    /**
//     * 电影分类
//     */
//    private String[] genres;
//
//    /**
//     * 电影上映日期
//     */
//    private String releaseDate;
//
//    /**
//     * 电影评分
//     */
//    private Double score;
//
//    /**
//     * 电影投票数量
//     */
//    private Integer scoreCount;


    public String getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(String idmovie) {
        this.idmovie = idmovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "is_director",
            joinColumns = {@JoinColumn(name = "idmovie", referencedColumnName = "idmovie")},
            inverseJoinColumns = {@JoinColumn(name = "iddirector", referencedColumnName = "iddirector")})
    public List<Director> getDirector() {
        return director;
    }

    public void setDirector(ArrayList<Director> director) {
        this.director = director;
    }
}
