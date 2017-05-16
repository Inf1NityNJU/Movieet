package moviereview.repository;

import moviereview.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface ActorRepository extends JpaRepository<Actor, String> {

    @Query(value = "SELECT idactor FROM actor WHERE idactor LIKE ?1 LIMIT ?2, ?3", nativeQuery = true)
    public List<Actor> findActorByTitle(String title, int start, int count);
}
