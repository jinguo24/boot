package com.simple.service;

import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysRolePermission;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface SysRolePermissionService {

    /**
     * 根据实体查询分页列表
     *
     * @param record
     * @param offset
     * @param limit
     * @return
     */
    PageInfo<SysRolePermission> listAsPage(SysRolePermission record, Integer offset, Integer limit);

    /**
     * 根据Id获得实体
     *
     * @param id
     * @return
     */
    SysRolePermission getById(Long id);

    /**
     * 保存或更新实体
     *
     * @param record
     */
    void saveOrUpdate(SysRolePermission record);

    /**
     * 根据Id删除实体
     *
     * @param id
     */
    void deleteById(Long id);
    
    /**
     * 批量保存
     * @param roleId
     * @param perId
     */
    void savePermissions(Long roleId,String[] perId);

}