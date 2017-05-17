package moviereview.repository;

import moviereview.model.EvaluateInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class EvaluateRepositoryTest {
    @Autowired
    EvaluateRepository evaluateRepository;

    @Test
    public void save(){
        List<String> directors = new ArrayList<>();
        directors.add("a");
        evaluateRepository.save(new EvaluateInfo(0, "123", LocalDateTime.now().withNano(0).toString(),
                5.0, "action,woman", "d","a"));
    }
}
