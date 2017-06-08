package moviereview.controller;

import moviereview.bean.*;
import moviereview.model.Page;
import moviereview.model.User;
import moviereview.service.RecommendService;
import moviereview.service.UserService;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vivian on 2017/5/12.
 */
@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RecommendService recommendService;

    /**
     * 注册
     *
     * @param user 用户名和密码
     * @return 注册结果，是否成功
     */
    @ResponseBody
    @RequestMapping(
            value = "/user/signup",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"})
    public Result signUp(
            @RequestBody User user) {
        ResultMessage resultMessage = userService.signUp(user);
        Result result = new Result(false);
        if (resultMessage == ResultMessage.SUCCESS) {
            result.result = true;
        } else if (resultMessage == ResultMessage.EXIST) {
            result.message = "Username is already existed!";
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
            value = "/user/signout",
            method = RequestMethod.POST
    )
    public Result signout() {
        return new Result(true);
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

    @ResponseBody
    @RequestMapping(
            value = "/user/info/{userId}",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public UserFull findUserById(@PathVariable(value = "userId") int userId) {
        return userService.findUserById(userId);
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
        ResultMessage resultMessage = userService.collect(Integer.parseInt(movieId));
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
        ResultMessage resultMessage = userService.cancelCollect(Integer.parseInt(movieId));
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
    public EvaluateResult evaluate(@PathVariable("movieid") String movieId,
                           @RequestBody EvaluateBean evaluateBean) {
        EvaluateResult evaluate = userService.evaluate(Integer.parseInt(movieId), evaluateBean);
        if (evaluate.isResult() == true) {
            return new EvaluateResult(true, evaluate.getRecommend());
        }
        return new EvaluateResult(false, "Evaluate Failed", evaluate.getRecommend());
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
        ResultMessage resultMessage = userService.cancelEvaluate(Integer.parseInt(movieId));
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
    public Page<MovieMini> getUserCollect(@PathVariable("userid") String userId,
                                          @RequestParam(value = "orderBy") String orderBy,
                                          @RequestParam(value = "order") String order,
                                          @RequestParam(value = "size") int size,
                                          @RequestParam(value = "page") int page) {
        return userService.getUserCollect(userId, orderBy, order, size, page);
    }

    /**
     * 获得用户评价过的电影
     *
     * @param orderBy 排序字段名称
     * @param order   排序方向
     * @param size    每页显示的条目数
     * @param page    当前页码
     * @return CollectMovie 分页列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/user/{userid}/evaluate",
            params = {"orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<MovieMini> getUserEvaluate(@PathVariable("userid") String userId,
                                           @RequestParam(value = "orderBy") String orderBy,
                                           @RequestParam(value = "order") String order,
                                           @RequestParam(value = "size") int size,
                                           @RequestParam(value = "page") int page) {
        return userService.getUserEvaluate(userId, orderBy, order, size, page);
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/{userId}/follow",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    public Result follow(@PathVariable("userId") String userId) {
        ResultMessage resultMessage = userService.follow(Integer.parseInt(userId));
        if (resultMessage == ResultMessage.SUCCESS) {
            return new Result(true);
        }
        return new Result(false, "Post Failed");
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/{userId}/follow",
            method = RequestMethod.DELETE,
            produces = {"application/json; charset=UTF-8"}
    )
    public Result cancelFollow(@PathVariable("userId") String userId) {
        ResultMessage resultMessage = userService.cancelFollow(Integer.parseInt(userId));
        if (resultMessage == ResultMessage.SUCCESS) {
            return new Result(true);
        }
        return new Result(false, "Cancel Follow Failed");
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/{userId}/follow",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public StateBean userState(@PathVariable("userId") int userId) {
        return userService.userState(userId);
    }


    @ResponseBody
    @RequestMapping(
            value = "/user/{userId}/following",
            params = {"orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<UserMini> getFollowingList(@PathVariable("userId") String userId,
                                           @RequestParam(value = "orderBy") String orderBy,
                                           @RequestParam(value = "order") String order,
                                           @RequestParam(value = "size") int size,
                                           @RequestParam(value = "page") int page) {
        return userService.getFollowingList(Integer.parseInt(userId), orderBy, order, size, page);
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/{userId}/follower",
            params = {"orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<UserMini> getFollowerList(@PathVariable("userId") String userId,
                                           @RequestParam(value = "orderBy") String orderBy,
                                           @RequestParam(value = "order") String order,
                                           @RequestParam(value = "size") int size,
                                           @RequestParam(value = "page") int page) {
        return userService.getFollowerList(Integer.parseInt(userId), orderBy, order, size, page);
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/recommend",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public List<MovieMini> everyDayRecommend(@RequestParam(value = "size") int size) {
//        int userId = userService.getCurrentUser().getId();
        return userService.everyDayRecommend(size);
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/movie/{movieid}",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public StateBean movieStateForUser(@PathVariable("movieid") String movieId) {
        return userService.movieStateForUser(Integer.parseInt(movieId));
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/survey",
            method = RequestMethod.POST,
            produces = {"application/json; charset=UTF-8"}
    )
    public Result survey(@RequestBody List<Integer> genres) {
        ResultMessage resultMessage = userService.survey(genres);
        if (resultMessage == ResultMessage.SUCCESS) {
            return new Result(true);
        } else {
            return new Result(false, "survey failed");
        }
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/{userId}/similarity",
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public double getSimilarity(@PathVariable("userId") int userId) {
        return userService.getSimilarity(userId);
    }

    @ResponseBody
    @RequestMapping(
            value = "/user/similar/recommend",
            params = {"size"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Page<MovieMini> similarMovie(@RequestParam(value = "size") int size) {
        return userService.getSimilarMovie(size);
    }
}
