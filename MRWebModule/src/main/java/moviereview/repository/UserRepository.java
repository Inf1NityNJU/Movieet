package moviereview.repository;

import moviereview.model.CollectInfo;
import moviereview.model.EvaluateInfo;
import moviereview.model.Movie;
import moviereview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Sorumi on 17/5/12.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT MAX(id) from user;" , nativeQuery = true)
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
}
