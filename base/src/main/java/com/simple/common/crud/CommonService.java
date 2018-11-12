package com.simple.common.crud;


import com.github.pagehelper.PageInfo;
import org.springframework.context.annotation.Primary;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenkx
 * @date   2017-12-27
 */
public interface CommonService<T,ID extends Serializable> {

    /**
     * 根据Id查找对象
     * @param id
     * @return
     */
    T getById(ID id);

    /**
     * 根据Id集合查找对象
     * @param ids
     * @return
     */
    List<T> getByIds(List<ID> ids);

    /**
     * 根据条件查找对象
     * @param t
     * @return
     */
    T findOne(T t);

    /**
     * 根据条件查找对象列表
     * @param t
     * @return
     */
    List<T> findList(T t);

    /**
     * 根据条件查找带分页的对象
     * @param t
     * @param offset
     * @param limit
     * @return
     */
    PageInfo<T> listAsPage(T t, Integer offset, Integer limit);

    /**
     * 保存对象
     * @param t
     */
    void save(T t);

    /**
     * 修改对象
     * @param t
     */
    void update(T t);

    /**
     * 批量保存对象
     * @param lists
     */
    void batchInsert(List<T> lists);

    /**
     * 删除对象
     * @param id
     */
    void deleteById(ID id);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteByIds(List<ID> ids);
}
