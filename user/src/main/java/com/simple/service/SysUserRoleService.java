package com.simple.service;

import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysUserRole;

import java.util.List;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface SysUserRoleService {

    /**
     * 根据实体查询分页列表
     *
     * @param record
     * @param offset
     * @param limit
     * @return
     */
    PageInfo<SysUserRole> listAsPage(SysUserRole record, Integer offset, Integer limit);

    /**
     * 根据Id获得实体
     *
     * @param id
     * @return
     */
    SysUserRole getById(Long id);

    /**
     * 保存或更新实体
     *
     * @param record
     */
    void saveOrUpdate(SysUserRole record);

    /**
     * 根据Id删除实体
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 批量增加用户对应角色
     * @param u2rs
     */
    void batchInsert(List<SysUserRole> u2rs);
}