package com.simple.domain.po;

import com.simple.common.crud.BaseModel;
import javax.persistence.*;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Table(name = "sys_role_permission")
public class SysRolePermission extends BaseModel {
    /**
     * 权限ID
     */
    @Column(name = "permission_id")
    private Long permissionId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 获取权限ID
     *
     * @return permission_id - 权限ID
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * 设置权限ID
     *
     * @param permissionId 权限ID
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}