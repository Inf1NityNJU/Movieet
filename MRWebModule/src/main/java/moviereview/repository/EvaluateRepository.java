package moviereview.repository;

import moviereview.model.EvaluateInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vivian on 2017/5/16.
 */
public interface EvaluateRepository extends JpaRepository<EvaluateInfo, Integer> {
    public EvaluateInfo findEvaluateInfoByUserIdAndMovieId(int userId, String movieId);
}
