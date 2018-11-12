package com.simple.freemarker.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: Sring 获取bean的工具 以及spring ApplicationEvent监听者
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016年3月29日
 * </p>
 * <p>
 * Company: LENOVO
 * </p>
 * 
 * @author <a href="mailto:wupb1@lenovo.com">soldiers888@gmail.com</a>
 * @version 1.0 2016年3月29日 上午11:28:26
 */
@Component
public class BeanUtils implements ApplicationContextAware
{

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		applicationContext = context;
	}

	public static Object getBean(String beanId)
	{
		return applicationContext.getBean(beanId);
	}

	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	public static <T> T getBean(Class<T> clazz)
	{
		return applicationContext.getBean(clazz);
	}

}
