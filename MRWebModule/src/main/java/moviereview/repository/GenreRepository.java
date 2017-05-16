package moviereview.repository;

import moviereview.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface GenreRepository extends JpaRepository<Genre, String> {

    /**
     * 找所有的类别
     *
     * @return
     */
    @Query(value = "SELECT * FROM genre", nativeQuery = true)
    public List<Genre> findGenre();
}
