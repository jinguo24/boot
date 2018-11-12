package com.simple.domain.po;

import com.simple.common.crud.BaseModel;
import javax.persistence.*;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Table(name = "sys_user_role")
public class SysUserRole extends BaseModel {
    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 获取用户ID
     *
     * @return uid - 用户ID
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置用户ID
     *
     * @param uid 用户ID
     */
    public void setUid(Long uid) {
        this.uid = uid;
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