package moviereview.service;

import java.util.List;

/**
 * Created by Kray on 2017/3/22.
 */
public interface PageService<T> {

    /**
     * 得到一共要分多少页
     *
     * @param list  总的列表
     * @return  分页数
     */
    public int getTotalPageNum(List<T> list);

    /**
     * 返回第 pageNum 页的数据
     *
     * @param list  总的列表
     * @param pageNum  第几页
     * @return
     */
    public List<T> getSubListOfPage(List<T> list, int pageNum);

}
