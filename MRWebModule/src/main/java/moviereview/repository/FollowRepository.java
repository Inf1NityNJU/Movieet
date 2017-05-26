package moviereview.repository;

import moviereview.model.FollowInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vivian on 2017/5/26.
 */
public interface FollowRepository extends JpaRepository<FollowInfo, Integer> {
    public FollowInfo findFollowInfoByFolloweridAndFollowingid(int followerid, int followingid);
}
