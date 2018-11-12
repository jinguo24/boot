package com.simple.mapper;

import java.util.List;
import java.util.Map;

import com.simple.common.crud.CommonMapper;
import com.simple.domain.po.SysRole;

import java.util.List;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface SysRoleMapper extends CommonMapper<SysRole, Long> {

    /**
     * 通过UserId查找角色
     * @param userId
     * @return
     */
    List<SysRole> findRoleByUserId(Long userId);

	List<SysRole> findListByParams(Map<String,Object> map);
	
	int countByName(Map<String,Object> map);

}