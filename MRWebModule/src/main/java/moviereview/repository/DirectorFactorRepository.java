package moviereview.repository;

import moviereview.model.DirectorFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SilverNarcissus on 2017/5/14.
 */
public interface DirectorFactorRepository extends JpaRepository<DirectorFactor, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE user_director_factor set factor = ?2 where id = ?1", nativeQuery = true)
    public void updateDirector(int id, double factor);
}
