package com.simple.codemake.model;

import java.util.Arrays;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.simple.codemake.util.PathUtil;

public class TableColumnSchema {
    @Id
    protected String id;
    private String dbColumnName;
	private String columnName;
	private String dataType;
	private String javaType;
	private String mybatisType;
	private String columnComment;
	private String extra;
	
	public String getColumnName() {
		return columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
		this.javaType = getJavaTypeFromDb(dataType);
		this.mybatisType = getMybatisTypeFromDb(dataType);
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJavaType() {
		return javaType;
	}
	public String getDbColumnName() {
		return dbColumnName;
	}
	public void setDbColumnName(String dbColumnName) {
		this.dbColumnName = dbColumnName;
		this.columnName = PathUtil.getHumpWithUnderLine(dbColumnName, false);
	}
	public String getMybatisType() {
		return mybatisType;
	}


	private static final String[] StringArrays = new String[]{"char","varchar","longtext","mediumtext","text","tinytext"};
	private static final String[] IntegerArrays = new String[]{"int","mediumint","smallint","tinyint","bigint"};
	private static final String[] FloatArrays = new String[]{"float"};
	private static final String[] DoubleArrays = new String[]{"double","numeric"};
	private static final String[] DecimalArrays = new String[]{"decimal"};
	private static final String[] DateArrays = new String[]{"date"};
	private static final String[] TimeArrays = new String[]{"datetime","timestamp"};
	private static final String[] BooleanArrays = new String[]{"boolean"};
	
	private String getJavaTypeFromDb(String dbtype) {
		if (Arrays.asList(StringArrays).contains(dbtype)) {
			return "String";
		}
		if (Arrays.asList(IntegerArrays).contains(dbtype)) {
			return "Integer";
		}
		if (Arrays.asList(FloatArrays).contains(dbtype)) {
			return "Float";
		}
		if (Arrays.asList(DoubleArrays).contains(dbtype)) {
			return "Double";
		}
		if (Arrays.asList(DecimalArrays).contains(dbtype)) {
			return "BigDecimal";
		}
		if (Arrays.asList(DateArrays).contains(dbtype) || Arrays.asList(TimeArrays).contains(dbtype)) {
			return "Date";
		}
		if (Arrays.asList(BooleanArrays).contains(dbtype)) {
			return "Boolean";
		}
		return null;
	}
	
	private String getMybatisTypeFromDb(String dbtype) {
		if (Arrays.asList(StringArrays).contains(dbtype)) {
			return "VARCHAR";
		}
		if (Arrays.asList(IntegerArrays).contains(dbtype)) {
			return "INTEGER";
		}
		if (Arrays.asList(FloatArrays).contains(dbtype)) {
			return "FLOAT";
		}
		if (Arrays.asList(DoubleArrays).contains(dbtype)) {
			return "DOUBLE";
		}
		if (Arrays.asList(DecimalArrays).contains(dbtype)) {
			return "DECIMAL";
		}
		if (Arrays.asList(DateArrays).contains(dbtype)) {
			return "DATE";
		}
		if (Arrays.asList(TimeArrays).contains(dbtype)) {
			return "TIMESTAMP";
		}
		if (Arrays.asList(BooleanArrays).contains(dbtype)) {
			return "BOOLEAN";
		}
		return null;
	}
}
