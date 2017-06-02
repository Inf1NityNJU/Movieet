package moviereview.bean;

/**
 * Created by vivian on 2017/5/26.
 */
public class PeopleMini {
    private int id;

    private String name;

    private String photo;

    public PeopleMini() {
    }

    public PeopleMini(int id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = "https://image.tmdb.org/t/p/w500" + photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
