package com.simple.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysRole;
import com.simple.mapper.SysRoleMapper;
import com.simple.service.SysRoleService;

import java.util.List;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> listAsPage(SysRole record, Integer pageNum, Integer pageSize) {
        Map<String,Object> map =new HashMap<>(1);
        map.put("name", record.getName());
    	return PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> sysRoleMapper.findListByParams(map));
    }

    @Override
    public SysRole getById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SysRole record) {
        if (record.getId() == null) {
            sysRoleMapper.insert(record);
        } else {
            sysRoleMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void deleteById(Long id) {
        sysRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysRole> findRoleByUserId(Long userId) {
        return sysRoleMapper.findRoleByUserId(userId);
    }

	@Override
	public int countByName(String name,Long id) {
		Map<String,Object> map = new HashMap<>(2);
		map.put("name", name);
		map.put("id", id);
		return sysRoleMapper.countByName(map);
	}

}