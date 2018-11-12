<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackageName}.${entityName}Mapper">
	<resultMap id="BaseResultMap" type="${entityPackageName}.${entityName}">
		<id column="${pk.dbColumnName}" jdbcType="${pk.mybatisType}" property="${pk.columnName}" />
	<#list columns as col>
		<#if col.columnName != 'id' >
		<result column="${col.dbColumnName}" jdbcType="${col.mybatisType}" property="${col.columnName}" />
		</#if>
	</#list>
	</resultMap>
	
	<sql id="allColumns">
	    <#list columns as col><#if col_index !=0>,</#if>`${col.dbColumnName}`</#list> 
	</sql>

	<sql id="dynamicWhereConditions">
	        where 1 = 1
	<#list columns as col>
		<if test=" null != ${col.columnName} "> and `${col.dbColumnName}` = #${'{'+col.columnName+'}' } </if>    
		<#if col.columnComment?contains("[LIST]")>
		<if test=" null != ${col.columnName}s "> 
			and `${col.dbColumnName}` in
		   <foreach collection="${col.columnName}s" item="item_${col.columnName}" open="(" close=")" separator=",">  
		     #${'{item_'+col.columnName+'}' } 
		   </foreach> 
		</if>
		</#if>
		<#if col.columnComment?contains("[LIKE]")>
		<if test=" null != ${col.columnName}Like and '' != ${col.columnName}Like "> and `${col.dbColumnName}`  like concat('%', #${'{'+col.columnName+'Like}' },'%')</if> 
		</#if> 
		<#if col.columnComment?contains("[FROM]")>
		<if test=" null != ${col.columnName}From "> and `${col.dbColumnName}`  >= #${'{'+col.columnName+'From}'} </if>  
		</#if>
		<#if col.columnComment?contains("[TO]")>
		<if test=" null != ${col.columnName}To ">  and #${'{'+col.columnName+'To}'}    >= `${col.dbColumnName}` </if>  
		</#if> 
		<#if col.columnComment?contains("[GTE]")>
		<if test=" null != ${col.columnName}Gte ">  and `${col.dbColumnName}`  >= #${'{'+col.columnName+'Gte}'} </if>  
		</#if>
		<#if col.columnComment?contains("[GT]")>
		<if test=" null != ${col.columnName}Gt ">  and `${col.dbColumnName}`  > #${'{'+col.columnName+'Gt}'} </if>  
		</#if>
		<#if col.columnComment?contains("[LTE]")>
		<if test=" null != ${col.columnName}Lte "> and #${'{'+col.columnName+'Lte}'}  >=  `${col.dbColumnName}` </if>  
		</#if> 
		<#if col.columnComment?contains("[LT]")>
		<if test=" null != ${col.columnName}Lt "> and #${'{'+col.columnName+'Lt}'}  >  `${col.dbColumnName}` </if>  
		</#if> 
	</#list> 
		<if test=" null != ids ">
		 	and id in  
			<foreach collection="ids" index="index" item="idItem" open="(" separator="," close=")">
			#${'{idItem}'}
			</foreach>
		</if>		
		<if test=" null != sortColumns"> order by $${'{sortColumns}'} </if>
	</sql>
	
	<select id="findList" parameterType="${entityPackageName}.${entityName!}" resultMap="BaseResultMap">
		select <include refid="allColumns" /> from ${tableName!}
		<include refid="dynamicWhereConditions" />
	</select>
	
	<#if tableName == 'sys_permission' >
	<select id="findPermissionByRoleId" parameterType="java.lang.Long" resultMap="BaseResultMap">
	    SELECT p.*
	    FROM sys_role_permission r2p, sys_permission p
	    WHERE r2p.permission_id = p.id
	    AND r2p.id = ${r"#{roleId}"}
	</select>

  	<select id="findPermissionByRoleIds" resultMap="BaseResultMap"> 
	    SELECT p.*
	    FROM sys_role_permission r2p, sys_permission p
	    WHERE resource_type='0' and r2p.permission_id = p.id 
	    <if test="roleIds != null and roleIds.size() > 0 ">
		    AND r2p.id in
		    <foreach item="id" index="index" collection="roleIds" open="(" separator="," close=")">
		      ${r"#{id}"}
		    </foreach>
		</if>
  	</select>
	</#if>
	
	<#if tableName == 'sys_role' >
	<select id="countByName" resultType="java.lang.Integer"  parameterType="java.util.HashMap">
   		select count(1) from sys_role where name=${r"#{name}"}
   		<if test="id!=null"> and id !=${r"#{id}"}</if>
   	</select>
   	
    <select id="findRoleByUserId" parameterType="java.lang.Long" resultMap="BaseResultMap">
        SELECT r.id
        FROM sys_user_role u2r, sys_role r
        WHERE u2r.role_id = r.id
        AND u2r.uid = ${r"#{userId}"}
    </select>
	</#if>
	
	<#if tableName == 'sys_role_permission' >
    <delete id="deleteByRoleId" parameterType="java.util.HashMap">
        delete from sys_role_permission where role_id=${r"#{roleId}"}
    </delete>
	</#if>
	
	<#if tableName == 'user_info' >
    <select id="hasButtonPermission" resultType="java.lang.Integer">
	    SELECT 1
	    FROM sys_user_role ur, sys_role_permission rp, sys_permission p
	    WHERE rp.role_id = ur.role_id
	    AND rp.permission_id = p.id
	    AND p.resource_type = 1
	    AND ur.uid = ${r"#{userId}"}
	    AND p.permission = ${r"#{permission}"}
  	</select>
	</#if>
	
	${ignoreText}
</mapper>
