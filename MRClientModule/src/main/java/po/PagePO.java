package po;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/24.
 */
public class PagePO<T> {
    /**
     * 当前页数
     */
    private int pageNo;
    /**
     * 每页显示的条目数
     */
    private int pageSize;
    /**
     * 排序字段名称
     */
    private String orderBy;
    /**
     * 排序方向
     */
    private String order;
    /**
     * 总条目数
     */
    private String totalCount;
    /**
     * 当前页数据列表
     */
    private List<T> result;

    public PagePO() {

    }

    public PagePO(int pageNo, int pageSize, String orderBy, String order, String totalCount, List<T> result) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.order = order;
        this.totalCount = totalCount;
        this.result = result;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
