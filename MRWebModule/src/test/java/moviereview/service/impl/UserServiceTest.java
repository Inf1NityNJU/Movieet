package moviereview.service.impl;

import moviereview.model.CollectInfo;
import moviereview.model.User;
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
    public void signUp(){
        userService.signUp(new User(1, "vivian", "111"));
    }

    @Test
    public void collect(){
        CollectInfo collectInfo = new CollectInfo(2, "234", LocalDateTime.now().withNano(0).toString());
        userService.collect(collectInfo);
    }

    @Test
    public void cancelCollect(){
        userService.cancelCollect("34");
    }
}
