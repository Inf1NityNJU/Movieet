package moviereview.repository;

import moviereview.model.Actor;
import moviereview.model.Director;
import moviereview.model.Genre;
import moviereview.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by Kray on 2017/5/14.
 */
public interface MovieRepository extends JpaRepository<Movie, String> { //第一个为该接口处理的域对象类型，第二个为该域对象的主键类型。

    /**
     * 根据关键字找电影
     *
     * @param title
     * @return
     */
    @Query(value = "SELECT * FROM tmdb_movie WHERE tmdbtitle LIKE ?1 ORDER BY douban_score ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleScoreAsc(String title, int start, int count);

    @Query(value = "SELECT * FROM tmdb_movie WHERE tmdbtitle LIKE ?1 ORDER BY douban_score DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleScoreDesc(String title, int start, int count);

    @Query(value = "SELECT * FROM tmdb_movie WHERE tmdbtitle LIKE ?1 ORDER BY release_date ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleDateAsc(String title, int start, int count);

    @Query(value = "SELECT * FROM tmdb_movie WHERE tmdbtitle LIKE ?1 ORDER BY release_date DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleDateDesc(String title, int start, int count);

    @Query(value = "SELECT COUNT(*) FROM tmdb_movie WHERE tmdbtitle LIKE ?1", nativeQuery = true)
    public Integer findMovieCountByTitle(String title);

    /**
     * 根据类型查找电影
     *
     * @param genres 类型
     * @param count  限定数量
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_genre t WHERE t.tmdbgenreid IN " +
            "(SELECT g.tmdbgenreid FROM tmdb_genre g WHERE g.tmdbgenre_en IN ?1)) " +
            "ORDER BY m.douban_score ASC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByGenreScoreAsc(List<String> genres, int start, int count);

    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_genre t WHERE t.tmdbgenreid IN " +
            "(SELECT g.tmdbgenreid FROM tmdb_genre g WHERE g.tmdbgenre_en IN ?1)) " +
            "ORDER BY m.douban_score DESC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByGenreScoreDesc(List<String> genres, int start, int count);

    //
    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_genre t WHERE t.tmdbgenreid IN " +
            "(SELECT g.tmdbgenreid FROM tmdb_genre g WHERE g.tmdbgenre_en IN ?1)) " +
            "ORDER BY m.release_date ASC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByGenreDateAsc(List<String> genres, int start, int count);

    //
    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_genre t WHERE t.tmdbgenreid IN " +
            "(SELECT g.tmdbgenreid FROM tmdb_genre g WHERE g.tmdbgenre_en IN ?1)) " +
            "ORDER BY m.release_date DESC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByGenreDateDesc(List<String> genres, int start, int count);

    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_genre t WHERE t.tmdbgenreid IN " +
            "(SELECT g.tmdbgenreid FROM tmdb_genre g WHERE g.tmdbgenre_en = ?1)) " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByGenre(String Genre, int start, int count);

    @Query(value = "SELECT COUNT(*) FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_genre t WHERE t.tmdbgenreid IN " +
            "(SELECT g.tmdbgenreid FROM tmdb_genre g WHERE g.tmdbgenre_en IN ?1)) ", nativeQuery = true)
    public Integer findMovieCountByGenre(List<String> genres);

    /**
     * 根据演员查找电影
     *
     * @param Actor 演员名
     * @param count 限定数量
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_actor t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_actor g WHERE g.name = ?1)) " +
            "ORDER BY m.douban_score ASC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActorScoreAsc(String Actor, int start, int count);

    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_actor t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_actor g WHERE g.name = ?1)) " +
            "ORDER BY m.douban_score DESC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActorScoreDesc(String Actor, int start, int count);

    //
    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_actor t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_actor g WHERE g.name = ?1)) " +
            "ORDER BY m.release_date ASC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActorDateAsc(String Actor, int start, int count);

    //
    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_actor t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_actor g WHERE g.name = ?1)) " +
            "ORDER BY m.release_date DESC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActorDateDesc(String Actor, int start, int count);

    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_actor t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_actor g WHERE g.name = ?1)) " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActor(String Actor, int start, int count);

    @Query(value = "SELECT COUNT(*) FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_actor t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_actor g WHERE g.name = ?1)) " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public Integer findMovieCountByActor(String Actor);

    /**
     * 根据导演查找电影
     *
     * @param Director 导演名
     * @param count    限定数量
     * @return 查询到的电影
     */

    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_director t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_director g WHERE g.name = ?1)) " +
            "ORDER BY m.douban_score ASC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirectorScoreAsc(String Director, int start, int count);

    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_director t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_director g WHERE g.name = ?1)) " +
            "ORDER BY m.douban_score DESC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirectorScoreDesc(String Director, int start, int count);

