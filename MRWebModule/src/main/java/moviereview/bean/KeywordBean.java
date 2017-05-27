package moviereview.bean;

/**
 * Created by vivian on 2017/5/27.
 */
public class KeywordBean {
    private int keywordId;

    private String value;

    public KeywordBean() {
    }

    public KeywordBean(int keywordId, String value) {
        this.keywordId = keywordId;
        this.value = value;
    }

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
