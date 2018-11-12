package com.simple.freemarker.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author zhangxd
 * 
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment environment, @SuppressWarnings("rawtypes") Map parameters, TemplateModel[] templateModel,
			TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
		Map<String,TemplateModel> params = new HashMap<String,TemplateModel>();
		Map requestParameters = DirectiveRequest.getParameters();
		if ( null != requestParameters) {
			params.putAll(requestParameters);
		}
		if ( null != parameters) {
			params.putAll(parameters);
		}
		execute(new DirectiveHandler(environment, params, templateDirectiveBody));
	}

	/**
	 * @return
	 */
	public Map<String, Object> getMap() {
		return new HashMap<String, Object>();
	}

	public abstract void execute(DirectiveHandler handler) throws TemplateException, IOException;
}
