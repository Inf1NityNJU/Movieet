package po;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/21.
 */
public class MovieGenrePO {
    /**
     * 电影分类
     */
    private List<String> tags;

    /**
     * 各分类里的电影数量
     */
    private List<Integer> amounts;

    public MovieGenrePO(List<String> tags, List<Integer> amounts) {
        this.tags = tags;
        this.amounts = amounts;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Integer> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<Integer> amounts) {
        this.amounts = amounts;
    }
}
