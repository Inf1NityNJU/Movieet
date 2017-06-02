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

    @Query(value = "SELECT tmdbpeopleid FROM tmdb_director WHERE name LIKE ?1", nativeQuery = true)
    public List<Integer> findDirectorByKeyword(String keyword);

    @Query(value = "SELECT tmdbpeopleid FROM tmdb_director WHERE name LIKE ?1 ORDER BY popularity ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Integer> findDirectorByKeywordPopularityAsc(String keyword, int start, int count);

    @Query(value = "SELECT tmdbpeopleid FROM tmdb_director WHERE name LIKE ?1 ORDER BY popularity DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Integer> findDirectorByKeywordPopularityDesc(String title, int start, int count);

    @Query(value = "select * from tmdb_director where tmdbpeopleid = ?1", nativeQuery = true)
    public Director findDirectorByDirectorId(int directorId);

    //for wekaPredict
    @Query(value = "select factor from director_for_predict where id = ?1", nativeQuery = true)
    public double findDirectorFactors(int directorId);
}