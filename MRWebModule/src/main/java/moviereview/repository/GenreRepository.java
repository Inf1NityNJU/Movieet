package moviereview.repository;

import moviereview.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/5/16.
 */
public interface GenreRepository extends JpaRepository<Genre, String>{

    @Query(value = "select tmdbgenreid from tmdb_movie_genre where tmdbid = ?1", nativeQuery = true)
    public List<Integer> findGenreIdByIdMovie(int idmovie);

    @Query(value = "select tmdbgenre_en from tmdb_genre where tmdbgenreid = ?1" ,nativeQuery = true)
    public String findGenreById(int idgenre);

//
//    /**
//     * 找所有的类别
//     *
//     * @return
//     */
//    @Query(value = "SELECT * FROM genre", nativeQuery = true)
//    public List<GenreBean> findGenre();
}
