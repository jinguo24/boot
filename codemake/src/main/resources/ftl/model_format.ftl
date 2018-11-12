package ${entityPackageName};

import com.simple.common.crud.BaseModel;
import javax.persistence.*;
import java.util.*;
import java.math.*;
import javax.persistence.Transient;
import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;

@Table(name = "${tableName!}")
public class ${entityName!} extends BaseModel {
	private static final long serialVersionUID = 1L;
<#list columns as col>
	<#if col.columnName != 'id' >
	/*${col.columnComment}**/
	@io.swagger.annotations.ApiModelProperty(value="${col.columnComment}",name="${col.columnName}")
	private ${col.javaType} ${col.columnName};
	<#if col.columnComment?contains("[LIST]")>
	@Transient
	private List<${col.javaType}> ${col.columnName}s;
	</#if>		
	<#if col.columnComment?contains("[LIKE]")>
	@Transient
	private ${col.javaType} ${col.columnName}Like;
	</#if>
	<#if col.columnComment?contains("[FROM]")>
	@Transient
	private ${col.javaType} ${col.columnName}From;
	</#if>
	<#if col.columnComment?contains("[TO]")>
	@Transient
	private ${col.javaType}  ${col.columnName}To;
	</#if>
	<#if col.columnComment?contains("[GTE]")>
	@Transient
	private ${col.javaType} ${col.columnName}Gte;
	</#if>
	<#if col.columnComment?contains("[GT]")>
	@Transient
	private ${col.javaType} ${col.columnName}Gt;
	</#if>
	<#if col.columnComment?contains("[LTE]")>
	@Transient
	private ${col.javaType} ${col.columnName}Lte;
	</#if>
	<#if col.columnComment?contains("[LT]")>
	@Transient
	private ${col.javaType} ${col.columnName}Lt;
	</#if>
	</#if>	
</#list>
<#if tableName == 'user_info' >
    //该用户所有的角色
    @Transient
    private List<SysRole> role;
    //该用户包含的所有权限
    @Transient
    private List<SysPermission> permission;
</#if>
<#list columns as col>
<#if col.columnName != 'id'>
	public ${col.javaType}  get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}() {
		return ${col.columnName};
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}(${col.javaType} _${col.columnName}) {
		${col.columnName} = _${col.columnName};
	}
	<#if col.columnComment?contains("[LIST]")>
	public List<${col.javaType}>  get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}s() {
		return ${col.columnName}s;
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}s( List<${col.javaType}> _${col.columnName}s) {
		${col.columnName}s = _${col.columnName}s;
	}
	</#if>	
	<#if col.columnComment?contains("[LIKE]")>
	public ${col.javaType}  get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Like() {
		return ${col.columnName}Like;
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Like( ${col.javaType} _${col.columnName}Like) {
		${col.columnName}Like = _${col.columnName}Like;
	}
	</#if>	
	<#if col.columnComment?contains("[FROM]")>
	public ${col.javaType} get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}From() {
		return ${col.columnName}From;
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}From( ${col.javaType} _${col.columnName}From) {
		${col.columnName}From = _${col.columnName}From;
	}
	</#if>
	<#if col.columnComment?contains("[TO]")>
	public ${col.javaType} get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}To() {
		return ${col.columnName}To;
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}To( ${col.javaType} _${col.columnName}To) {
		${col.columnName}To = _${col.columnName}To;
	}
	</#if>
	<#if col.columnComment?contains("[GTE]")>
	public ${col.javaType} get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Gte() {
		return ${col.columnName}Gte;
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Gte( ${col.javaType} _${col.columnName}Gte) {
		${col.columnName}Gte = _${col.columnName}Gte;
	}
	</#if> 
	<#if col.columnComment?contains("[GT]")>
	public ${col.javaType} get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Gt() {
		return ${col.columnName}Gt;
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Gt( ${col.javaType} _${col.columnName}Gt) {
		${col.columnName}Gt = _${col.columnName}Gt;
	}
	</#if>
	<#if col.columnComment?contains("[LTE]")>
	public ${col.javaType} get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Lte() {
		return ${col.columnName}Lte;
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Lte( ${col.javaType} _${col.columnName}Lte) {
		${col.columnName}Lte = _${col.columnName}Lte;
	}
	</#if>
	<#if col.columnComment?contains("[LT]")>
	public ${col.javaType} get${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Lt() {
		return ${col.columnName}Lt;
	}
	public void set${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}Lt( ${col.javaType} _${col.columnName}Lt) {
		${col.columnName}Lt = _${col.columnName}Lt;
	}
	</#if>
</#if>		
</#list>
<#if tableName == 'user_info' >
    public List<SysRole> getRole() {
        return role;
    }

    public void setRole(List<SysRole> role) {
        this.role = role;
    }

    public List<SysPermission> getPermission() {
        return permission;
    }

    public void setPermission(List<SysPermission> permission) {
        this.permission = permission;
    }
</#if>

${ignoreText}

	public static enum Field
	{
	<#list columns as col>
		<#if col_index !=0>,</#if>${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}_ASC("`${col.columnName}` ASC"),${col.columnName?substring(0,1)?upper_case}${col.columnName?substring(1)}_DESC("`${col.columnName}` DESC")
	</#list>;
		private String value;
		Field(String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public void setCol(String value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return this.getValue();
		}
	}
	
	public void setSortColumns(${entityName!}.Field... fields)
	{
		if (fields == null || fields.length == 0) {
			return;
		}
		for (int k = 0; k < fields.length; k++) {
			if (fields[k] == null) {
				return;
			}
		}
		StringBuilder sb = new StringBuilder(fields[0].toString());
		for (int k = 1; k < fields.length; k++) {
			sb.append(",");
			sb.append(fields[k].toString());
		}
		super.setSortColumns(sb.toString());
	}
	
	@Override
	public void setSortColumns(String sortColumns)
	{
		if (sortColumns == null || "".equals(sortColumns.trim())) {
			return;
		}
		if (sortColumns.contains(",")) {
			String[] cols = sortColumns.split(",");
			java.util.List<Field> fList = new java.util.ArrayList();
			for (int k = 0; k < cols.length; k++) {
				fList.add(Field.valueOf(cols[k]));
			}
			this.setSortColumns(fList.toArray(new Field[fList.size()]));
		} else {
			this.setSortColumns(Field.valueOf(sortColumns));
		}
	}
}
