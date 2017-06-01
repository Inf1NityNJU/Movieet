package moviereview.model;

import moviereview.util.Language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

@Entity
@Table(name = "tmdb_movie")
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
    private Date release_date;

    /**
     * 放映时间
     */
    private Integer runtime;

    /**
     * 豆瓣评分分布
     */
    private String douban_distribution;

    /**
     * 豆瓣评分
     */
    private Double douban_score;

    /**
     * 豆瓣评价数量
     */
    private Integer douban_count;

    /**
     * 豆瓣id
     */
    private Integer doubanid;

    /**
     * 豆瓣标题
     */
    private String doubantitle;

    /**
     * imdb评分分布
     */
    private String imdb_distribution;

    /**
     * imdb评分
     */
    private Double imdb_score;

    /**
     * imdb评分人数
     */
    private Integer imdb_count;

    /**
     * 中文剧情简介
     */
    private String plot_cn;

    private Integer budget;

    private Integer revenue;

    private String background_poster;

    public String getDoubantitle() {
        if (doubantitle == null) {
            return "";
        }
        return doubantitle;
    }

    public void setDoubantitle(String doubantitle) {
        this.doubantitle = doubantitle;
    }

    public String getPlot_cn() {
        if (plot_cn == null) {
            return "";
        }
        return plot_cn;
    }

    public void setPlot_cn(String plot_cn) {
        this.plot_cn = plot_cn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTmdbtitle() {
        if (tmdbtitle == null) {
            return "";
        }
        return tmdbtitle;
    }

    public void setTmdbtitle(String tmdbtitle) {
        this.tmdbtitle = tmdbtitle;
    }

    public String getTmdb_original_title() {
        if (tmdb_original_title == null) {
            return "";
        }
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
        Language l = Language.getLanguageFromString(language);
        return l.getFullName(l);
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPoster() {
        if (poster == null) {
            return "";
        }
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        if (plot == null) {
            return "";
        }
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }


    public String getDouban_distribution() {
        return douban_distribution;
    }

    public void setDouban_distribution(String douban_distribution) {
        this.douban_distribution = douban_distribution;
    }

    public Integer getDoubanid() {
        return doubanid;
    }

    public void setDoubanid(Integer doubanid) {
        this.doubanid = doubanid;
    }

    public String getImdb_distribution() {
        return imdb_distribution;
    }

    public void setImdb_distribution(String imdb_distribution) {
        this.imdb_distribution = imdb_distribution;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Double getDouban_score() {
        if (douban_score == null) {
            return 0.0;
        }
        return douban_score;
    }

    public void setDouban_score(Double douban_score) {
        this.douban_score = douban_score;
    }

    public Double getImdb_score() {
        if (imdb_score == null) {
            return 0.0;
        }
        return imdb_score;
    }

    public void setImdb_score(Double imdb_score) {
        this.imdb_score = imdb_score;
    }

    public Integer getImdb_count() {
        if (imdb_count == null) {
            return 0;
        }
        return imdb_count;
    }

    public void setImdb_count(Integer imdb_count) {
        this.imdb_count = imdb_count;
    }

    public Integer getDouban_count() {
        return douban_count;
    }

    public void setDouban_count(Integer douban_count) {
        this.douban_count = douban_count;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public String getBackground_poster() {
        return background_poster;
    }

    public void setBackground_poster(String background_poster) {
        this.background_poster = background_poster;
    }

    public Movie() {

    }

    public Movie(int id, String tmdbtitle, String tmdb_original_title, double popularity, String imdbid, String language, String poster, String plot, Date release_date, int runtime, String douban_distribution, double douban_score, int doubanid, String doubantitle, String imdb_distribution, double imdb_score, int imdb_count) {
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
        this.doubantitle = doubantitle;
        this.imdb_distribution = imdb_distribution;
        this.imdb_score = imdb_score;
        this.imdb_count = imdb_count;
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
