package util;

/**
 * Created by vivian on 2017/3/27.
 * 电影分类
 * 以下分类来源于IMDB中已经出现的数据
 */
public enum MovieGenre {
    ALL,
    Action ,
    Adventure ,
    Animation ,
    Biography ,
    Comedy ,
    Crime ,
    Documentary ,
    Drama ,
    Family ,
    Fantasy ,
    History ,
    Horror ,
    Music ,
    Musical ,
    Mystery ,
    Others,
    Romance ,
    SciFi ,
    Short,
    Sport ,
    Thriller ,
    War ;

    public String getGenreName() {
        return toString().charAt(0) + toString().toLowerCase().substring(1);
    }
}
