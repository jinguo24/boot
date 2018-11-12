package com.simple.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysPermission;
import com.simple.mapper.SysPermissionMapper;
import com.simple.service.SysPermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Override
    public PageInfo<SysPermission> listAsPage(SysPermission record, Integer offset, Integer limit) {
        return PageHelper.offsetPage(offset, limit).doSelectPageInfo(() -> sysPermissionMapper.select(record));
    }

    @Override
    public SysPermission getById(Long id) {
        return sysPermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SysPermission record) {
        if (record.getId() == null) {
            sysPermissionMapper.insert(record);
        } else {
            sysPermissionMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void deleteById(Long id) {
        sysPermissionMapper.deleteByPrimaryKey(id);
    }

	@Override
	public List<SysPermission> list(Map param) {
		return sysPermissionMapper.list(param);
	}

    @Override
    public List<SysPermission> findPermissionByRoleId(Long roleId) {
        return sysPermissionMapper.findPermissionByRoleId(roleId);
    }

    @Override
    public List<SysPermission> findPermissionByRoleIds(List<Long> roleIds) {
        return sysPermissionMapper.findPermissionByRoleIds(roleIds);
    }
}