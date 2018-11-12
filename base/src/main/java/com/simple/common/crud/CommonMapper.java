package com.simple.common.crud;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.io.Serializable;

/**
 * @author chenkx
 * @date   2017-12-27
 */

public interface CommonMapper<T, ID extends Serializable> extends BaseMapper<T>, IdsMapper<T>, InsertListMapper<T>, Marker {
}
