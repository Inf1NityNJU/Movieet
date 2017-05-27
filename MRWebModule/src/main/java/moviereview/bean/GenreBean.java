package moviereview.bean;

/**
 * Created by vivian on 2017/5/27.
 */
public class GenreBean {
    private int genreId;

    private String value;

    public GenreBean() {
    }

    public GenreBean(int genreId, String value) {
        this.genreId = genreId;
        this.value = value;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
