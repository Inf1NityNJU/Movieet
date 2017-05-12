package moviereview.model;

import java.util.Set;

/**
 * Created by vivian on 2017/5/7.
 */
public class UserPO {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 类型因子
     */
    private Set<GenreFactor> genre_factors;

    /**
     * 导演因子
     */
    private Set<DirectorFactor> director_factors;

    /**
     * 主演因子
     */
    private Set<ActorFactor> actor_factors;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<GenreFactor> getGenre_factors() {
        return genre_factors;
    }

    public void setGenre_factors(Set<GenreFactor> genre_factors) {
        this.genre_factors = genre_factors;
    }

    public Set<DirectorFactor> getDirector_factors() {
        return director_factors;
    }

    public void setDirector_factors(Set<DirectorFactor> director_factors) {
        this.director_factors = director_factors;
    }

    public Set<ActorFactor> getActor_factors() {
        return actor_factors;
    }

    public void setActor_factors(Set<ActorFactor> actor_factors) {
        this.actor_factors = actor_factors;
    }

    public UserPO(String userId, String userName, String avatar, Set<GenreFactor> genre_factors, Set<DirectorFactor> director_factors, Set<ActorFactor> actor_factors) {
        this.userId = userId;
        this.userName = userName;
        this.avatar = avatar;
        this.genre_factors = genre_factors;
        this.director_factors = director_factors;
        this.actor_factors = actor_factors;
    }
}
