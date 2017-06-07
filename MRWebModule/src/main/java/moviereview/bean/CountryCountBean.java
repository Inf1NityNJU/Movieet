package moviereview.bean;

/**
 * Created by Kray on 2017/6/7.
 */
public class CountryCountBean {

    private String name;

    private String area;

    private Integer more;

    private Integer less;

    public CountryCountBean() {
    }

    public CountryCountBean(String name, String area, Integer more, Integer less) {
        this.name = name;
        this.area = area;
        this.more = more;
        this.less = less;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getMore() {
        return more;
    }

    public void setMore(Integer more) {
        this.more = more;
    }

    public Integer getLess() {
        return less;
    }

    public void setLess(Integer less) {
        this.less = less;
    }
}
