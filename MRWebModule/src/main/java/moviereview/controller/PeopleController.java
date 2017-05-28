package moviereview.controller;

import moviereview.bean.PeopleMini;
import moviereview.model.Page;
import moviereview.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Kray on 2017/5/16.
 */

@Controller
@RequestMapping("/api")
public class PeopleController {


    @Autowired
    private PeopleService peopleService;


    /**
     * 通过关键词搜索导演
     *
     * @param keyword 关键词
     * @param orderBy 按什么排序
     * @param order   asc 还是 desc
     * @param size    每页大小
     * @param page    第几页
     * @return PeopleMini分页列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/director/search",
            params = {"keyword", "orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Page<PeopleMini> findDirectorByKeyword(@RequestParam(value = "keyword") String keyword,
                                                  @RequestParam(value = "orderBy") String orderBy,
                                                  @RequestParam(value = "order") String order,
                                                  @RequestParam(value = "size") int size,
                                                  @RequestParam(value = "page") int page) {
        return peopleService.findDirectorByKeyword(keyword, orderBy, order, size, page);
    }

    /**
     * 通过关键词搜索演员
     *
     * @param keyword 关键词
     * @param orderBy 按什么排序
     * @param order   asc 还是 desc
     * @param size    每页大小
     * @param page    第几页
     * @return PeopleMini分页列表
     */
    @ResponseBody
    @RequestMapping(
            value = "/actor/search",
            params = {"keyword", "orderBy", "order", "size", "page"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"})
    public Page<PeopleMini> findActorByKeyword(@RequestParam(value = "keyword") String keyword,
                                               @RequestParam(value = "orderBy") String orderBy,
                                               @RequestParam(value = "order") String order,
                                               @RequestParam(value = "size") int size,
                                               @RequestParam(value = "page") int page) {
        return peopleService.findActorByKeyword(keyword, orderBy, order, size, page);
    }
}
