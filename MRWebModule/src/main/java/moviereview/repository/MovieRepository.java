package moviereview.repository;

import moviereview.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/5/14.
 */
public interface MovieRepository extends JpaRepository<Movie, String> {

    @Query(value = "SELECT * FROM movie WHERE idmovie IN " +
            "(SELECT idmovie FROM is_genre WHERE idgenre = ?1) LIMIT ?2", nativeQuery = true)
    public List<Movie> findMovieByGenre(String Genre, int limit);
}
