package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by vivian on 2017/5/12.
 */
@Entity
public class User {
    /**
     * 用户Id
     */
    @Id
    private int id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 类型因子
     */
//    private Set<Genre_factor> genreFactors;

    /**
     * 导演因子
     */
//    private Set<Director_factor> directorFactors;

    /**
     * 主演因子
     */
//    private Set<Actor_factor> actorFactors;

//    public User(int userId, String userName, String password, Set<Genre_factor> genre_factors, Set<Director_factor> director_factors, Set<Actor_factor> actor_factors) {
//        this.id = userId;
//        this.username = userName;
//        this.password = password;
//        this.genreFactors = genre_factors;
//        this.directorFactors = director_factors;
//        this.actorFactors = actor_factors;
//    }
    public User() {

    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Set<Genre_factor> getGenreFactors() {
//        return genreFactors;
//    }
//
//    public void setGenreFactors(Set<Genre_factor> genreFactors) {
//        this.genreFactors = genreFactors;
//    }
//
//    public Set<Director_factor> getDirectorFactors() {
//        return directorFactors;
//    }
//
//    public void setDirectorFactors(Set<Director_factor> directorFactors) {
//        this.directorFactors = directorFactors;
//    }
//
//    public Set<Actor_factor> getActorFactors() {
//        return actorFactors;
//    }
//
//    public void setActorFactors(Set<Actor_factor> actorFactors) {
//        this.actorFactors = actorFactors;
//    }
}
