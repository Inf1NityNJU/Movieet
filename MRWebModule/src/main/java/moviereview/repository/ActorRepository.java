package moviereview.repository;

import moviereview.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface ActorRepository extends JpaRepository<Actor, String> {

//    @Query(value = "SELECT * FROM actor WHERE idactor LIKE ?1 LIMIT ?2, ?3", nativeQuery = true)
//    public List<Actor> findActorByTitle(String title, int start, int count);

    @Query(value = "select name from tmdb_actor where tmdbpeopleid = ?1" ,nativeQuery = true)
    public String findActorById(int actorId);
//    @Query(value = "select * from actor where idactor in" +
//            "(select idactor from is_actor where idmovie = ?1)")
//    public List<Actor> findActorsByIdMovie(String idmovie);
}
