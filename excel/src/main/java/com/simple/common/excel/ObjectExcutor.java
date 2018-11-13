package com.simple.common.excel;

import java.util.List;
import java.util.Map;

public abstract class ObjectExcutor {
	public abstract Object getObject(Map objectMap,List<String> cellValues) throws Exception;
	
	/*在往list里面新增之前的处理，比如合并等等**/
	public void executeListAdd(Map objectMap,List list,Object o) {
		list.add(o);
	}
	
}
