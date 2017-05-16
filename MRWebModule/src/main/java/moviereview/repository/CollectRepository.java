package moviereview.repository;

import moviereview.model.CollectInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vivian on 2017/5/15.
 */
public interface CollectRepository extends JpaRepository<CollectInfo, Integer>{
    public CollectInfo findCollectInfoByUserIdAndMovieId(int userId, String movieId);
}
