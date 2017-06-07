package moviereview.bean;

/**
 * Created by vivian on 2017/6/7.
 */
public class GenreCountBean {
    private Integer id;

    /**
     * foreign/domestic
     */
    private String area;

    /**
     * 超过均分的电影数量
     */
    private int more;

    /**
     * 低于均分的电影数量
     */
    private int less;

    public GenreCountBean() {
    }

    public GenreCountBean(Integer id, String area, int more, int less) {
        this.id = id;
        this.area = area;
        this.more = more;
        this.less = less;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public int getLess() {
        return less;
    }

    public void setLess(int less) {
        this.less = less;
    }
}
