package com.simple.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysRolePermission;
import com.simple.mapper.SysRolePermissionMapper;
import com.simple.service.SysRolePermissionService;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public PageInfo<SysRolePermission> listAsPage(SysRolePermission record, Integer offset, Integer limit) {
        return PageHelper.offsetPage(offset, limit).doSelectPageInfo(() -> sysRolePermissionMapper.select(record));
    }

    @Override
    public SysRolePermission getById(Long id) {
        return sysRolePermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SysRolePermission record) {
        if (record.getId() == null) {
            sysRolePermissionMapper.insert(record);
        } else {
            sysRolePermissionMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void deleteById(Long id) {
        sysRolePermissionMapper.deleteByPrimaryKey(id);
    }

	@Override
	public void savePermissions(Long roleId, String[] perId) {
		Map<String,Object> map = new HashMap<>();
		map.put("roleId", roleId);
		sysRolePermissionMapper.deleteByRoleId(map);
		 for(String pId:perId){
			   SysRolePermission permission =new SysRolePermission();
			   permission.setId(roleId);
			   permission.setPermissionId(Long.parseLong(pId));
			   sysRolePermissionMapper.insert(permission);
		   }
		
	}
    
    
}