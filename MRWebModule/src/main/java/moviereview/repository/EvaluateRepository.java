package moviereview.repository;

import moviereview.model.EvaluateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/5/16.
 */
public interface EvaluateRepository extends JpaRepository<EvaluateInfo, Integer> {
    public EvaluateInfo findEvaluateInfoByUserIdAndMovieId(int userId, int movieId);

    @Query(value = "SELECT * FROM evaluate WHERE userId = ?1 ORDER BY time ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<EvaluateInfo> findEvaluatesByUserIdOrderByTimeAsc(int userId, int start, int count);

    @Query(value = "SELECT * FROM evaluate WHERE userId = ?1 ORDER BY time DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<EvaluateInfo> findEvaluatesByUserIdOrderByTimeDesc(int userId, int start, int count);
}
