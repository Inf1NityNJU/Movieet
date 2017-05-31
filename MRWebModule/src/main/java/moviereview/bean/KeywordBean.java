package moviereview.bean;

/**
 * Created by vivian on 2017/5/27.
 */
public class KeywordBean {
    private int id;

    private String value;

    public KeywordBean() {
    }

    public KeywordBean(int id, String value) {
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
