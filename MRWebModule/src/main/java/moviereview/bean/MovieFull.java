package moviereview.bean;

import moviereview.model.*;
import moviereview.util.ShellUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/5/15.
 */
public class MovieFull {


    private String FilePath = "/Users/Kray/Desktop/PythonHelper/iteration3/";

    /**
     * 海报
     */
    private String poster;

    /**
     * 情节
     */
    private String plot;

    /**
     * 数据库里 id
     */
    private String id;

    /**
     * 电影标题
     */
    private String title;

    /**
     * 年份
     */
    private String year;

    /**
     * 种类
     */
    private String kind;

    private List<String> directorIDs = new ArrayList<>();

    /**
     * 类型
     */
    private List<String> genreIDs = new ArrayList<>();

    /**
     * 关键字
     */
//    private List<Keyword> keyword = new ArrayList<>();

    /**
     * 上映日期
     */
    private List<String> releaseDateIDs = new ArrayList<>();

    /**
     * 演员
     */
    private List<String> actorIDs = new ArrayList<>();

    public MovieFull(Movie movie, String poster, String plot) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.kind = movie.getKind();
        this.poster = poster;
        this.plot = plot;

        this.actorIDs = new ArrayList<>();
        for (Actor actor : movie.getActor()) {
            this.actorIDs.add(actor.getIdactor());
        }
        this.directorIDs = new ArrayList<>();
        for (Director director : movie.getDirector()) {
            this.directorIDs.add(director.getIddirector());
        }
        this.genreIDs = new ArrayList<>();
        for (Genre genre : movie.getGenre()) {
            this.genreIDs.add(genre.getIdgenre());
        }
        this.releaseDateIDs = new ArrayList<>();
        for (ReleaseDate releaseDate : movie.getReleaseDate()) {
            this.releaseDateIDs.add(releaseDate.getIddate());
        }

        String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + FilePath + "MovieIMDBInfoGetter.py " + movie.getTitle() + " " + movie.getYear());
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Map<String, Object> jsonMap = jsonObject.toMap();
            this.plot = (String)jsonMap.get("Plot");
            this.poster = (String)jsonMap.get("Poster");
        } catch (Exception e) {
            e.printStackTrace();
            this.plot = "";
            this.poster = "";
        }

    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<String> getDirectorIDs() {
        return directorIDs;
    }

    public void setDirectorIDs(List<String> directorIDs) {
        this.directorIDs = directorIDs;
    }

    public List<String> getGenreIDs() {
        return genreIDs;
    }

    public void setGenreIDs(List<String> genreIDs) {
        this.genreIDs = genreIDs;
    }

    public List<String> getReleaseDateIDs() {
        return releaseDateIDs;
    }

    public void setReleaseDateIDs(List<String> releaseDateIDs) {
        this.releaseDateIDs = releaseDateIDs;
    }

    public List<String> getActorIDs() {
        return actorIDs;
    }

    public void setActorIDs(List<String> actorIDs) {
        this.actorIDs = actorIDs;
    }
}
