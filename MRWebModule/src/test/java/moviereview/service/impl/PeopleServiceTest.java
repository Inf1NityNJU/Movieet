package moviereview.service.impl;

import moviereview.service.PeopleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by vivian on 2017/6/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class PeopleServiceTest {
    @Autowired
    private PeopleService peopleService;

    @Test
    public void findAcorRank() {
        peopleService.getActorRank(5);
    }
}
