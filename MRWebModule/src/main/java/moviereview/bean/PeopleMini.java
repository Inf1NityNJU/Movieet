package moviereview.bean;

/**
 * Created by vivian on 2017/5/26.
 */
public class PeopleMini {
    private int id;

    private String name;

    private String poster;

    public PeopleMini() {
    }

    public PeopleMini(int id, String name, String poster) {
        this.id = id;
        this.name = name;
        this.poster = "https://image.tmdb.org/t/p/w500" + poster;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
