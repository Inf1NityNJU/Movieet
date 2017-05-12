package moviereview.model;


import java.lang.reflect.Field;
import java.util.EnumSet;

/**
 * Created by SilverNarcissus on 2017/3/3.
 * id name imageURL genre(标签/类型) duration releaseDate country language plot(简介/故事结构)
 * imdbId (director, writers, actors)
 */
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
     * 电影评论数量
     */
    private int reviewCount;
    /**
     * 电影评分
     */
    private double score;
    /**
     * 电影评分
     */
    private double rating;
    /**
     * 电影创作者
     */
    private String writers;
    /**
     * 主要演员
     */
    private String actors;

    public Movie() {

    }

    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
