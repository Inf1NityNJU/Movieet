package moviereview.bean;

/**
 * Created by vivian on 2017/5/26.
 */
public class PeopleMini {
    private int id;

    private String name;

    public PeopleMini() {
    }

    public PeopleMini(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
