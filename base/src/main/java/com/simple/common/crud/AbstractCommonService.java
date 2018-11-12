package com.simple.common.crud;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenkx
 * @date 2017-12-27
 */
public abstract class AbstractCommonService<T, ID extends Serializable> implements CommonService<T, ID> {

    protected abstract CommonMapper<T, ID> getMapper();


    @Override
    public T getById(ID id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public List<T> getByIds(List<ID> ids) {
        //return baseMapper.selectByIds(String.join(",", ids.stream().map(m -> m.toString()).collect(Collectors.toList())));
        return null;
    }

    @Override
    public T findOne(T t) {
        return getMapper().selectOne(t);
    }

    @Override
    public List<T> findList(T t) {
        return getMapper().select(t);
    }

    @Override
    public PageInfo<T> listAsPage(T t, Integer offset, Integer limit) {
        return PageHelper.offsetPage(offset, limit).doSelectPageInfo(() -> getMapper().select(t));
    }

    @Override
    public void save(T t) {
        getMapper().insert(t);
    }

    @Override
    public void update(T t) {
        getMapper().updateByPrimaryKey(t);
    }

    @Override
    public void batchInsert(List<T> lists) {
        getMapper().insertList(lists);
    }

    @Override
    public void deleteById(ID id) {
        getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(List<ID> ids) {
        //getMapper().deleteByIds(String.join(",", ids.stream().map(m -> \m.toString()).collect(Collectors.toList())));
    }
}
