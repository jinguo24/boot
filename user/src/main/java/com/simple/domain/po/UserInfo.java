package com.simple.domain.po;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.simple.common.crud.BaseModel;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Table(name = "user_info")
public class UserInfo extends BaseModel {

    public static final String SORT = "userInfo";

    public UserInfo() {
    }

    public UserInfo(String username) {
        this.username = username;
    }

    /**
     * 用户名
     */
    private String username;

    /**
     * url描述
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    //@JSONField(serialzeFeatures = SerializerFeature.WriteDateUseDateFormat)
    //private LocalDateTime createTime;
    private Date createTime;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    //private LocalDateTime lastLoginTime;
    private Date lastLoginTime;

    /**
     * 1:有效，0:禁止登录
     */
    private Integer status;

    /**
     * 该用户所有的角色
     */
    @Transient
    private List<SysRole> role;

    /**
     * 该用户包含的所有权限
     */
    @Transient
    private List<SysPermission> permission;

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取url描述
     *
     * @return name - url描述
     */
    public String getName() {
        return name;
    }

    /**
     * 设置url描述
     *
     * @param name url描述
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后登录时间
     *
     * @return last_login_time - 最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	/**
     * 获取1:有效，0:禁止登录
     *
     * @return status - 1:有效，0:禁止登录
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1:有效，0:禁止登录
     *
     * @param status 1:有效，0:禁止登录
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SysRole> getRole() {
        return role;
    }

    public void setRole(List<SysRole> role) {
        this.role = role;
    }

    public List<SysPermission> getPermission() {
        return permission;
    }

    public void setPermission(List<SysPermission> permission) {
        this.permission = permission;
    }

}