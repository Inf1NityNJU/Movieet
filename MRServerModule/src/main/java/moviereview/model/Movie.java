package moviereview.model;

import moviereview.util.GsonUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.EnumSet;

/**
 * Created by Kray on 2017/3/7.
 */

//@Entity
//@Table(name = "Movie")
public class Movie {
    /**
     * 电影序列号
     */
    private String id;
    /**
     * 电影名称
     */
    private String name;
    /**
     * 电影图片URL
     */
    private String imageURL;
    /**
     * 片长(分)
     */
    private int duration;
    /**
     * 电影类型
     */
    private String genre;
    /**
     * 发布日期
     */
    private String releaseDate;
    /**
     * 电影国家
     */
    private String country;
    /**
     * 电影语言
     */
    private String language;
    /**
     * 电影剧情简介
     */
    private String plot;
    /**
     * 电影imdbId
     */
    private String imdbId;
    /**
     * 电影导演
     */
    private String director;
    /**
     * 电影创作者
     */
    private String writers;
    /**
     * 主要演员
     */
    private String actors;

    private String jsonString;

    public Movie() {

    }

    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Movie(String id, String imdbJsonString, MovieJson movieJson) {
        this.id = id;
        this.name = movieJson.getTitle();
        this.genre = movieJson.getGenre();
        this.jsonString = imdbJsonString;
        this.imageURL = movieJson.getPoster();
        //TODO
        this.duration = Integer.parseInt(movieJson.getRuntime().substring(0, movieJson.getRuntime().length() - 4));
        this.releaseDate = movieJson.getReleased();
        this.country = movieJson.getCountry();
        this.language = movieJson.getLanguage();
        this.plot = movieJson.getPlot();
        this.imdbId = movieJson.getImdbID();
        this.director = movieJson.getDirector();
        this.writers = movieJson.getWriter();
        this.actors = movieJson.getActors();
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "#" + name + "#" + genre + "#" + jsonString;
    }
}
