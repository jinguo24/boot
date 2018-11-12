package com.simple.freemarker.common;


import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

public class DirectiveHandler {
	private Environment environment;
	private Map<String, TemplateModel> parameters;//请求带入的参数
	private TemplateDirectiveBody templateDirectiveBody;
	private Map<String, Object> setParameters = new HashMap<String, Object>();//设置的参数

	public DirectiveHandler(Environment environment, Map<String, TemplateModel> parameters,
			TemplateDirectiveBody templateDirectiveBody) {
		this.environment = environment;
		this.parameters = parameters;
		this.templateDirectiveBody = templateDirectiveBody;
	}

	/**
	 * 打印变量
	 * 
	 * @param str
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void print(String str) throws IOException, TemplateException {
		environment.getOut().append(str);
	}


	public void put(String realKey,Object object)  throws TemplateModelException {
		setParameters.put(realKey, object);
	}
	
	//渲软
	public void render() throws TemplateException, IOException {
		//先将原来的放到临时的map
		Map<String, TemplateModel> reduceMap = getReduceMap();
		//执行现在的
		if (null != templateDirectiveBody)
			templateDirectiveBody.render(environment.getOut());
		//执行完毕还原回原来的
		reduce(reduceMap);
	}
	
	private Map<String, TemplateModel> getReduceMap() throws TemplateModelException {
		Map<String, TemplateModel> reduceMap = new HashMap<String, TemplateModel>();
		for (String realKey : setParameters.keySet()) {
			if (null != environment.getVariable(realKey))
				reduceMap.put(realKey, environment.getVariable(realKey));
			environment.setVariable(realKey, environment.getObjectWrapper().wrap(setParameters.get(realKey)));
		}
		return reduceMap;
	}
	
	/**
	 * @param key
	 * @throws TemplateModelException
	 */
	private void reduce(Map<String, TemplateModel> map) throws TemplateModelException {
		for (String key : map.keySet()) {
			environment.setVariable(key, map.get(key));
		}
	}

	/**
	 * @param index
	 * @return
	 * @throws TemplateModelException
	 */
	public TemplateHashModel getMap(String name) throws TemplateModelException {
		TemplateModel model = parameters.get(name);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateHashModelEx) {
			return (TemplateHashModelEx) model;
		} else if (model instanceof TemplateHashModel) {
			return (TemplateHashModel) model;
		} else {
			return null;
		}
	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public String getString(String name) throws TemplateException {
		TemplateModel model = parameters.get(name);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateScalarModel) {
			return ((TemplateScalarModel) model).getAsString();
		} else if ((model instanceof TemplateNumberModel)) {
			return ((TemplateNumberModel) model).getAsNumber().toString();
		} else {
			return null;
		}
	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public Integer getInteger(String name) throws TemplateException {
		TemplateModel model = parameters.get(name);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) model).getAsNumber().intValue();
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isBlank(s)) {
				return null;
			}
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public Short getShort(String name) throws TemplateException {
		TemplateModel model = parameters.get(name);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) model).getAsNumber().shortValue();
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isBlank(s)) {
				return null;
			}
			try {
				return Short.parseShort(s);
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public Long getLong(String name) throws TemplateException {
		TemplateModel model = parameters.get(name);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) model).getAsNumber().longValue();
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isBlank(s)) {
				return null;
			}
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public Double getDouble(String name) throws TemplateException {
		TemplateModel model = parameters.get(name);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) model).getAsNumber().doubleValue();
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isBlank(s)) {
				return null;
			}
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public Integer[] getIntegerArray(String name) throws TemplateException {
		String[] arr = getStringArray(name);
		if (null != arr) {
			Integer[] ids = new Integer[arr.length];
			int i = 0;
			try {
				for (String s : arr) {
					ids[i++] = Integer.valueOf(s);
				}
				return ids;
			} catch (NumberFormatException e) {
				return null;
			}
		} else
			return null;

	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public Long[] getLongArray(String name) throws TemplateException {
		String[] arr = getStringArray(name);
		if (null != arr) {
			Long[] ids = new Long[arr.length];
			int i = 0;
			try {
				for (String s : arr) {
					ids[i++] = Long.valueOf(s);
				}
				return ids;
			} catch (NumberFormatException e) {
				return null;
			}
		} else
			return null;

	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public String[] getStringArray(String name) throws TemplateException {
		String str = getString(name);
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return StringUtils.split(str, ',');
	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public Boolean getBoolean(String name) throws TemplateException {
		TemplateModel model = parameters.get(name);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateBooleanModel) {
			return ((TemplateBooleanModel) model).getAsBoolean();
		} else if (model instanceof TemplateNumberModel) {
			return !(0 == ((TemplateNumberModel) model).getAsNumber().intValue());
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isNotBlank(s)) {
				return !("0".equals(s) || "false".equalsIgnoreCase(s));
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @param name
	 * 
	 * @return
	 * @throws TemplateException
	 */
	public Date getDate(String name) throws TemplateException {
		TemplateModel model = parameters.get(name);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateDateModel) {
			return ((TemplateDateModel) model).getAsDate();
		} else if (model instanceof TemplateScalarModel) {
			String temp = StringUtils.trimToEmpty(((TemplateScalarModel) model).getAsString());
			try {
				if (FreemarkerConstant.FULL_DATE_LENGTH == temp.length()) {
					return FreemarkerConstant.FULL_DATE_FORMAT.parse(temp);
				} else if (FreemarkerConstant.SHORT_DATE_LENGTH == temp.length()) {
					return FreemarkerConstant.SHORT_DATE_FORMAT.parse(temp);
				} else {
					return null;
				}
			} catch (ParseException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public Object getObject(String key) throws TemplateModelException {
		TemplateModel  model = parameters.get(key);
		if ( model instanceof StringModel) {
			return ((StringModel) model).getWrappedObject();
		}
		return null;
	}
	
	/**
	 * @return the parameters
	 */
	public Map<String, TemplateModel> getParameters() {
		return parameters;
	}

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @return the templateDirectiveBody
	 */
	public TemplateDirectiveBody getTemplateDirectiveBody() {
		return templateDirectiveBody;
	}
}
