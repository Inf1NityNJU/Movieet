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
    @Column(name = "tmdbid")
    private int id;

    /**
     * 电影标题
     */
    private String tmdbtitle;

    /**
     * 电影原生标题
     */
    private String tmdb_original_title;

    /**
     * 搜索热度
     */
    private double popularity;

    /**
     * imdb——id
     */
    private String imdbid;

    /**
     * 电影语言
     */
    private String language;

    /**
     * 海报
     */
    private String poster;

    /**
     * 剧情
     */
    private String plot;

    /**
     * 上映时间
     */
    private String release_date;

    /**
     * 放映时间
     */
    private int runtime;

    /**
     * 豆瓣评分分布
     */
    private String douban_distribution;

    /**
     * 豆瓣评分
     */
    private double douban_score;

    /**
     * 豆瓣id
     */
    private int doubanid;

    /**
     * 豆瓣标题
     */
    private String doubantitile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTmdbtitle() {
        return tmdbtitle;
    }

    public void setTmdbtitle(String tmdbtitle) {
        this.tmdbtitle = tmdbtitle;
    }

    public String getTmdb_original_title() {
        return tmdb_original_title;
    }

    public void setTmdb_original_title(String tmdb_original_title) {
        this.tmdb_original_title = tmdb_original_title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getDouban_distribution() {
        return douban_distribution;
    }

    public void setDouban_distribution(String douban_distribution) {
        this.douban_distribution = douban_distribution;
    }

    public double getDouban_score() {
        return douban_score;
    }

    public void setDouban_score(double douban_score) {
        this.douban_score = douban_score;
    }

    public int getDoubanid() {
        return doubanid;
    }

    public void setDoubanid(int doubanid) {
        this.doubanid = doubanid;
    }

    public String getDoubantitile() {
        return doubantitile;
    }

    public void setDoubantitile(String doubantitile) {
        this.doubantitile = doubantitile;
    }

    public Movie() {

    }

    public Movie(int id, String tmdbtitle, String tmdb_original_title, double popularity, String imdbid, String language, String poster, String plot, String release_date, int runtime, String douban_distribution, double douban_score, int doubanid, String doubantitile) {

        this.id = id;
        this.tmdbtitle = tmdbtitle;
        this.tmdb_original_title = tmdb_original_title;
        this.popularity = popularity;
        this.imdbid = imdbid;
        this.language = language;
        this.poster = poster;
        this.plot = plot;
        this.release_date = release_date;
        this.runtime = runtime;
        this.douban_distribution = douban_distribution;
        this.douban_score = douban_score;
        this.doubanid = doubanid;
        this.doubantitile = doubantitile;
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
