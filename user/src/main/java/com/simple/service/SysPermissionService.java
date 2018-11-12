package com.simple.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysPermission;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface SysPermissionService {

    /**
     * 根据实体查询分页列表
     *
     * @param record
     * @param offset
     * @param limit
     * @return
     */
    PageInfo<SysPermission> listAsPage(SysPermission record, Integer offset, Integer limit);
    
    /**
     * 根据Id获得实体
     *
     * @param id
     * @return
     */
    SysPermission getById(Long id);

    /**
     * 保存或更新实体
     *
     * @param record
     */
    void saveOrUpdate(SysPermission record);

    /**
     * 根据Id删除实体
     *
     * @param id
     */
    void deleteById(Long id);
    
    /**
     * 查询所有菜单
     * @return
     */
    List<SysPermission> list(Map param);

    /**
     * 根据roleId查询菜单和按钮权限
     * @param roleId
     * @return
     */
    List<SysPermission> findPermissionByRoleId(Long roleId);

    /**
     * 根据ruleId权限组查找相应权限
     * @param roleIds
     * @return
     */
    List<SysPermission> findPermissionByRoleIds(List<Long> roleIds);
}