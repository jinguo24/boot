package ${serviceImplPackageName};

import java.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;
import ${entityPackageName}.${entityName};
import ${mapperPackageName}.${entityName}Mapper;
import ${servicePackageName}.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<#if tableName == 'user_info' >
import org.springframework.transaction.annotation.Transactional;
import ${entityPackageName}.SysUserRole;
import ${mapperPackageName}.SysUserRoleMapper;
</#if>

@Service
public class ${entityName!}ServiceImpl implements ${entityName!}Service {
	
	@Autowired
    ${entityName!}Mapper ${entityInstanceName}Mapper;
	<#if tableName == 'user_info' >
	@Autowired
	SysUserRoleMapper userRoleMapper;
	</#if>


    @Override
    public PageInfo<${entityName!}> listAsPage(${entityName!} record, Integer pageIndex, Integer pageSize) {
        return PageHelper.startPage(pageIndex, pageSize).doSelectPageInfo(() -> ${entityInstanceName}Mapper.findList(record));
    }

    @Override
    public ${entityName!} getById(String id) {
        return ${entityInstanceName}Mapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(${entityName!} record) {
        if (record.getId() == null) {
            record.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            ${entityInstanceName}Mapper.insertSelective(record);
        } else {
            ${entityInstanceName}Mapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void deleteById(String id) {
        ${entityInstanceName}Mapper.deleteByPrimaryKey(id);
    }
    
    <#if tableName == 'sys_permission' >
    @Override
    public List<SysPermission> findPermissionByRoleId(String roleId) {
        return sysPermissionMapper.findPermissionByRoleId(roleId);
    }

    @Override
    public List<SysPermission> findPermissionByRoleIds(List<String> roleIds) {
        return sysPermissionMapper.findPermissionByRoleIds(roleIds);
    }
    </#if>
    
    <#if tableName == 'sys_role' >
    @Override
    public List<SysRole> findRoleByUserId(String userId) {
        return sysRoleMapper.findRoleByUserId(userId);
    }

	@Override
	public int countByName(String name,String id) {
		Map<String,Object> map = new HashMap<>(2);
		map.put("name", name);
		map.put("id", id);
		return sysRoleMapper.countByName(map);
	}
    </#if>
    
    <#if tableName == 'sys_role_permission' >
	@Override
	public void savePermissions(String roleId, String[] perId) {
		Map<String,Object> map = new HashMap<>();
		map.put("roleId", roleId);
		sysRolePermissionMapper.deleteByRoleId(map);
		 for(String pId:perId){
			   SysRolePermission permission =new SysRolePermission();
			   permission.setId(roleId);
			   permission.setPermissionId(pId);
			   sysRolePermissionMapper.insert(permission);
		   }
		
	}
    </#if>
    
    <#if tableName == 'sys_user_role' >
    @Override
    public void batchInsert(List<SysUserRole> u2rs) {
        sysUserRoleMapper.insertList(u2rs);
    }
    </#if>
    
    <#if tableName == 'user_info' >
    @Override
    public UserInfo getByName(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(username);
        return userInfoMapper.selectOne(userInfo);
    }
    
    @Override
    public long cntByUserName(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        return PageHelper.count(() -> userInfoMapper.select(userInfo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String id) {
        userInfoMapper.deleteByPrimaryKey(id);
        SysUserRole userRole = new SysUserRole();
        userRole.setUid(id);
        userRoleMapper.delete(userRole);
    }

    @Override
    public boolean hasButtonPermission(String userId, String permission) {
        return userInfoMapper.hasButtonPermission(userId, permission) == null ? false : true;
    }
    </#if>
    
    ${ignoreText}
    
}
