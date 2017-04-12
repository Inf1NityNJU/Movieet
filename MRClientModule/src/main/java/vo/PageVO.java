package vo;

import po.PagePO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/3/24.
 */
public class PageVO<T> {
    /**
     * 当前页数
     */
    public int currentPage;
    /**
     * 总页数
     */
    public int totalPage;
    /**
     * 当前页数据列表
     */
    public List<T> list;

    public PageVO(int currentPage, int totalPage, List<T> list) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.list = list;
    }

}
