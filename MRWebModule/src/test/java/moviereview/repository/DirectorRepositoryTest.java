package moviereview.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by vivian on 2017/5/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class DirectorRepositoryTest {
    @Autowired
    DirectorRepository directorRepository;

    @Test
    public void findDirectorByKeyword() {
        List<Integer> ids = directorRepository.findDirectorByKeywordPopularityAsc("%A%", 1,4);
        System.out.println(ids.size());
        for (Integer id : ids) {
            System.out.println(id);
        }
    }
}
