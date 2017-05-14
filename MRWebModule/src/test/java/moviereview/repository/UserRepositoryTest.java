package moviereview.repository;

import moviereview.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Sorumi on 17/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void proxy() throws Exception {
        System.out.println(userRepository.getClass());
    }

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("1");
        user.setPassword("123");
        userRepository.save(user);
    }


    @Test
    public void findById() throws Exception {
        assertNotNull(userRepository.findUserById(1));
    }


}