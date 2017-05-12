package moviereview.controller;

import moviereview.bean.Result;
import moviereview.model.User;
import moviereview.service.UserService;
import moviereview.util.LoginState;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by vivian on 2017/5/12.
 */
@Controller
@RequestMapping("/api")
@SessionAttributes({"user", "a"})
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
            @RequestBody User user, ModelMap model) {
        System.out.println(user.getUsername());

        ResultMessage resultMessage = userService.signUp(user);
        Result result = new Result(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.result = true;
            user = userService.findByUsername(user.getUsername());
            model.addAttribute("user", user);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/signin",
            method = RequestMethod.POST)
    public Result signIn(
            @RequestBody User user, ModelMap model) {
        System.out.println(user.getUsername());

        ResultMessage resultMessage = userService.signIn(user.getUsername(), user.getPassword());
        Result result = new Result(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.result = true;
            user = userService.findByUsername(user.getUsername());
            model.addAttribute("user", user);
            System.out.println("!!!" +  model.get("user"));
        }
        model.addAttribute("a", user);
        return result;
    }

    @ResponseBody
    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET)
    public User getCurrentUser(ModelMap modelMap) {
        User user = (User) modelMap.get("user");
        if (user != null) {
            System.out.println("user:" + user.getUsername());
            return user;
        } else {
            System.out.println("no user");
        }
        return null;
    }


    //Test
    @ResponseBody
    @RequestMapping(
            value = "/user/{id}",
            method = RequestMethod.GET)
    public Result get1(
            @PathVariable("id") Integer id, ModelMap modelMap) {
        User user = new User(id, "111111", "123");
        modelMap.addAttribute("a", user);
        Result result = new Result(false);

        return result;
    }

    //Test
    @ResponseBody
    @RequestMapping(
            value = "/user/get",
            method = RequestMethod.GET)
    public User get2(ModelMap modelMap) {
        User user = (User) modelMap.get("a");
        return user;
    }
}
