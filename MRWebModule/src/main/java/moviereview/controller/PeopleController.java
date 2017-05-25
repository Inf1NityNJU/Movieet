package moviereview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kray on 2017/5/16.
 */

@Controller
@RequestMapping("/api/people")
public class PeopleController {


//    @Autowired
//    private PeopleService peopleService;
//
//
//    /**
//     * @param keyword 关键字
//     * @param size    每页大小
//     * @param page    第几页
//     * @return 导演 分页列表
//     */
//    @ResponseBody
//    @RequestMapping(
//            value = "/search/",
//            params = {"director", "size", "page"},
//            method = RequestMethod.GET,
//            produces = {"application/json; charset=UTF-8"})
//    public List<DirectorBean> findDirectorByKeyword(@RequestParam(value = "director") String keyword,
//                                                    @RequestParam(value = "size") int size,
//                                                    @RequestParam(value = "page") int page) {
//        return peopleService.findDirectorByKeyword(keyword, size, page);
//    }
//
//    /**
//     * @param keyword 关键字
//     * @param size    每页大小
//     * @param page    第几页
//     * @return 导演 分页列表
//     */
//    @ResponseBody
//    @RequestMapping(
//            value = "/search/",
//            params = {"actor", "size", "page"},
//            method = RequestMethod.GET,
//            produces = {"application/json; charset=UTF-8"})
//    public List<ActorBean> findActorByKeyword(@RequestParam(value = "actor") String keyword,
//                                              @RequestParam(value = "size") int size,
//                                              @RequestParam(value = "page") int page) {
//        return peopleService.findActorByKeyword(keyword, size, page);
//    }
}
