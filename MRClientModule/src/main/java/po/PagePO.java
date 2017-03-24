package po;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/24.
 */
public class PagePO<T> {
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数据列表
     */
    private List<T> list;

    public PagePO(int currentPage, int totalPage, List<T> list) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
