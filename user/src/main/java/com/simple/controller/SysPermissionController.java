package com.simple.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.simple.common.rest.Result;
import com.simple.common.rest.ResultData;
import com.simple.domain.po.SysPermission;
import com.simple.service.SysPermissionService;

/**
 * @author chenkx
 * @date 2018-01-05.
 */
@RestController
@RequestMapping("permission")
public class SysPermissionController {

    @Autowired
    SysPermissionService sysPermissionService;

    @GetMapping("alllist")
    public ResultData alllist(String ava) {
        Map param = new HashMap();
        if (StringUtils.isBlank(ava)) {
            param.put("available", ava);
        }
        List<SysPermission> permissions = sysPermissionService.list(param);
        return new ResultData(Result.SUCCESS, "查询成功", permissions);
    }

    @PostMapping("add")
    public ResultData createPermission(SysPermission sysPermission) {
        Assert.notNull(sysPermission.getName(), "用户名不能为空");
        sysPermissionService.saveOrUpdate(sysPermission);
        return new ResultData(sysPermission.getId());
    }

    @PostMapping("update")
    public ResultData updatePermission(SysPermission sysPermission) {
        sysPermissionService.saveOrUpdate(sysPermission);
        return new ResultData();
    }

    @GetMapping("/del")
    public ResultData delete(Long id) {
        sysPermissionService.deleteById(id);
        return new ResultData(Result.SUCCESS, "删除成功", null);
    }


}
