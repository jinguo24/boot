package com.simple.common.crud;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author chenkx
 * @date   2017-12-26
 */
public class BaseModel implements Serializable{

    private static final long serialVersionUID = 6241879562754564639L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;
    
    @Transient
    protected List<String> ids;
    @Transient
    protected String sortColumns;
  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSortColumns() {
		return sortColumns;
	}
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}