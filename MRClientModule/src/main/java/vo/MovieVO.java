package vo;


import javafx.scene.image.Image;
import po.MoviePO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/3.
 */
public class MovieVO {
    /**
     * id
     */
    public String id;

    /**
     * 电影名称
     */
    public String name;

    /**
     * 片长(分)
     */
    public int duration;
    /**
     * 电影类型
     */
    public List<String> genre;

    /**
     * 发行日期
     */
    public String releaseDate;

    /**
     * 电影海报
     */
    public Image poster;

    /**
     * 电影国家
     */
    public String country;
    /**
     * 电影语言
     */
    public String language;
    /**
     * 电影剧情简介
     */
    public String plot;
    /**
     * 电影导演
     */
    public List<String> director;
    /**
     * 电影创作者
     */
    public List<String> writers;
    /**
     * 主要演员
     */
    public List<String> actors;
    /**
     * 电影评分
     */
    public double rating;
    /**
     * 电影评分
     */
    public double score;
    /**
     * 电影评论数量
     */
    public int reviewCount;

    public MovieVO(String id, String name, String releaseDate, Image poster) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.poster = poster;
    }

    public MovieVO(String id, String name, int duration, String genre, String releaseDate, String country, String language, String plot, String director, String writers, String actors, double rating, double score, int reviewCount) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genre = getList(genre);
        this.releaseDate = releaseDate;
        this.country = country;
        this.language = language;
        this.plot = getPlot(plot);
        this.director = getList(director);
        this.writers = getList(writers);
        this.actors = getList(actors);
        this.rating = rating;
        this.score = score;
        this.reviewCount = reviewCount;
    }

    public MovieVO(MoviePO moviePO) {
        this.id = moviePO.getId();
        this.name = moviePO.getName();
        this.duration = moviePO.getDuration();
        this.genre = getList(moviePO.getGenre());
        this.releaseDate = getReleaseDate(moviePO.getReleaseDate());
        this.country = moviePO.getCountry();
        this.language = moviePO.getLanguage();
        this.plot = getPlot(moviePO.getPlot());
        this.director = getList(moviePO.getDirector());
        this.writers = getList(moviePO.getWriters());
        this.actors = getList(moviePO.getActors());
        this.rating = moviePO.getRating();
        this.score = moviePO.getScore();
        this.reviewCount = moviePO.getReviewCount();
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MovieVO) {
            MovieVO movieVO = (MovieVO) o;
            return compareData(movieVO);
        }
        return false;
    }

    public boolean compareData(MovieVO movieVO) {
        return judgeEqual(id, movieVO.id)
                && judgeEqual(name, movieVO.name);
    }

    private String getReleaseDate(String releaseDate) {
        if (releaseDate.equals("-1")) {
            releaseDate = "";
        }
        return releaseDate;
    }

    private List<String> getList(String s) {
        List<String> res;
        if (s.equals("N/A")) {
            res = Collections.EMPTY_LIST;
        } else {
            String[] list = s.split(", ");
            res = Arrays.asList(list);
        }
        return res;
    }

    private String getPlot(String s) {
        if (s.equals("N/A")) {
            return "";
        } else {
            return s;
        }
    }


}
