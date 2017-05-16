package moviereview.model;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
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
     * 评分数量
     */
    private int votes;

    /**
     * 评分
     */
    private double rank;

    /**
     * 评分分布
     */
    private String distribution;
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
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "is_keyword",
            joinColumns = {@JoinColumn(name = "idmovie", referencedColumnName = "idmovie")},
            inverseJoinColumns = {@JoinColumn(name = "idkeyword", referencedColumnName = "idkeyword")})
    private List<Keyword> keyword = new ArrayList<>();

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

    public List<Keyword> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<Keyword> keyword) {
        this.keyword = keyword;
    }

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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator", "\n");

        StringBuilder result = new StringBuilder();
        result.append("----------")
                .append(this.getClass().getName())
                .append("----------")
                .append(lineSeparator);
        //
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                result.append(field.getName());
                if (field.get(this) == null) {
                    result.append(": null    ");
                } else {
                    result.append(": ").append(field.get(this).toString()).append("    ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.append(lineSeparator).append("--------------------").append(lineSeparator);

        return result.toString();
    }
}
