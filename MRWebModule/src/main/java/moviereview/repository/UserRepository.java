package moviereview.repository;

import moviereview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Sorumi on 17/5/12.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findUserById(Integer id);

    public User findUserByUsername(String username);
}
