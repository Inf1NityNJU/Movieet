package moviereview.repository;

import moviereview.model.ActorFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SilverNarcissus on 2017/5/14.
 */
public interface ActorFactorRepository extends JpaRepository<ActorFactor, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE user_actor_factor set factor = ?2 where id = ?1", nativeQuery = true)
    public void updateActor(int id, double factor);
}
