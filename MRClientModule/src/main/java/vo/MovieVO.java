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
     * 发行日期
     */
    public String realeseDate;

    /**
     * 电影海报
     */
    public Image poster;



    public String getRealeseDate() {
        return realeseDate;
    }

    public void setRealeseDate(String realeseDate) {
        this.realeseDate = realeseDate;
    }

    public Image getPoster() {
        return poster;
    }

    public void setPoster(Image poster) {
        this.poster = poster;
    }

    public MovieVO(String id, String name, String realeseDate, Image poster) {

        this.id = id;
        this.name = name;
        this.realeseDate = realeseDate;
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
