package moviereview.controller;

import moviereview.bean.*;
import moviereview.model.Page;
import moviereview.model.User;
import moviereview.service.UserService;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET)
    public UserMini getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return userService.findUserByUsername(auth.getName());
        }
        return null;
    }

    /**
     * 收藏电影
     *
     * @param movieId 收藏的电影ID
     * @return 收藏结果
     */
    @ResponseBody
    @RequestMapping(
            value = "/user/movie/{movieid}/collect",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    public Result collect(
            @PathVariable("movieid") String movieId
    ) {
//        movieId = "\"#" + movieId.substring(1);
        System.out.println(movieId);
        ResultMessage resultMessage = userService.collect(movieId);
        if (resultMessage == ResultMessage.SUCCESS) {
            return new Result(true);
        }
        return new Result(false, "Post Failed");
    }

    /**
     * 取消想看/收藏
     *
     * @param movieId 取消收藏的电影ID
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping(
            value = "/user/movie/{movieid}/collect",
            method = RequestMethod.DELETE,
            produces = {"application/json; charset=UTF-8"}
    )
    public Result cancelCollect(@PathVariable("movieid") String movieId) {
        ResultMessage resultMessage = userService.cancelCollect(movieId);
        if (resultMessage == ResultMessage.SUCCESS) {
            return new Result(true);
        }
        return new Result(false, "Cancel Collect Failed");
    }

    /**
     * 看过/评价
     *
     * @param movieId      电影ID
     * @param evaluateBean 评价的电影信息
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping(
            value = "/user/movie/{movieid}/evaluate",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    public Result evaluate(@PathVariable("movieid") String movieId,
                           @RequestBody EvaluateBean evaluateBean) {
        System.out.println(movieId);
        ResultMessage resultMessage = userService.evaluate(movieId, evaluateBean);
        if (resultMessage == ResultMessage.SUCCESS) {
            return new Result(true);
        }
        return new Result(false, "Evaluate Failed");
    }

    /**
     * 取消看过/评价
     *
     * @param movieId 取消评价过的电影ID
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping(
            value = "/user/movie/{movieid}/evaluate",
            method = RequestMethod.DELETE,
            produces = {"application/json; charset=UTF-8"}
    )
    public Result cancelEvaluate(@PathVariable("movieid") String movieId) {
        ResultMessage resultMessage = userService.cancelEvaluate(movieId);
        if (resultMessage == ResultMessage.SUCCESS) {
            return new Result(true);
        }
        return new Result(false, "Cancel Evaluate Failed");
    }

    /**
     * 获得用户的收藏
     *
     * @param orderBy 排序字段名称
     * @param order   排序方向
     * @param size    每页显示的条目数
     * @param page    当前页码
     * @return CollectMovie 分页列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/user/{userid}/collect",
            params = {"orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<MovieFull> getUserCollect(@PathVariable("userid") String userId,
                                          @RequestParam(value = "orderBy") String orderBy,
                                          @RequestParam(value = "order") String order,
                                          @RequestParam(value = "size") int size,
                                          @RequestParam(value = "page") int page) {
        return userService.getUserCollect(userId, orderBy, order, size, page);
    }
}
