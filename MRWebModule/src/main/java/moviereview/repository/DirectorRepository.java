package moviereview.repository;

import moviereview.model.Director;
import moviereview.model.DirectorRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface DirectorRepository extends JpaRepository<Director, String> {

    @Query(value = "select name from tmdb_director where tmdbpeopleid = ?1" ,nativeQuery = true)
    public String findDirectorNameById(int directorId);

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

    @Query(value = "select imdb_score from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_director where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<BigDecimal> findScoreEnByDirectorId(int directorId);

    @Query(value = "select douban_score from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_director where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<BigDecimal> findScoreCnByDirectorId(int directorId);

    @Query(value = "select imdb_count from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_director where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<Integer> findVoteEnByDirectorId(int directorId);

    @Query(value = "select douban_count from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_director where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<Integer> findVoteCnByDirectorId(int directorId);

    @Query(value = "select revenue from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_director where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<Integer> findBoxOfficeByDirectorId(int directorId);

    @Query(value = "SELECT min(factor) from user_director_factor where user_id = ?1 OR user_id = ?2 " +
            "group by name having count(factor) = 2", nativeQuery = true)
    public ArrayList<Double> getSimilarDirectorFactor(int user1, int user2);

    @Query(value = "SELECT sum(factor) from user_director_factor where user_id = ?1",
            nativeQuery = true)
    public double getDirectorFactor(int user);
}