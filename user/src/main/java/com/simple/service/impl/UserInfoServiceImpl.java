package com.simple.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.domain.po.SysUserRole;
import com.simple.domain.po.UserInfo;
import com.simple.mapper.SysUserRoleMapper;
import com.simple.mapper.UserInfoMapper;
import com.simple.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Override
    public PageInfo<UserInfo> listAsPage(UserInfo record, Integer pageNum, Integer pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> userInfoMapper.listAsPage(record));
    }

    @Override
    public UserInfo getById(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserInfo getByName(String username) {
        UserInfo userInfo = new UserInfo(username);
        return userInfoMapper.selectOne(userInfo);
    }

    @Override
    public void saveOrUpdate(UserInfo record) {
        if (record.getId() == null) {
            record.setCreateTime(new Date());
            record.setLastLoginTime(new Date());
            record.setStatus(1);
            userInfoMapper.insert(record);
        } else {
            userInfoMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void deleteById(Long id) {
        userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Long cntByUserName(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        return PageHelper.count(() -> userInfoMapper.select(userInfo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        userInfoMapper.deleteByPrimaryKey(id);
        SysUserRole userRole = new SysUserRole();
        userRole.setUid(id);
        userRoleMapper.delete(userRole);
    }

    @Override
    public boolean hasButtonPermission(Long userId, String permission) {
        return userInfoMapper.hasButtonPermission(userId, permission) == null ? false : true;
    }
}