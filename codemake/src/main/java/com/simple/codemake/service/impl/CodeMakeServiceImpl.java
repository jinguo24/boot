package com.simple.codemake.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.codemake.mapper.CodeMakeMapper;
import com.simple.codemake.model.TableColumnSchema;
import com.simple.codemake.service.CodeMakeService;

@Service
public class CodeMakeServiceImpl implements CodeMakeService {

	
	@Autowired
	CodeMakeMapper codeMakeMapper;
	
	@Override
	public List<String> getTables(String db) {
		return codeMakeMapper.getTables(db);
	}

	@Override
	public List<TableColumnSchema> getTableColumnSchema(String db, String table) {
		Map param = new HashMap();
		param.put("dbName", db);
		param.put("tableName", table);
		return codeMakeMapper.getTableColumnSchema(param);
	}
	
}
