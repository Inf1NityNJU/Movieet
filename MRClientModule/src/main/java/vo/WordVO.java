package vo;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/17.
 */
public class WordVO {
    private List<String> topWords;

    public WordVO(List<String> topWords) {
        this.topWords = topWords;
    }

    public List<String> getTopWords() {
        return topWords;
    }

    public void setTopWords(List<String> topWords) {
        this.topWords = topWords;
    }
}
