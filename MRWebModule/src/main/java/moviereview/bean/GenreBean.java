package moviereview.bean;

/**
 * Created by vivian on 2017/5/27.
 */
public class GenreBean {
    private int id;

    private String value;

    public GenreBean() {
    }

    public GenreBean(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
