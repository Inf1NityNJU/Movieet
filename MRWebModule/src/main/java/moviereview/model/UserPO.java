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
    private Set<Genre_factor> genre_factors;

    /**
     * 导演因子
     */
    private Set<Director_factor> director_factors;

    /**
     * 主演因子
     */
    private Set<Actor_factor> actor_factors;

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

    public Set<Genre_factor> getGenre_factors() {
        return genre_factors;
    }

    public void setGenre_factors(Set<Genre_factor> genre_factors) {
        this.genre_factors = genre_factors;
    }

    public Set<Director_factor> getDirector_factors() {
        return director_factors;
    }

    public void setDirector_factors(Set<Director_factor> director_factors) {
        this.director_factors = director_factors;
    }

    public Set<Actor_factor> getActor_factors() {
        return actor_factors;
    }

    public void setActor_factors(Set<Actor_factor> actor_factors) {
        this.actor_factors = actor_factors;
    }

    public UserPO(String userId, String userName, String avatar, Set<Genre_factor> genre_factors, Set<Director_factor> director_factors, Set<Actor_factor> actor_factors) {
        this.userId = userId;
        this.userName = userName;
        this.avatar = avatar;
        this.genre_factors = genre_factors;
        this.director_factors = director_factors;
        this.actor_factors = actor_factors;
    }
}
