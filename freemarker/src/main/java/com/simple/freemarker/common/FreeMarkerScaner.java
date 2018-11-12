package com.simple.freemarker.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class FreeMarkerScaner implements ApplicationContextAware{

	private static final Log logger = LogFactory.getLog(FreeMarkerScaner.class);
	
	private String directiveBasePackage;
	private String utilPackage;
	private FreeMarkerConfigurer freeMarkerConfigurer;
	private ApplicationContext applicationContext;
	
	private FreeMarkerScaner() {
		
	}

	public void doScan() {
		logger.info("Creating Freemarker directives and methods started");
		Map freemarkerVariables = new HashMap();

		StringBuffer directives = new StringBuffer();
		List<Class<?>> directiveClasses = MyClassUtils.getAllAssignedClass(BaseDirective.class, directiveBasePackage);
		for (Class<?> c : directiveClasses) {
			String directiveName = c.getSimpleName();
			//String directiveName = StringUtils.uncapitalize(c.getSimpleName());
			//directiveName = DIRECTIVE_PREFIX + directiveName.substring(0, directiveName.indexOf(DIRECTIVE_CUT_STRING));
			freemarkerVariables.put(directiveName, applicationContext.getBean(c));
			if (0 != directives.length())
				directives.append(",");
			directives.append(directiveName);
		}
		if (0 != directives.length())
			directives.append(",");


		List<Class<?>> methodClasses = MyClassUtils.getAllAssignedClass(TemplateMethodModelEx.class, directiveBasePackage);
		StringBuffer methods = new StringBuffer();
		for (Class<?> c : methodClasses) {
			String methodName = c.getSimpleName() ;
			freemarkerVariables.put(methodName, applicationContext.getBean(c));
			if (0 != methods.length())
				methods.append(",");
			methods.append(methodName);
		}

		if (0 != methods.length())
			methods.append(",");
		
		
		List<Class<?>> utilClasses = MyClassUtils.getAllAssignedClass(Object.class, utilPackage);
		StringBuffer utils = new StringBuffer();
		for (Class<?> c : utilClasses) {
			String methodName = c.getSimpleName() ;
			freemarkerVariables.put(methodName, applicationContext.getBean(c));
			if (0 != utils.length())
				utils.append(",");
			utils.append(methodName);
		}

		if (0 != methods.length())
			utils.append(",");
		
		
		try {
			freeMarkerConfigurer.getConfiguration().setAllSharedVariables(
					new SimpleHash(freemarkerVariables, freeMarkerConfigurer.getConfiguration().getObjectWrapper()));
			logger.info((directiveClasses.size() + 1) + " directives created:[" + directives.toString() + "]");
			logger.info((methodClasses.size() + 1) + " methods created:[" + methods.toString() + "]");
			logger.info((methodClasses.size() + 1) + " utils created:[" + utils.toString() + "]");
		} catch (TemplateModelException e) {
		}
	}


	public String getDirectiveBasePackage() {
		return directiveBasePackage;
	}

	public void setDirectiveBasePackage(String directiveBasePackage) {
		this.directiveBasePackage = directiveBasePackage;
	}

	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		return freeMarkerConfigurer;
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}
	
	public String getUtilPackage()
	{
		return utilPackage;
	}

	public void setUtilPackage(String utilPackage)
	{
		this.utilPackage = utilPackage;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		doScan();
	}
}
