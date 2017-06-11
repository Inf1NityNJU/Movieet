package moviereview.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by vivian on 2017/6/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class PeopleRepositoryTest {
    @Autowired
    DirectorRepository directorRepository;

    @Test
    public void addRank(){
        directorRepository.addRank(1,2.0);
    }
}
