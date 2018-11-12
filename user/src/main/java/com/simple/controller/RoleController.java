package com.simple.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.simple.common.rest.Result;
import com.simple.common.rest.ResultData;
import com.simple.domain.po.SysRole;
import com.simple.service.SysRolePermissionService;
import com.simple.service.SysRoleService;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRolePermissionService SysRolePermissionService;

    private Logger logger = Logger.getLogger(RoleController.class);

    @GetMapping("list")
    public ResultData list(SysRole sysRole,Integer pageNum, Integer pageSize) {
        final PageInfo<SysRole> page = sysRoleService.listAsPage(sysRole, pageNum, pageSize);
        return new ResultData(page);
    }

    @PostMapping("add")
    public ResultData add(SysRole sysRole) {
        Assert.notNull(sysRole.getName(), "角色名不能为空");
        Assert.isTrue(!checkUnique(sysRole.getName(), null), "重复的角色名");
        sysRoleService.saveOrUpdate(sysRole);
        return new ResultData();
    }

    @PostMapping("update")
    public ResultData update(SysRole sysRole) {
        if (sysRole.getName() != null) {
        	System.out.println(checkUnique(sysRole.getName(), sysRole.getId()));
            Assert.isTrue(!checkUnique(sysRole.getName(), sysRole.getId()), "角色名已存在");
        }
        sysRoleService.saveOrUpdate(sysRole);
//		Assert.notNull(sysRole.getName(), "角色名不能为空");
        return new ResultData();
    }

    @GetMapping("/del")
    public ResultData delete(Long id) {
        sysRoleService.deleteById(id);
        return new ResultData(Result.SUCCESS, "删除成功", null);
    }

    @GetMapping("setPermission")
    public ResultData setPermission(Long roleId, String perId) {
    	Assert.notNull(perId,"请选择权限");
    	String[] perIds = perId.split(",");
        SysRolePermissionService.savePermissions(roleId, perIds);
        return new ResultData(Result.SUCCESS, "设置角色权限成功", null);

    }

    private boolean checkUnique(String name, Long id) {
        return sysRoleService.countByName(name, id) > 0 ? true : false;
    }

}
