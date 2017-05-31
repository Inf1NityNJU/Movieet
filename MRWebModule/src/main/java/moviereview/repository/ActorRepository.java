package moviereview.repository;

import moviereview.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface ActorRepository extends JpaRepository<Actor, String> {

    @Query(value = "select name from tmdb_actor where tmdbpeopleid = ?1" ,nativeQuery = true)
    public String findActorById(int actorId);

    @Query(value = "select tmdbpeopleid from tmdb_movie_actor where tmdbid = ?1", nativeQuery = true)
    public List<Integer> findActorIdByMovieId(int movieId);

    @Query(value = "SELECT tmdbpeopleid FROM tmdb_actor WHERE name LIKE ?1 ORDER BY popularity ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<Integer> findActorByKeywordPopularityAsc(String keyword, int start, int count);

    @Query(value = "SELECT tmdbpeopleid FROM tmdb_actor WHERE name LIKE ?1 ORDER BY popularity DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<Integer> findActorByKeywordPopularityDesc(String keyword, int start, int count);

    @Query(value = "select * from tmdb_actor where tmdbpeopleid = ?1", nativeQuery = true)
    public Actor findActorByActorId(int actorId);

    //for predict
    @Query(value = "select factor from actor_for_predict where id = ?1", nativeQuery = true)
    public double findActorFactors(int actorId);

}
