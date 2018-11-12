package com.simple.mapper;

import java.util.Map;

import com.simple.common.crud.CommonMapper;
import com.simple.domain.po.SysRolePermission;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface SysRolePermissionMapper extends CommonMapper<SysRolePermission, Long> {
	
	void deleteByRoleId(Map<String,Object> map);
}