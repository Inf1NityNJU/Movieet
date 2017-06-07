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
     * 电影原始标题
     */
    private String originTitle;

    /**
     * 海报url
     */
    private String poster;

    /**
     * 电影类型
     */
    private List<GenreBean> genre;

    /**
     * 评分(imdb)
     */
    private double scoreFR;

    /**
     * 投票数(imdb)
     */
    private int votesFR;

    private double scoreCN;

    private int votesCN;

    private double rankScore;

    /**
     * 上映日期,eg 2017.05.21
     */
    private String releaseDate;

    public MovieMini() {
    }

    public MovieMini(Movie movie, List<GenreBean> genre) {
        this.id = movie.getId();
        this.title = movie.getTmdbtitle();
        this.originTitle = movie.getTmdb_original_title();
        this.poster = "https://image.tmdb.org/t/p/w500" + movie.getPoster();
        this.genre = genre;
        if (movie.getImdb_score() != null) {
            this.scoreFR = movie.getImdb_score();
        }
        if (movie.getImdb_count() != null) {
            this.votesFR = movie.getImdb_count();
        }
        if (movie.getDouban_score() != null) {
            this.scoreCN = movie.getDouban_score();
        }
        if (movie.getDouban_count() != null) {
            this.votesCN = movie.getDouban_count();
        }
        if (movie.getRelease_date() != null) {
            String date = movie.getRelease_date().toString();
            this.releaseDate = date.replace("-", ".").substring(0, 10);
        }
    }

    public MovieMini(Movie movie, double rankScore, List<GenreBean> genre) {
        this.id = movie.getId();
        this.title = movie.getTmdbtitle();
        this.originTitle = movie.getTmdb_original_title();
        this.poster = "https://image.tmdb.org/t/p/w500" + movie.getPoster();
        this.genre = genre;
        if (movie.getImdb_score() != null) {
            this.scoreFR = movie.getImdb_score();
        }
        if (movie.getImdb_count() != null) {
            this.votesFR = movie.getImdb_count();
        }
        if (movie.getDouban_score() != null) {
            this.scoreCN = movie.getDouban_score();
        }
        if (movie.getDouban_count() != null) {
            this.votesCN = movie.getDouban_count();
        }
        if (movie.getRelease_date() != null) {
            String date = movie.getRelease_date().toString();
            this.releaseDate = date.replace("-", ".").substring(0, 10);
        }
        this.rankScore = rankScore;
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

    public String getOriginTitle() {
        return originTitle;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    public List<GenreBean> getGenre() {
        return genre;
    }

    public void setGenre(List<GenreBean> genre) {
        this.genre = genre;
    }

    public double getScoreFR() {
        return scoreFR;
    }

    public void setScoreFR(double scoreFR) {
        this.scoreFR = scoreFR;
    }

    public int getVotesFR() {
        return votesFR;
    }

    public void setVotesFR(int votesFR) {
        this.votesFR = votesFR;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getScoreCN() {
        return scoreCN;
    }

    public void setScoreCN(double scoreCN) {
        this.scoreCN = scoreCN;
    }

    public int getVotesCN() {
        return votesCN;
    }

    public void setVotesCN(int votesCN) {
        this.votesCN = votesCN;
    }

    public double getRankScore() {
        return rankScore;
    }

    public void setRankScore(double rankScore) {
        this.rankScore = rankScore;
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
