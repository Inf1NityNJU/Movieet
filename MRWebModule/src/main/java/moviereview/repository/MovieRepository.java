package moviereview.repository;

import moviereview.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    @Query(value = "SELECT * FROM movie WHERE title LIKE ?1 LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMoviesByTitleLike(String title, int start, int count);

    /**
     * 根据类型查找电影
     *
     * @param Genre 类型
     * @param count 限定数量
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM movie WHERE idmovie IN " +
            "(SELECT idmovie FROM is_genre WHERE idgenre = ?1) LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByGenre(String Genre, int start, int count);

    /**
     * 根据演员查找电影
     *
     * @param Actor 演员名
     * @param count 限定数量
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM movie WHERE idmovie IN " +
            "(SELECT idmovie FROM is_actor WHERE idactor LIKE ?1) LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByActor(String Actor, int start, int count);

    /**
     * 根据导演查找电影
     *
     * @param Director 导演名
     * @param count    限定数量
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM movie WHERE idmovie IN " +
            "(SELECT idmovie FROM is_director WHERE iddirector LIKE ?1) LIMIT ?2, ?3", nativeQuery = true)
    public List<Movie> findMovieByDirector(String Director, int start, int count);

    /**
     * 根据最新时间查找电影
     *
     * @param count 限定数量
     * @return 查询到的电影
     */
    @Query(value = "SELECT * FROM movie movie, is_release_date date WHERE (movie.idmovie = date.idmovie AND iddate < ?3) ORDER BY iddate DESC LIMIT ?1, ?2", nativeQuery = true)
    public List<Movie> findLatestMovies(int start, int count, String now);

}