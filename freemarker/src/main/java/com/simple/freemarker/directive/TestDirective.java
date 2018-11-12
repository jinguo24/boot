package com.simple.freemarker.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.simple.freemarker.common.BaseDirective;
import com.simple.freemarker.common.DirectiveHandler;

import freemarker.template.TemplateException;

//@Component
public class TestDirective extends BaseDirective{
	
	@Override
	public void execute(DirectiveHandler handler) throws TemplateException,
			IOException {
		String type = "1";
		String floorId = handler.getString("floorId");
		Integer sort = handler.getInteger("sort");
		handler.put("floor","123123");
		handler.render();
	}
}



