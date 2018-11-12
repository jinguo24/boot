package com.simple.domain.po;

import com.simple.common.crud.BaseModel;
import javax.persistence.*;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Table(name = "sys_role")
public class SysRole extends BaseModel {
    /**
     * 角色名称
     */
    private String name;

    /**
     * 是否可用
     */
    private String available;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取是否可用
     *
     * @return available - 是否可用
     */
    public String getAvailable() {
        return available;
    }

    /**
     * 设置是否可用
     *
     * @param available 是否可用
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * 获取角色描述
     *
     * @return description - 角色描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置角色描述
     *
     * @param description 角色描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}