package moviereview.dao.util;


import moviereview.util.ResultMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilverNarcissus on 16/11/12.
 */
public interface DataHelper<T> {


    /**
     * 持久化指定的PO
     *
     * @param o 指定的PO
     * @return 保存成功——ResultMessage.SUCCESS<br>
     * 存在ID相同的PO——ResultMessage.EXIST<br>
     * 底层持久化过程失败——ResultMessage.FAILED
     */
    public ResultMessage save(Object o, Class<T> type);

    /**
     * 持久化指定的PO列表
     *
     * @param poList 指定的PO列表
     * @return 保存成功——ResultMessage.SUCCESS<br>
     * 存在ID相同的PO——ResultMessage.EXIST<br>
     * 底层持久化过程失败——ResultMessage.FAILED
     */
    public ResultMessage save(List<T> poList, Class<T> type);

    /**
     * 更新已被持久化的PO
     *
     * @param o 指定的PO
     * @return 更新成功——ResultMessage.SUCCESS<br>
     * 不存在ID相同的PO——ResultMessage.NOT_EXIST<br>
     * 底层持久化过程失败——ResultMessage.FAILED
     */
    public ResultMessage update(Object o, Class<T> type);

    /**
     * 删除已被持久化的PO
     *
     * @param key 被删除的PO的ID域名
     * @param ID  被删除的PO的ID域值
     * @return 删除成功——ResultMessage.SUCCESS<br>
     * 不存在指定ID的PO——ResultMessage.NOT_EXIST<br>
     * 底层持久化过程失败——ResultMessage.FAILED
     */
    public ResultMessage delete(String key, String ID, Class<T> type);

    /**
     * 按指定字段精确查询PO<br>
     * 建议使用ID进行查询
     *
     * @param field 指定字段名
     * @param value 指定字段值
     * @return 找不到指定的PO——null<br>
     * 找到指定的PO——指定的PO
     */
    public T exactlyQuery(String field, Object value, Class<T> type);

    /**
     * 按指定字段完全匹配查询PO<br>
     *
     * @param field 指定字段名
     * @param value 指定字段值
     * @return 找不到指定的PO——空ArrayList<br>
     * 找到指定的POs——指定的PO列表
     */
    public ArrayList<T> fullMatchQuery(String field, Object value, Class<T> type);

    /**
     * 按指定字段前缀匹配查询PO<br>
     *
     * @param field 指定字段名
     * @param value 指定字段值
     * @return 找不到指定的PO——空ArrayList<br>
     * 找到指定的POs——指定的PO列表
     */
    public ArrayList<T> prefixMatchQuery(String field, String value, Class<T> type);

    /**
     * 按指定字段后缀匹配查询PO<br>
     *
     * @param field 指定字段名
     * @param value 指定字段值
     * @return 找不到指定的PO——空ArrayList<br>
     * 找到指定的POs——指定的PO列表
     */
    public ArrayList<T> suffixMatchQuery(String field, String value, Class<T> type);

    /**
     * 按指定字段模糊匹配查询PO<br>
     *
     * @param field 指定字段名
     * @param value 指定字段值
     * @return 找不到指定的PO——空ArrayList<br>
     * 找到指定的POs——指定的PO列表
     */
    public ArrayList<T> fuzzyMatchQuery(String field, String value, Class<T> type);

    /**
     * 按指定字段范围查询符合范围条件的PO<br>
     * 注意：最大值和最小值包括在范围内
     *
     * @param field 指定字段名
     * @param min   指定字段最小值
     * @param max   指定字段最大值
     * @return 找不到指定的PO——空ArrayList<br>
     * 找到指定的POs——指定的PO列表
     */
    public ArrayList<T> rangeQuery(String field, Object min, Object max, Class<T> type);

    /**
     * 多条件多字段查询符合条件的PO<br>
     *
     * @param criteriaClauses 单字段查询分句组
     * @return 找不到指定的PO——空ArrayList<br>
     * 找到指定的POs——指定的PO列表
     * @see CriteriaClause
     */
    public ArrayList<T> multiCriteriaQuery(ArrayList<CriteriaClause> criteriaClauses, Class<T> type);

    /**
     * 执行 sql 语句进行查询
     *
     * @param sql 合法的 sql 语句
     * @param type 要查询的 po 类型（表名）
     * @return 查询得到的 PO 列表
     */
    public List<T> sqlQuery(String sql, Class<T> type);
}