    //
    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_director t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_director g WHERE g.name = ?1)) " +
            "ORDER BY m.release_date ASC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirectorDateAsc(String Director, int start, int count);

    //
    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_director t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_director g WHERE g.name = ?1)) " +
            "ORDER BY m.release_date DESC " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirectorDateDesc(String Director, int start, int count);


    @Query(value = "SELECT * FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_director t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_director g WHERE g.name = ?1)) " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirector(String Director, int start, int count);


    @Query(value = "SELECT COUNT(*) FROM tmdb_movie m WHERE m.tmdbid IN " +
            "(SELECT t.tmdbid FROM tmdb_movie_director t WHERE t.tmdbpeopleid IN " +
            "(SELECT g.tmdbpeopleid FROM tmdb_director g WHERE g.name = ?1)) " +
            "LIMIT ?2, ?3", nativeQuery = true)
    public Integer findMovieCountByDirector(String Director);

    /**
     * 根据最新时间查找电影
     *
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM tmdb_movie ORDER BY release_date DESC LIMIT ?1", nativeQuery = true)
    public List<Movie> findLatestMovies(int limit);


    /**
     * 根据电影 ID 找电影
     * <p>
     * //     * @param movieID
     *
     * @return
     */
//    public Movie findMovieById(String idmovie);
    @Query(value = "SELECT * FROM tmdb_movie WHERE tmdbid = ?1", nativeQuery = true)
    public Movie findMovieById(int movieID);

    @Query(value = "SELECT tmdbid FROM tmdb_movie_genre WHERE tmdbgenreid = ?1 LIMIT 10000", nativeQuery = true)
    public List<Integer> findMovieIdByGenre(Integer genre);

    @Query(value = "SELECT COUNT(*) FROM tmdb_movie WHERE year(release_date) = ?2 AND (tmdbgenreid IN ?1)", nativeQuery = true)
    public Integer countByYears(List<Integer> movieId, String year);

    @Query(value = "SELECT AVG(*) FROM tmdb_movie WHERE year(release_date) = ?2 AND (tmdbgenreid IN ?1)", nativeQuery = true)
    public Double avgByYears(List<Integer> movieId, String year);

    @Query(value = "SELECT * FROM tmdb_movie m WHERE tmdbid <> ?1 AND imdb_score > ?2 AND imdb_score < ?3 " +
            "AND NOT EXISTS " +
            "(SELECT * FROM tmdb_genre t WHERE tmdbgenreid IN ?4 AND NOT EXISTS " +
            "(SELECT * FROM tmdb_movie_genre i WHERE i.tmdbid = m.tmdbid AND i.tmdbgenreid = t.tmdbgenreid)) " +
            "LIMIT ?5", nativeQuery = true)
    public List<Movie> findSimilarMovie(int tmdbgenreid, double low, double high, List<Integer> genres, int limit);

    /**
     * 根据电影id找电影的属性（类型、导演、演员等）
     * @param movieid
     * @return
     */
    @Query(value = "select tmdbgenreid from tmdb_movie_genre where tmdbid = ?1", nativeQuery = true)
    public List<Integer> findMovieGenreByMovieId(int movieid);
}