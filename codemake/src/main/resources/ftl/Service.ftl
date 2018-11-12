package ${servicePackageName};

import java.util.*;
import com.github.pagehelper.PageInfo;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;
import ${entityPackageName}.${entityName};

public interface ${entityName!}Service {

	/**
     * 根据实体查询分页列表
     *
     * @param record
     * @param offset
     * @param limit
     * @return
     */
	PageInfo<${entityName}> listAsPage(${entityName!} record, Integer pageIndex, Integer pageSize);
	
	/**
     * 根据Id获得实体
     *
     * @param id
     * @return
     */
    ${entityName} getById(String id);
    
        /**
     * 保存或更新实体
     *
     * @param record
     */
    void saveOrUpdate(${entityName} record);

    /**
     * 根据Id删除实体
     *
     * @param id
     */
    void deleteById(String id);
    
    <#if tableName == 'sys_permission' >
    /**
     * 根据roleId查询菜单和按钮权限
     * @param roleId
     * @return
     */
    List<SysPermission> findPermissionByRoleId(String roleId);

    /**
     * 根据ruleId权限组查找相应权限
     * @param roleIds
     * @return
     */
    List<SysPermission> findPermissionByRoleIds(List<String> roleIds);
	</#if>
	
	<#if tableName == 'sys_role' >
    /**
     * 根据userId查找Role集合
     * @param userId
     * @return
     */
    List<SysRole> findRoleByUserId(String userId);

    /**
     * 根据名称查询数量
     * @param name 名称
     * @return
     */
    int countByName(String name,String id);
	</#if>
	
	<#if tableName == 'sys_role_permission' >
    /**
     * 批量保存
     * @param roleId
     * @param perId
     */
    void savePermissions(String roleId,String[] perId);
	</#if>	

	<#if tableName == 'sys_user_role' >
    /**
     * 批量增加用户对应角色
     * @param u2rs
     */
    void batchInsert(List<SysUserRole> u2rs);
	</#if>	
	
	<#if tableName == 'user_info' >
    /**
     * 查询用户名总数，用于校验用户名是否重复
     * @param username
     */
    long cntByUserName(String username);

    /**
     * 根据用户名查询用户对象
     * @param username
     * @return
     */
    UserInfo getByName(String username);

    /**
     * 删除用户相关数据(用户记录，角色关联)
     * @param id
     */
    void deleteUser(String id);

    /**
     * 检查用户按钮是否有权限
     * @param userId
     * @param permission
     * @return 是否有权限(true: 有| false: 没有)
     */
    boolean hasButtonPermission(String userId, String permission);
	</#if>	
	
	${ignoreText}
	
}
