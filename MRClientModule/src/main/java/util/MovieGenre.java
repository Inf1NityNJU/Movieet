package util;

/**
 * Created by vivian on 2017/3/27.
 * 电影分类
 * 以下分类来源于IMDB中已经出现的数据
 */
public enum MovieGenre {
    All,
    Action,
    Adventure,
    Animation,
    Biography,
    Comedy,
    Crime,
    Documentary,
    Drama,
    Family,
    Fantasy,
    History,
    Horror,
    Music,
    Musical,
    Mystery,
    Others,
    Romance,
    SciFi,
    Short;


    public String getGenreName() {
        return toString();
    }

    public static MovieGenre getMovieGenreByName(String name) {
        for (MovieGenre type : MovieGenre.values()) {
            if (name.equals(type.toString()))
                return type;
        }
        return null;
    }
}
