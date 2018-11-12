package com.simple.controller;

import com.simple.common.rest.Result;
import com.simple.common.rest.ResultData;
import com.simple.domain.po.SysPermission;
import com.simple.domain.po.SysRole;
import com.simple.domain.po.UserInfo;
import com.simple.error.ErrorCode;
import com.simple.error.SystemException;
import com.simple.service.SysPermissionService;
import com.simple.service.SysRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class HomeController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("login")
    public ResultData login() {
        return new ResultData(Result.UNLOGIN, "用户未登录");
    }

    @GetMapping("/doLogin")
    public ResultData login(UserInfo user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            throw new SystemException(ErrorCode.LOGIN_USER_OR_PWD_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        subject.login(token);
        List<SysRole> roleList = sysRoleService.findRoleByUserId(user.getId());
        if (null != roleList && roleList.size() > 0) {
	        List<SysPermission> permissionList = sysPermissionService
	                .findPermissionByRoleIds(roleList.stream().map(SysRole::getId).collect(Collectors.toList()));
	//			user.setRole(roleList);
	        user.setPermission(permissionList);
        }
        return new ResultData();

    }

}