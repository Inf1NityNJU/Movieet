package moviereview.controller;

import moviereview.bean.Result;
import moviereview.model.User;
import moviereview.service.UserService;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vivian on 2017/5/12.
 */
@Controller
@RestController
@RequestMapping("/api")
@SessionAttributes({"user"})
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
    public Result signup(
            @RequestBody User user, ModelMap model) {
        ResultMessage resultMessage = userService.add(user);
        Result result = new Result(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.result = true;
            model.addAttribute("user", user);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET)
    public User getCurrentUser(ModelMap modelMap){
        if (modelMap.containsAttribute("user")) {
            return (User)modelMap.get("user");
        }
        return null;
    }
}
