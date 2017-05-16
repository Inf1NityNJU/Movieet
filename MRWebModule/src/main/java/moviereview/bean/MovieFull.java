package moviereview.bean;

import moviereview.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/15.
 */
public class MovieFull {

    /**
     * 海报
     */
    private String poster;

    /**
     * 情节
     */
    private String plot;

    /**
     * 数据库里 id
     */
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
    private List<String> directors = new ArrayList<>();

    /**
     * 类型
     */
    private List<String> genres = new ArrayList<>();

    /**
     * 关键字
     */
    private List<String> keywords = new ArrayList<>();

    /**
     * 上映日期
     */
    private List<String> releaseDates = new ArrayList<>();

    /**
     * 演员
     */
    private List<String> actors = new ArrayList<>();

    /**
     * 投票数
     */
    private int votes;

    /**
     * 评分
     */
    private double rank;

    public MovieFull(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.kind = movie.getKind();
        this.poster = "";
        this.plot = "";

        this.rank = movie.getRank();
        this.votes = movie.getVotes();

        this.actors = new ArrayList<>();
        for (Actor actor : movie.getActor()) {
            this.actors.add(actor.getIdactor());
        }
        this.directors = new ArrayList<>();
        for (Director director : movie.getDirector()) {
            this.directors.add(director.getIddirector());
        }
        this.genres = new ArrayList<>();
        for (Genre genre : movie.getGenre()) {
            this.genres.add(genre.getIdgenre());
        }
        this.releaseDates = new ArrayList<>();
        for (ReleaseDate releaseDate : movie.getReleaseDate()) {
            this.releaseDates.add(releaseDate.getIddate());
        }
        this.keywords = new ArrayList<>();
        for (Keyword keyword : movie.getKeyword()) {
            this.keywords.add(keyword.getIdkeyword());
        }

    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

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

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(List<String> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
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

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
