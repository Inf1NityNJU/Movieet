package moviereview.model;

import javax.persistence.*;
import java.io.Serializable;
import java.rmi.activation.ActivationGroup;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    /**
     * 数据库里 id
     */
    @Id
    @Column(name = "idmovie")
    private String id;

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
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "is_director",
            joinColumns = {@JoinColumn(name = "idmovie", referencedColumnName = "idmovie")},
            inverseJoinColumns = {@JoinColumn(name = "iddirector", referencedColumnName = "iddirector")})
    private List<Director> director = new ArrayList<>();

    /**
     * 类型
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "is_genre",
            joinColumns = {@JoinColumn(name = "idmovie", referencedColumnName = "idmovie")},
            inverseJoinColumns = {@JoinColumn(name = "idgenre", referencedColumnName = "idgenre")})
    private List<Genre> genre = new ArrayList<>();

    /**
     * 关键字
     */
//    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "is_keyword",
//            joinColumns = {@JoinColumn(name = "idmovie", referencedColumnName = "idmovie")},
//            inverseJoinColumns = {@JoinColumn(name = "idkeyword", referencedColumnName = "idkeyword")})
//    private List<Keyword> keyword = new ArrayList<>();

    /**
     * 上映日期
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "is_release_date",
            joinColumns = {@JoinColumn(name = "idmovie", referencedColumnName = "idmovie")},
            inverseJoinColumns = {@JoinColumn(name = "iddate", referencedColumnName = "iddate")})
    private List<ReleaseDate> releaseDate = new ArrayList<>();

    /**
     * 演员
     */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "is_actor",
            joinColumns = {@JoinColumn(name = "idmovie", referencedColumnName = "idmovie")},
            inverseJoinColumns = {@JoinColumn(name = "idactor", referencedColumnName = "idactor")})
    private List<Actor> actor = new ArrayList<>();
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Director> getDirector() {
        return director;
    }

    public void setDirector(ArrayList<Director> director) {
        this.director = director;
    }

    public void setDirector(List<Director> director) {
        this.director = director;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

//    public List<Keyword> getKeyword() {
//        return keyword;
//    }
//
//    public void setKeyword(List<Keyword> keyword) {
//        this.keyword = keyword;
//    }

    public List<ReleaseDate> getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(List<ReleaseDate> releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Actor> getActor() {
        return actor;
    }

    public void setActor(List<Actor> actor) {
        this.actor = actor;
    }
}
