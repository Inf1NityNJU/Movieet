package moviereview.repository;

import moviereview.model.CollectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/5/15.
 */
public interface CollectRepository extends JpaRepository<CollectInfo, Integer> {
    public CollectInfo findCollectInfoByUserIdAndMovieId(int userId, int movieId);

    @Query(value = "SELECT * FROM collect WHERE userId = ?1 ORDER BY time ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<CollectInfo> findCollectsInfoByUserIdOrderByTimeAsc(int userId, int start, int count);

    @Query(value = "SELECT * FROM collect WHERE userId = ?1 ORDER BY time DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<CollectInfo> findCollecstInfoByUserIdOrderByTimeDesc(int userId, int start, int count);

    @Query(value = "select count(*) from collect where userId = ?1",nativeQuery = true)
    public int findCollectAmountByUserId(int userId);
}
