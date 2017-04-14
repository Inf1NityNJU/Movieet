package vo;


import javafx.scene.image.Image;

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

    public MovieVO(String id, String name, String releaseDate, Image poster) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.poster = poster;
    }

    public MovieVO(String id, String name, int duration, String genre, String releaseDate, String country, String language, String plot, String director, String writers, String actors) {
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

    private List<String> getList(String s){
        List<String> res;
        if(s.equals("N/A")){
            res = Collections.EMPTY_LIST;
        } else {
            String[] list = s.split(",");
            res = Arrays.asList(list);
        }
        return res;
    }

    private String getPlot(String s){
        if (s.equals("N/A")){
            return "";
        } else {
            return s;
        }
    }




}
