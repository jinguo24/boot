package com.simple.codemake.service;

import java.util.List;

import com.simple.codemake.model.TableColumnSchema;

public interface CodeMakeService {

	public List<String> getTables(String db);
	
	public List<TableColumnSchema> getTableColumnSchema(String db,String table);
}
