package po;

/**
 * Created by SilverNarcissus on 2017/3/3.
 */
public class MoviePO {
    /**
     * 电影序列号
     */
    private String id;

    /**
     * 电影名称
     */
    private String name;

    public MoviePO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
