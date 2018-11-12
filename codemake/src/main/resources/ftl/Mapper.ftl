package ${mapperPackageName};

import java.util.*;
import com.simple.common.crud.CommonMapper;
import org.apache.ibatis.annotations.Param;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;
import ${entityPackageName}.${entityName};

public interface ${entityName!}Mapper extends CommonMapper<${entityName}, String> {

	List<${entityName}> findList(${entityName} ${entityInstanceName});
	
	<#if tableName == 'sys_permission' >
	List<SysPermission> findPermissionByRoleId(String roleId);
	List<SysPermission> findPermissionByRoleIds(@Param("roleIds") List<String> roleIds);
	</#if>
	
	<#if tableName == 'sys_role' >
	List<SysRole> findRoleByUserId(String userId);
	int countByName(Map<String,Object> map);
	</#if>	

	<#if tableName == 'sys_role_permission' >
	void deleteByRoleId(Map<String,Object> map);
	</#if>	
	
	<#if tableName == 'user_info' >
	Integer hasButtonPermission(@Param("userId") String userId, @Param("permission") String permission);
	</#if>
	
	${ignoreText}
}
