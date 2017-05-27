package moviereview.repository;

import moviereview.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface DirectorRepository extends JpaRepository<Director, String> {

    @Query(value = "select name from tmdb_director where tmdbpeopleid = ?1" ,nativeQuery = true)
    public String findDirectorById(int directorId);

    @Query(value = "select tmdbpeopleid from tmdb_movie_director where tmdbid = ?1", nativeQuery = true)
    public List<Integer> findDirectorIdByMovieId(int movieId);
}