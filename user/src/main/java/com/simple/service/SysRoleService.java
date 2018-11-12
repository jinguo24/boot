package com.simple.service;

import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysRole;

import java.util.List;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface SysRoleService {

    /**
     * 根据实体查询分页列表
     *
     * @param record
     * @param offset
     * @param limit
     * @return
     */
    PageInfo<SysRole> listAsPage(SysRole record, Integer offset, Integer limit);

    /**
     * 根据Id获得实体
     *
     * @param id
     * @return
     */
    SysRole getById(Long id);

    /**
     * 保存或更新实体
     *
     * @param record
     */
    void saveOrUpdate(SysRole record);

    /**
     * 根据Id删除实体
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据userId查找Role集合
     * @param userId
     * @return
     */
    List<SysRole> findRoleByUserId(Long userId);

    /**
     * 根据名称查询数量
     * @param name 名称
     * @return
     */
    int countByName(String name,Long id);
    
}