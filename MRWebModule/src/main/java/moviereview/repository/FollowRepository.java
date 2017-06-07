package moviereview.repository;

import moviereview.model.FollowInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/5/26.
 */
public interface FollowRepository extends JpaRepository<FollowInfo, Integer> {
    public FollowInfo findFollowInfoByFolloweridAndFollowingid(int followerid, int followingid);

    public List<FollowInfo> findFollowInfoByFollowerid(int followerid);

    public List<FollowInfo> findFollowInfoByFollowingid(int followingid);

    @Query(value = "SELECT * FROM follow WHERE followerid = ?1 ORDER BY time ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<FollowInfo> findFollowInfoByFolloweridByTimeAsc(int followerid, int start, int count);

    @Query(value = "SELECT * FROM follow WHERE followerid = ?1 ORDER BY time DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<FollowInfo> findFollowInfoByFolloweridByTimeDesc(int followerid, int start, int count);

    @Query(value = "SELECT * FROM follow WHERE followingid = ?1 ORDER BY time ASC LIMIT ?2, ?3", nativeQuery = true)
    public List<FollowInfo> findFollowInfoByFollowingidByTimeAsc(int followingid, int start, int count);

    @Query(value = "SELECT * FROM follow WHERE followingid = ?1 ORDER BY time DESC LIMIT ?2, ?3", nativeQuery = true)
    public List<FollowInfo> findFollowInfoByFollowingidByTimeDesc(int followingid, int start, int count);

    //找粉丝
    @Query(value = "select count(*) from follow where followingId = ?1",nativeQuery = true)
    public int findFollowerAmountByFollowingId(int followingId);

    //找关注的人
    @Query(value = "select count(*) from follow where followerId = ?1",nativeQuery = true)
    public int findFollowingAmountByFollowerId(int followerId);
}
