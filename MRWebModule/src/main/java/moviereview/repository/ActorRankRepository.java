package moviereview.repository;

import moviereview.model.ActorRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/6/8.
 */
public interface ActorRankRepository extends JpaRepository<ActorRank, Integer>{
    @Query(value = "select * from actor_rank", nativeQuery = true)
    public List<ActorRank> findAtorForRank();
}
