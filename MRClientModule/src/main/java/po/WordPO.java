package po;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/17.
 */
public class WordPO {
    List<String> topWords;

    public WordPO(List<String> topWords) {
        this.topWords = topWords;
    }

    public List<String> getTopWords() {
        return topWords;
    }

    public void setTopWords(List<String> topWords) {
        this.topWords = topWords;
    }
}
