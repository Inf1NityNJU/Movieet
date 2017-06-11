package moviereview.service.impl;

import moviereview.bean.EvaluateBean;
import moviereview.bean.MovieMini;
import moviereview.bean.UserMini;
import moviereview.model.Page;
import moviereview.model.User;
import moviereview.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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
//        CollectInfo collectInfo = new CollectInfo(2, "234", LocalDateTime.now().withNano(0).toString());
//        userService.collect("234");
    }

    @Test
    public void cancelCollect(){
//        userService.cancelCollect("34");
    }

    @Test
    public void dayRecommend(){
        List<MovieMini> movies = userService.everyDayRecommend(4);
        for (MovieMini movieFull : movies) {
            System.out.println(movieFull.getId());
        }

    }

    @Test
    public void getfollower(){
        Page<UserMini> userMiniPage = userService.getFollowerList(1, "time", "order", 4, 1);
        for (UserMini userMini : userMiniPage.getResult()) {
            System.out.println(userMini.getId());
        }
    }

    @Test
    public void jsonFormat(){
        List<String> test = new ArrayList<>(2);
        test.add("123");
        test.add("456");
//        EvaluateBean evaluateBean = new EvaluateBean(0,"","","","");

//        evaluateBean = GsonUtil.parseJson("{\n" +
//                "\t\"score\":2,\n" +
//                "\t\"tags\":[\"action\",\"woman\"],\n" +
//                "\t\"genre\":true,\n" +
//                "\t\"director\":true,\n" +
//                "\t\"actor\":true\n" +
//                "}",EvaluateBean.class);
//
//        System.out.println(evaluateBean);
    }

    @Test
    public void evaluate() {
        List<Integer> keyword = new ArrayList<>();
        List<Integer> genre = new ArrayList<>();
        List<Integer> director = new ArrayList<>();
        List<Integer> actor = new ArrayList<>();
//        actor.add(1);
//        actor.add(2);
        director.add(608);
//        director.add(2);
        genre.add(16);
//        genre.add(13);
        userService.evaluate(128, new EvaluateBean(5,keyword, genre, director, actor));
//        actor.add(3);
//        userService.evaluate(12, new EvaluateBean(5,keyword, genre, director, actor));
    }
}
