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
//    private Set<GenreFactor> genreFactors;

    /**
     * 导演因子
     */
//    private Set<DirectorFactor> directorFactors;

    /**
     * 主演因子
     */
//    private Set<ActorFactor> actorFactors;

//    public User(int userId, String userName, String password, Set<GenreFactor> genre_factors, Set<DirectorFactor> director_factors, Set<ActorFactor> actor_factors) {
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

//    public Set<GenreFactor> getGenreFactors() {
//        return genreFactors;
//    }
//
//    public void setGenreFactors(Set<GenreFactor> genreFactors) {
//        this.genreFactors = genreFactors;
//    }
//
//    public Set<DirectorFactor> getDirectorFactors() {
//        return directorFactors;
//    }
//
//    public void setDirectorFactors(Set<DirectorFactor> directorFactors) {
//        this.directorFactors = directorFactors;
//    }
//
//    public Set<ActorFactor> getActorFactors() {
//        return actorFactors;
//    }
//
//    public void setActorFactors(Set<ActorFactor> actorFactors) {
//        this.actorFactors = actorFactors;
//    }
}
