package moviereview.repository;

import moviereview.model.Actor;
import moviereview.model.ActorRank;
import moviereview.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface ActorRepository extends JpaRepository<Actor, String> {

    @Query(value = "select name from tmdb_actor where tmdbpeopleid = ?1", nativeQuery = true)
    public String findActorById(int actorId);

    @Query(value = "select tmdbpeopleid from tmdb_movie_actor where tmdbid = ?1", nativeQuery = true)
    public List<Integer> findActorIdByMovieId(int movieId);

    @Query(value = "SELECT tmdbpeopleid FROM tmdb_actor WHERE name LIKE ?1 ", nativeQuery = true)
    public List<Integer> findActorByKeyword(String keyword);

    @Query(value = "SELECT tmdbpeopleid FROM tmdb_actor WHERE name LIKE ?1 ORDER BY popularity ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Integer> findActorByKeywordPopularityAsc(String keyword, int start, int count);

    @Query(value = "SELECT tmdbpeopleid FROM tmdb_actor WHERE name LIKE ?1 ORDER BY popularity DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Integer> findActorByKeywordPopularityDesc(String keyword, int start, int count);

    @Query(value = "select * from tmdb_actor where tmdbpeopleid = ?1", nativeQuery = true)
    public Actor findActorByActorId(int actorId);

    //for wekaPredict
    @Query(value = "select factor from actor_for_predict where id = ?1", nativeQuery = true)
    public double findActorFactors(int actorId);

    @Query(value = "select imdb_score from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_actor where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<BigDecimal> findScoreEnByActorId(int actorId);

    @Query(value = "select douban_score from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_actor where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<BigDecimal> findScoreCnByActorId(int actorId);

    @Query(value = "select imdb_count from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_actor where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<Integer> findVoteEnByActorId(int actorId);

    @Query(value = "select douban_count from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_actor where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<Integer> findVoteCnByActorId(int actorId);

    @Query(value = "select revenue from tmdb_movie where tmdbid in " +
            "(select tmdbid from tmdb_movie_actor where tmdbpeopleid = ?1)"
            , nativeQuery = true)
    public List<Integer> findBoxOfficeByActorId(int actorId);

    //for rank
    @Query(value = "select count(*) from tmdb_actor", nativeQuery = true)
    public int countActor();

    @Query(value = "select tmdbpeopleid from tmdb_actor", nativeQuery = true)
    public List<Integer> findAllActor();

    @Transactional
    @Modifying
    @Query(value = "update tmdb_actor set rank = ?2 where tmdbpeopleid = ?1", nativeQuery = true)
    public void  addRank(int id, Double rank);

    @Query(value = "select * from tmdb_actor order by rank desc LIMIT ?1", nativeQuery = true)
    public List<Actor> findActorByRank(int limit);
}
