package com.yuanpin.nautybee.dao.base;

import java.util.List;
import java.util.Map;


public interface BaseDao<T> {

    /**
     * 查询总数
     *
     * @param param
     * @return
     */
    Integer selectCount(Map<String, ?> param);

    /**
     * 查询
     *
     * @param parameters
     * @return
     */
    List<T> select(Map<String, ?> parameters);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    T selectById(Object id);

    /**
     * 根据IDS查询
     *
     * @param ids
     * @return
     */
    List<T> selectByIds(Long[] ids);

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    int deleteById(Object id);

    /**
     * 根据IDS批量删除
     *
     * @param list
     * @return
     */
    int deleteByIds(Object[] list);

    /**
     * 删除
     *
     * @param parameters
     * @return
     */
    int delete(Map<String, Object> parameters);

    /**
     * 添加
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 通过ID更新
     *
     * @param t
     * @return
     */
    int updateById(T t);

}
