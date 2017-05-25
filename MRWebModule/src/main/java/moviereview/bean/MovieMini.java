package moviereview.bean;

import moviereview.model.Movie;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Kray on 2017/5/15.
 */
public class MovieMini {

    /**
     * 数据库里 id
     */
    private int id;

    /**
     * 电影标题
     */
    private String title;

    /**
     * 海报url
     */
    private String poster;

    /**
     * 电影类型
     */
    private List<Integer>  genre;

    /**
     * 评分(imdb)
     */
    private double score;

    /**
     * 投票数(imdb)
     */
    private int votes;

    /**
     * 上映日期,eg 2017.05.21
     */
    private String releaseDate;

    public MovieMini() {
    }

    public MovieMini(Movie movie, List<Integer> genre) {
        this.id = movie.getId();
        this.title = movie.getTmdbtitle();
        this.poster = movie.getPoster();
        this.genre = genre;
        this.score = movie.getImdb_score();
        this.votes = movie.getImdb_count();
        String date = movie.getRelease_date().toString();
        this.releaseDate = date.replace("-", ".").substring(0,10);
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        if (poster.equals("N/A")) {
            this.poster = null;
        } else {
            this.poster = poster;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getGenre() {
        return genre;
    }

    public void setGenre(List<Integer> genre) {
        this.genre = genre;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator", "\n");

        StringBuilder result = new StringBuilder();
        result.append("----------")
                .append(this.getClass().getName())
                .append("----------")
                .append(lineSeparator);
        //
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                result.append(field.getName());
                if (field.get(this) == null) {
                    result.append(": null    ");
                } else {
                    result.append(": ").append(field.get(this).toString()).append("    ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.append(lineSeparator).append("--------------------").append(lineSeparator);

        return result.toString();
    }
}
