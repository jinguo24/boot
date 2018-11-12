package com.simple.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysUserRole;
import com.simple.mapper.SysUserRoleMapper;
import com.simple.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public PageInfo<SysUserRole> listAsPage(SysUserRole record, Integer offset, Integer limit) {
        return PageHelper.offsetPage(offset, limit).doSelectPageInfo(() -> sysUserRoleMapper.select(record));
    }

    @Override
    public SysUserRole getById(Long id) {
        return sysUserRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SysUserRole record) {
        if (record.getId() == null) {
            sysUserRoleMapper.insert(record);
        } else {
            sysUserRoleMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void deleteById(Long id) {
        sysUserRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void batchInsert(List<SysUserRole> u2rs) {
        sysUserRoleMapper.insertList(u2rs);
    }
}