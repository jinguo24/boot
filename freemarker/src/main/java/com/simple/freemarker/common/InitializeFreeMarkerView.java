package com.simple.freemarker.common;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import freemarker.ext.servlet.IncludePage;

/**
 * @author zhangxd
 * 
 */
public class InitializeFreeMarkerView extends FreeMarkerView {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.view.freemarker.FreeMarkerView#exposeHelpers
	 * (java.util.Map, javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exposeHelpers(Map model, HttpServletRequest request) throws Exception {
		super.exposeHelpers(model, request);
		Enumeration<String> parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			String paramterName = parameters.nextElement();
			model.put(paramterName, request.getParameter(paramterName));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doRender(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//model.put(CONTEXT_INCLUDE, new IncludePage(request, response));
		super.doRender(model, request, response);
	}

}