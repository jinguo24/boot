package com.simple.mapper;

import java.util.List;
import java.util.Map;

import com.simple.common.crud.CommonMapper;
import com.simple.domain.po.SysPermission;
import org.apache.ibatis.annotations.Param;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface SysPermissionMapper extends CommonMapper<SysPermission, Long> {
	
    List<SysPermission> findPermissionByRoleId(Long roleId);

	List<SysPermission> list(Map<String, Object> param);

    List<SysPermission> findPermissionByRoleIds(@Param("roleIds") List<Long> roleIds);
}