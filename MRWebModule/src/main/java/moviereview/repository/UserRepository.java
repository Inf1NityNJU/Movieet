package moviereview.repository;

import moviereview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Sorumi on 17/5/12.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT MAX(id) from user;" , nativeQuery = true)
    public Integer findNextId();

    @Query(value = "SELECT * FROM user WHERE id = ?1", nativeQuery = true)
    public User findUserById(Integer id);

    public User findUserByUsername(String username);
}
