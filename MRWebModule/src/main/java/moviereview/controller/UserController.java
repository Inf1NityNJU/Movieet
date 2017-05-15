package moviereview.controller;

import moviereview.bean.Result;
import moviereview.model.Collect;
import moviereview.model.User;
import moviereview.service.UserService;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Created by vivian on 2017/5/12.
 */
@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param user 用户名和密码
     * @return 注册结果，是否成功
     */
    @ResponseBody
    @RequestMapping(
            value = "/user/signup",
            method = RequestMethod.POST)
    public Result signUp(
            @RequestBody User user) {
        ResultMessage resultMessage = userService.signUp(user);
        Result result = new Result(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.result = true;
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/signin",
            method = RequestMethod.POST)
    public Result signIn(
            @RequestBody User user) {

        ResultMessage resultMessage = userService.signIn(user.getUsername(), user.getPassword());
        Result result = new Result(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.result = true;
        }
        return result;
    }

//    @ResponseBody
//    @RequestMapping(
//            value = "/user",
//            method = RequestMethod.GET)
//    public User getCurrentUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            return userService.findUserByUsername(auth.getName());
//        }
//        return null;
//    }

    @ResponseBody
    @RequestMapping(
            value = "/user/movie/{movieid}/collect",
            method = RequestMethod.POST
    )
    public Result post(
            @PathVariable("movieid") String movieId
    ){
        User user = userService.getCurrentUser();
        int userId = user.getId();
        LocalDateTime time = LocalDateTime.now().withNano(0);
        Collect collect = new Collect(0, userId, movieId, time.toString());
        ResultMessage resultMessage = userService.post(collect);
        if (resultMessage == ResultMessage.SUCCESS){
            return new Result(true);
        }
        return new Result(false, "Post Failed");
    }

}
