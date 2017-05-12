package moviereview.util;

import java.util.HashMap;

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
    Romance,
    SciFi,
    Short,
    Others;


    /**
     * This map is used to bound unique string from toString method and enum value
     */
    private static final HashMap<String, MovieGenre> stringToEnum = new HashMap<String, MovieGenre>();

    //bound unique string from toString method and enum value
    static {
        for (MovieGenre movieGenre : MovieGenre.values()) {
            stringToEnum.put(movieGenre.toString(), movieGenre);
        }
    }

    public static MovieGenre getMovieGenreByName(String name) {
        for (MovieGenre type : MovieGenre.values()) {
            if (name.equals(type.toString()))
                return type;
        }
        return null;
    }

    public String getGenreName() {
        return toString();
    }

    public String toUrlString() {
        switch (this) {
            case Others:
                return "n/a";
            case SciFi:
                return "sci-fi";
            default:
                return super.toString().toLowerCase();
        }
    }


    /**
     * Use true name to get the enum value
     *
     * @param name True name
     * @return Enum value or null if the true name isn't associate with an enum value
     */
    public MovieGenre fromString(String name) {
        return stringToEnum.get(name);
    }

}
