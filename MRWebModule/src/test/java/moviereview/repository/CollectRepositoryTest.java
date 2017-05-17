package moviereview.repository;

import moviereview.model.CollectInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by vivian on 2017/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class CollectRepositoryTest {
    @Autowired
    CollectRepository collectRepository;

    @Test
    public void collect() {
//        CollectInfo collectInfo = new CollectInfo(1, 1, "34", LocalDateTime.now().withNano(0).toString());
        CollectInfo collectInfo = new CollectInfo(1, "123", LocalDateTime.now().withNano(0).toString());
        collectRepository.save(collectInfo);
    }

    @Test
    public void findCollectInfoByUserIdAndMovieId(){
        CollectInfo collectInfo = collectRepository.findCollectInfoByUserIdAndMovieId(1, "34");
        System.out.println(collectInfo.getCollectId());
        System.out.println(collectInfo.getUserId());
        System.out.println(collectInfo.getMovieId());
    }

    @Test
    public void deleteCollectInfo() {
        collectRepository.delete(2);
        collectRepository.delete(new CollectInfo(3, 1, "234", LocalDateTime.now().withNano(0).toString()));
    }

    @Test
    public void getCollectsAsc() {
        List<CollectInfo> collectInfos = collectRepository.findCollectsInfoByUserIdOrderByTimeAsc(0, 0,10);
        for (CollectInfo collectInfo : collectInfos) {
            System.out.println(collectInfo.getMovieId());
        }
    }
}
