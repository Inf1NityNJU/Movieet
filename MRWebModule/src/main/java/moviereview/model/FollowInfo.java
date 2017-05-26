package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by vivian on 2017/5/26.
 */
@Entity
@Table(name = "follow")
public class FollowInfo {
    @Id
    private int followid;

    /**
     * 追随者id
     */
    private int followerid;

    /**
     * 被关注者id
     */
    private int followingid;

    /**
     * 关注时间
     */
    private String time;

    public FollowInfo() {
    }

    public FollowInfo(int followerid, int followingid) {
        this.followerid = followerid;
        this.followingid = followingid;
        this.time = LocalDateTime.now().withNano(0).toString();
    }

    public int getFollowid() {
        return followid;
    }

    public void setFollowid(int followid) {
        this.followid = followid;
    }

    public int getFollowerid() {
        return followerid;
    }

    public void setFollowerid(int followerid) {
        this.followerid = followerid;
    }

    public int getFollowingid() {
        return followingid;
    }

    public void setFollowingid(int followingid) {
        this.followingid = followingid;
    }
}
