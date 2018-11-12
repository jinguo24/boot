package com.simple.mapper;

import com.simple.common.crud.CommonMapper;
import com.simple.domain.po.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface UserInfoMapper extends CommonMapper<UserInfo, Long> {

    List<UserInfo> listAsPage(UserInfo user);

    Integer hasButtonPermission(@Param("userId") Long userId, @Param("permission") String permission);
}