package moviereview.repository;

import moviereview.model.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Sorumi on 17/5/12.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT MAX(id) from user;", nativeQuery = true)
    public Integer findNextId();

    @Query(value = "SELECT * FROM user WHERE id = ?1", nativeQuery = true)
    public User findUserById(Integer id);

    public User findUserByUsername(String username);

    /**
     * 通过用户名寻找用户密码
     *
     * @param username 用户名
     * @return 找到的用户密码, 如果用户名不存在则返回null
     */
    @Query(value = "SELECT password FROM user WHERE username = ?1", nativeQuery = true)
    public String findPasswordByUsername(String username);


    @Query(value = "SELECT id FROM user WHERE username = ?1", nativeQuery = true)
    public Integer findIdByUsername(String username);

    @Query(value = "SELECT min(factor) from user_actor_factor where user_id = ?1 OR user_id = ?2 " +
            "group by name having count(factor) = 2", nativeQuery = true)
    public ArrayList<Double> getSimilarActorFactor(int user1, int user2);

    @Query(value = "SELECT sum(factor) from user_actor_factor where user_id = ?1",
            nativeQuery = true)
    public double getActorFactor(int user);

    @Query(value = "SELECT id from user", nativeQuery = true)
    public ArrayList<Integer> getAllId();

    @Query(value = "SELECT movieId from collect where userId = ?1 and movieId not in ?2",nativeQuery = true)
    public ArrayList<Integer> getUserCollect(int userId, Set<Integer> exception);

    @Query(value = "SELECT movieId from evaluate where userId = ?1 and movieId not in ?2",nativeQuery = true)
    public ArrayList<Integer> getUserEvaluate(int userId, Set<Integer> exception);
}
