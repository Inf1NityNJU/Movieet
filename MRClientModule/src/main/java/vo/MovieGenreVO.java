package vo;

import java.util.List;

/**
 * Created by vivian on 2017/3/21.
 */
public class MovieGenreVO {
    //电影分类
    public List<String> tags;

    //各分类里的电影数量
    public List<Integer> amounts;

    public MovieGenreVO(List<String> tags, List<Integer> amounts) {
        this.tags = tags;
        this.amounts = amounts;
    }
}
