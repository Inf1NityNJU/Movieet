package moviereview.service.impl;

import moviereview.bean.PredictBean;
import moviereview.service.PredictService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * Created by SilverNarcissus on 2017/5/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class PredictServiceImplTest {
    @Autowired
    PredictService predictService;

    @Test
    public void predict() throws Exception {
        ArrayList<Integer> actors = new ArrayList<>();
        ArrayList<Integer> directors = new ArrayList<>();
        ArrayList<Integer> genres = new ArrayList<>();

        actors.add(287);
        actors.add(72855);
        actors.add(865);

        directors.add(1);
        directors.add(31);
        directors.add(42);

        genres.add(28);
        genres.add(12);
        genres.add(16);


        System.out.println(predictService.predict(new PredictBean(actors, directors, genres)));
    }

}