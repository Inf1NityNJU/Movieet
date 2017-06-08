package moviereview.repository;

import moviereview.model.DirectorRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/6/8.
 */
public interface DirectorRankRepository extends JpaRepository<DirectorRank, Integer> {
    @Query(value = "select * from director_rank", nativeQuery = true)
    public List<DirectorRank> findDirectorForRank();
}
