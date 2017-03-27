package vo;


import javafx.scene.image.Image;

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
    public String genre;
    
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
    public String director;
    /**
     * 电影创作者
     */

    public String writers;
    /**
     * 主要演员
     */
    public String actors;

    public MovieVO(String id, String name, String releaseDate, Image poster) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.poster = poster;
    }

    public MovieVO(String id, String name, int duration, String genre, String releaseDate, Image poster, String country, String language, String plot, String director, String writers, String actors) {
        this(id, name, releaseDate, poster);
        this.duration = duration;
        this.genre = genre;
        this.country = country;
        this.language = language;
        this.plot = plot;
        this.director = director;
        this.writers = writers;
        this.actors = actors;
    }

    public String getReleaseDate() {

        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Image getPoster() {
        return poster;
    }

    public void setPoster(Image poster) {
        this.poster = poster;
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
        return judgeEqual(id, movieVO.getId())
                && judgeEqual(name, movieVO.getName());
    }


}
