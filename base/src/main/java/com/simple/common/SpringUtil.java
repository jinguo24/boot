package com.simple.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Sring 获取bean的工具 以及spring ApplicationEvent监听者
 * 
 */
@Component
public class SpringUtil implements ApplicationContextAware{

	private static ApplicationContext applicationContext;


	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		applicationContext = context;
	}
	
	/**
	 * 获取sping管理的bean
	 * 
	 * @param beanId
	 * @return Object
	 */
	public static Object getBean(String beanId) {
		if ( null == applicationContext){
			return null; 
		}
		return applicationContext.getBean(beanId);
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		if ( null == applicationContext ){
			return null;
		}
		return applicationContext.getBean(clazz);
	}

}
