package com.simple.freemarker.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author renren
 */

public class RequestUtil {

	public static String getParams(HttpServletRequest request) {
		try {
			return getParams(request,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getParamsWithNoPage(HttpServletRequest request) {
		try {
			return getParams(request,false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private static String getParams(HttpServletRequest request,boolean hasPage) throws Exception {
		Enumeration enu=request.getParameterNames();
		StringBuffer s = new StringBuffer();
		while(enu.hasMoreElements()){
			String paraName=(String)enu.nextElement();
			if (hasPage) {
				if ("f".equals(paraName)) {
					continue;
				}
			}else {
				if ("page".equals(paraName) || "f".equals(paraName)) {
					continue;
				}
			}
			s.append(paraName).append("=").append(request.getParameter(paraName)).append("&");
		} 
		return s.toString();
		//return s.toString().replace("?f=", "&f=").replace(" ", "%20");
	}
}
