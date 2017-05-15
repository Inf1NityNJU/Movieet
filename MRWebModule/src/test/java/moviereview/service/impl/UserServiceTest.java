package moviereview.service.impl;

import moviereview.model.Collect;
import moviereview.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

/**
 * Created by vivian on 2017/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void post(){
        Collect collect = new Collect(1, "234", LocalDateTime.now().withNano(0).toString());
        userService.post(collect);
    }
}
