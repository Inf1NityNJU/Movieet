package moviereview.bean;

/**
 * Created by vivian on 2017/5/27.
 */
public class KeywordBean {

    private int id;

    private String chineseValue;

    private String value;

    public KeywordBean() {
    }

    public KeywordBean(int id, String chineseValue, String englishValue) {
        this.id = id;
        this.chineseValue = chineseValue;
        this.value = englishValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChineseValue() {
        return chineseValue;
    }

    public void setChineseValue(String chineseValue) {
        this.chineseValue = chineseValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
