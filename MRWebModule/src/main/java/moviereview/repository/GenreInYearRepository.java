package moviereview.repository;

import moviereview.model.GenreInYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by vivian on 2017/6/12.
 */
public interface GenreInYearRepository  extends JpaRepository<GenreInYear, Integer> {

//    @Query(value = "select * from genre_in_year where genreId = ?1", nativeQuery = true)
    public List<GenreInYear> findGenreInYearsByGenreId(int genreId);
}
