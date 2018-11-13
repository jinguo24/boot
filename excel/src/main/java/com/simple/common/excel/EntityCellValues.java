package com.simple.common.excel;

import java.io.Serializable;
import java.util.List;

public class EntityCellValues implements Serializable{

	private static final long serialVersionUID = 1L;

	private Object o;
	private List<String> cellValues;
	public Object getO() {
		return o;
	}
	public void setO(Object o) {
		this.o = o;
	}
	public List<String> getCellValues() {
		return cellValues;
	}
	public void setCellValues(List<String> cellValues) {
		this.cellValues = cellValues;
	}
}
