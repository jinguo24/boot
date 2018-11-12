package com.simple.freemarker.common;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateScalarModel;

public class DirectiveRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	//当前线程的request,供directive使用
	private static ThreadLocal<HttpServletRequest> threadLocalRequestParam = new ThreadLocal<HttpServletRequest>();
	
	public static HttpServletRequest getRequest() {
		return threadLocalRequestParam.get();
	}
	
	public static void setDirectRequest(HttpServletRequest request) {
		threadLocalRequestParam.set(request);
	}
	
	public static Map<String,TemplateScalarModel> getParameters() {
		HttpServletRequest request = threadLocalRequestParam.get();
		if ( null != request) {
			Map<String,TemplateScalarModel> parameter = new HashMap<String,TemplateScalarModel>();
			Enumeration pns = request.getParameterNames();
			while(pns.hasMoreElements()) {
				Object key = pns.nextElement();
				if (key instanceof String) {
					parameter.put((String) key,new SimpleScalar(request.getParameter((String)key)));
				}
			}
			return parameter;
		}
		return null;
	}
	
	public static Map<String,Object> getStringParameters() {
		HttpServletRequest request = threadLocalRequestParam.get();
		Map<String,Object> param = new HashMap<String,Object>();
		if ( null == request) {
			return null;
		}
		Map rparam = request.getParameterMap();
		for (Iterator it = rparam.keySet().iterator();it.hasNext();) {
			Object v = it.next();
			Object object = rparam.get(v);
			if (null!= object && (v instanceof String) ) {
				Object[] list= (Object[]) object;
				if(null!=list && list.length>0) {
					if (list[0] instanceof String && list[0].equals("null")) {
						list[0] = "";
					}
					if ( null != param) {
						param.put((String)v, list[0]);
					}
				}
			}
		}
		return param;
	}
	
	public static String getString(String key) {
		HttpServletRequest request = threadLocalRequestParam.get();
		if ( null != request) {
			return request.getParameter(key);
		}
		return null;
	}
	
	public static Integer getInteger(String key) {
		HttpServletRequest request = threadLocalRequestParam.get();
		if ( null != request) {
			String integer = request.getParameter(key);
			return Integer.parseInt(integer);
		}
		return null;
	}
	
}
