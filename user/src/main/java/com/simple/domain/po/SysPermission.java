package com.simple.domain.po;

import com.simple.common.crud.BaseModel;
import javax.persistence.*;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Table(name = "sys_permission")
public class SysPermission extends BaseModel {
    /**
     * 权限名称
     */
    private String name;

    /**
     * 是否可用
     */
    private String available;

    /**
     * 父编号
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 父编号列表
     */
    @Column(name = "parent_ids")
    private String parentIds;

    /**
     * 权限字符串
     */
    private String permission;

    /**
     * 资源类型menu|button
     */
    @Column(name = "resource_type")
    private String resourceType;

    /**
     * 资源路径
     */
    private String url;
    
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 获取权限名称
     *
     * @return name - 权限名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名称
     *
     * @param name 权限名称
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
     * 获取父编号
     *
     * @return parent_id - 父编号
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父编号
     *
     * @param parentId 父编号
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取父编号列表
     *
     * @return parent_ids - 父编号列表
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置父编号列表
     *
     * @param parentIds 父编号列表
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    /**
     * 获取权限字符串
     *
     * @return permission - 权限字符串
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置权限字符串
     *
     * @param permission 权限字符串
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * 获取资源类型menu|button
     *
     * @return resource_type - 资源类型menu|button
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * 设置资源类型menu|button
     *
     * @param resourceType 资源类型menu|button
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 获取资源路径
     *
     * @return url - 资源路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置资源路径
     *
     * @param url 资源路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}