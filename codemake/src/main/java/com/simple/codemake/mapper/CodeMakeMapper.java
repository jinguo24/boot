package com.simple.codemake.mapper;

import java.util.List;
import java.util.Map;

import com.simple.codemake.model.TableColumnSchema;
import com.simple.common.crud.CommonMapper;
/**
 * @author chenkx
 * @date 2018-01-02.
 */
public interface CodeMakeMapper extends CommonMapper<TableColumnSchema, String> {
	
    List<String> getTables(String db);

	List<TableColumnSchema> getTableColumnSchema(Map param);
}