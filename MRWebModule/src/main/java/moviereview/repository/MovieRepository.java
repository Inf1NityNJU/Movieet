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
    @Query(value = "SELECT * FROM movie WHERE kind = 'movie' AND title LIKE ?1 ORDER BY rank ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleScoreAsc(String title, int start, int count);

    @Query(value = "SELECT * FROM movie WHERE kind = 'movie' AND title LIKE ?1 ORDER BY rank DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleScoreDesc(String title, int start, int count);

    @Query(value = "SELECT movie.* FROM movie INNER JOIN is_release_date date ON date.idmovie = movie.idmovie " +
            "WHERE kind = 'movie' AND movie.title LIKE ?1 ORDER BY date.iddate ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleDateAsc(String title, int start, int count);

    @Query(value = "SELECT * FROM movie WHERE movie.title LIKE ?1 AND movie.idmovie IN " +
            "(SELECT date.idmovie FROM is_release_date date WHERE ) ORDER BY date.iddate DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleDateDesc(String title, int start, int count);

    @Query(value = "SELECT COUNT(*) FROM movie WHERE movie.title LIKE ?1", nativeQuery = true)
    public Integer findMovieCountByTitle(String title);

    /**
     * 根据类型查找电影
     *
     * @param genres 类型
     * @param count 限定数量
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM movie m WHERE m.kind = 'movie' AND NOT EXISTS " +
            "(SELECT * FROM genre g WHERE g.idgenre IN ?1 AND NOT EXISTS " +
            "(SELECT * FROM is_genre i WHERE i.idmovie = m.idmovie AND i.idgenre = g.idgenre)) " +
            "ORDER BY m.rank ASC LIMIT ?2, ?3",nativeQuery = true)
    public List<Movie> findMovieByGenreScoreAsc(List<String> genres, int start, int count);

    @Query(value = "SELECT * FROM movie m WHERE m.kind = 'movie' AND NOT EXISTS " +
            "(SELECT * FROM genre g WHERE g.idgenre IN ?1 AND NOT EXISTS " +
            "(SELECT * FROM is_genre i WHERE i.idmovie = m.idmovie AND i.idgenre = g.idgenre)) " +
            "ORDER BY m.rank DESC LIMIT ?2, ?3",nativeQuery = true)
    public List<Movie> findMovieByGenreScoreDesc(List<String> genres, int start, int count);

    //
    @Query(value = "SELECT movie.* FROM movie m, is_release_date date WHERE kind = 'movie' AND m.idmovie = date.idmovie AND " +
            "NOT EXISTS (SELECT * FROM genre g WHERE g.idgenre IN ?1 AND NOT EXISTS " +
            "(SELECT * FROM is_genre i WHERE i.idmovie = m.idmovie AND i.idgenre = g.idgenre)) " +
            "ORDER BY date.iddate ASC LIMIT ?2, ?3",nativeQuery = true)
    public List<Movie> findMovieByGenreDateAsc(List<String> genres, int start, int count);

    //
    @Query(value = "SELECT movie.* FROM movie m, is_release_date date WHERE kind = 'movie' AND m.idmovie = date.idmovie AND " +
            "NOT EXISTS (SELECT * FROM genre g WHERE g.idgenre IN ?1 AND NOT EXISTS " +
            "(SELECT * FROM is_genre i WHERE i.idmovie = m.idmovie AND i.idgenre = g.idgenre)) " +
            "ORDER BY date.iddate DESC LIMIT ?2, ?3",nativeQuery = true)
    public List<Movie> findMovieByGenreDateDesc(List<String> genres, int start, int count);

    @Query(value = "SELECT * FROM movie WHERE idmovie IN " +
            "(SELECT idmovie FROM is_genre WHERE idgenre = ?1) LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByGenre(String Genre, int start, int count);

    @Query(value = "SELECT COUNT(*) FROM movie m WHERE m.kind = 'movie' AND NOT EXISTS " +
            "(SELECT * FROM genre g WHERE g.idgenre IN ?1 AND NOT EXISTS " +
            "(SELECT * FROM is_genre i WHERE i.idmovie = m.idmovie AND i.idgenre = g.idgenre))",nativeQuery = true)
    public Integer findMovieCountByGenre(List<String> genres);

    /**
     * 根据演员查找电影
     *
     * @param Actor 演员名
     * @param count 限定数量
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM movie WHERE kind = 'movie' AND idmovie IN " +
            "(SELECT idmovie FROM is_actor WHERE idactor LIKE ?1) ORDER BY rank ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActorScoreAsc(String Actor, int start, int count);

    @Query(value = "SELECT * FROM movie WHERE kind = 'movie' AND idmovie IN " +
            "(SELECT idmovie FROM is_actor WHERE idactor LIKE ?1) ORDER BY rank DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActorScoreDesc(String Actor, int start, int count);

    //
    @Query(value = "SELECT movie.* FROM movie INNER JOIN is_actor ON is_actor.idmovie = movie.idmovie" +
            " INNER JOIN is_release_date date ON date.idmovie = movie.idmovie" +
            " WHERE kind = 'movie' AND is_actor.idactor LIKE ?1 ORDER BY date.iddate ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActorDateAsc(String Actor, int start, int count);

    //
    @Query(value = "SELECT movie.* FROM movie, is_release_date date, is_actor actor " +
            "WHERE kind = 'movie' AND movie.idmovie = date.idmovie AND movie.idmovie = actor.idmovie " +
            "AND actor.idactor LIKE ?1 ORDER BY date.iddate DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActorDateDesc(String Actor, int start, int count);

    @Query(value = "SELECT * FROM movie WHERE idmovie IN " +
            "(SELECT idmovie FROM is_actor WHERE idactor LIKE ?1) LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActor(String Actor, int start, int count);

    @Query(value = "SELECT COUNT(*) FROM movie WHERE idmovie IN " +
            "(SELECT idmovie FROM is_actor WHERE idactor LIKE ?1)", nativeQuery = true)
    public Integer findMovieCountByActor(String Actor);

    /**
     * 根据导演查找电影
     *
     * @param Director 导演名
     * @param count    限定数量
     * @return 查询到的电影
     */

    @Query(value = "SELECT * FROM movie WHERE kind = 'movie' AND idmovie IN " +
            "(SELECT idmovie FROM is_director WHERE idactor LIKE ?1) ORDER BY rank ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirectorScoreAsc(String Director, int start, int count);

    @Query(value = "SELECT * FROM movie WHERE kind = 'movie' AND idmovie IN " +
            "(SELECT idmovie FROM is_director WHERE idactor LIKE ?1) ORDER BY rank DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirectorScoreDesc(String Director, int start, int count);

    //
    @Query(value = "SELECT movie.* FROM movie, is_release_date date, is_director director " +
            "WHERE kind = 'movie' AND movie.idmovie = date.idmovie AND movie.idmovie = director.idmovie " +
            "AND director.iddirector LIKE ?1 ORDER BY date.iddate ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirectorDateAsc(String Director, int start, int count);

    //
    @Query(value = "SELECT movie.* FROM movie, is_release_date date, is_director director " +
            "WHERE kind = 'movie' AND movie.idmovie = date.idmovie AND movie.idmovie = director.idmovie " +
            "AND director.iddirector LIKE ?1 ORDER BY date.iddate DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirectorDateDesc(String Director, int start, int count);


    @Query(value = "SELECT * FROM movie WHERE kind = 'movie' AND idmovie IN " +
            "(SELECT idmovie FROM is_director WHERE iddirector LIKE ?1) LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirector(String Director, int start, int count);


    @Query(value = "SELECT COUNT(*) FROM movie WHERE kind = 'movie' AND idmovie IN " +
            "(SELECT idmovie FROM is_director WHERE iddirector LIKE ?1)", nativeQuery = true)
    public Integer findMovieCountByDirector(String Director);

    /**
     * 根据最新时间查找电影
     *
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM movie WHERE votes > 10 AND idmovie IN ?1 LIMIT ?2", nativeQuery = true)
    public List<Movie> findLatestMovies(List<String> movieId, int limit);

    @Query(value = "SELECT idmovie FROM is_release_date WHERE iddate < ?3 ORDER BY iddate DESC LIMIT ?1, ?2", nativeQuery = true)
    public List<String> findLatestMovieId(int start, int count, String now);


    /**
     * 根据电影 ID 找电影
     * <p>
     * //     * @param movieID
     *
     * @return
     */
//    public Movie findMovieById(String idmovie);
    @Query(value = "SELECT * FROM movie WHERE idmovie = ?1", nativeQuery = true)
    public Movie findMovieById(String movieID);

    @Query(value = "SELECT idmovie FROM is_genre WHERE idgenre = ?1 LIMIT 10000", nativeQuery = true)
    public List<String> findMovieIdByGenre(String genre);

    @Query(value = "SELECT COUNT(*) FROM movie WHERE (year = ?2) AND (idmovie IN ?1)", nativeQuery = true)
    public Integer countByYears(List<String> movieId, String year);

    @Query(value = "SELECT AVG(rank) FROM movie WHERE (year = ?2) AND (idmovie IN ?1) AND (rank <> 0)", nativeQuery = true)
    public Double avgByYears(List<String> movieId, String year);

    @Query(value = "SELECT * FROM movie m WHERE idmovie <> ?1 AND rank > ?2 AND rank < ?3 " +
            "AND m.kind = 'movie' AND NOT EXISTS " +
            "(SELECT * FROM genre g WHERE g.idgenre IN ?4 AND NOT EXISTS " +
            "(SELECT * FROM is_genre i WHERE i.idmovie = m.idmovie AND i.idgenre = g.idgenre)) " +
            "LIMIT ?5",nativeQuery = true)
    public List<Movie> findSimilarMovie(String idmovie, double low, double high, List<String> genres, int limit);
}