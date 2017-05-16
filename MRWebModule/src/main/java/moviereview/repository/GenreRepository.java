package moviereview.repository;

import moviereview.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/5/16.
 */
public interface GenreRepository extends JpaRepository<Genre, String>{

    @Query(value = "select * from genre where idgenre in" +
            "(select idgenre from is_genre where idmovie = ?1)", nativeQuery = true)
    public List<Genre> findGenreByIdMovie(String idmovie);
//
//    /**
//     * 找所有的类别
//     *
//     * @return
//     */
//    @Query(value = "SELECT * FROM genre", nativeQuery = true)
//    public List<Genre> findGenre();
}
