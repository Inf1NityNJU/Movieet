package moviereview.repository;

import moviereview.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

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
    public List<Movie> findMoviesByTitleLike(String title);

}