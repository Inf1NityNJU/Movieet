package moviereview.service.impl;

import org.junit.Test;

/**
 * Created by SilverNarcissus on 2017/5/31.
 */
public class PredictServiceImplTest {
    @Test
    public void predict() throws Exception {
        PredictServiceImpl predictService = new PredictServiceImpl();
        System.out.println(predictService.predict(10, 10, "Action"));
    }

}