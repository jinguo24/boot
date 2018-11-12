//package com.simple;
//
//import com.alibaba.fastjson.JSON;
//import com.simple.domain.po.SysPermission;
//import com.simple.service.SysPermissionService;
//import com.simple.service.UserInfoService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author chenkx
// * @date 2018-01-05.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SysPermissionServiceTest {
//
//    @Autowired
//    SysPermissionService permissionService;
//    @Autowired
//    UserInfoService userInfoService;
//
//    @Test
//    public void permissionTest() {
//        List<Long> roleId = new ArrayList<>();
//        roleId.add(1L);
//        final List<SysPermission> permissionByRoles = permissionService.findPermissionByRoleIds(roleId);
//        System.out.println(JSON.toJSONString(permissionByRoles));
//    }
//}
