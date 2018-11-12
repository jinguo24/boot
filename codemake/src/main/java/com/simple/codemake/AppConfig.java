package com.simple.codemake;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.simple.freemarker.common.FreeMarkerScaner;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer{

    
	/**
	 * 配置拦截器
	 * 
	 * @author wupb1
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		//registry.addInterceptor(new TimecostInterceptor()).addPathPatterns("/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("*.html").addResourceLocations("classpath:/html/");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry)
	{
		// registry.viewResolver(jspResolver());
		// registry.jsp("/WEB-INF/jsp/",".jsp");
		registry.viewResolver(freemarkerResolver());
		//registry.viewResolver(httlResolver());
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}

	/**
	 * 设置Converter 字符集， 处理中文乱码
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
	}

//	public ViewResolver jspResolver()
//	{
//		InternalResourceViewResolver jspResolver = new InternalResourceViewResolver();
//		jspResolver.setPrefix("/WEB-INF/jsp/");
//		jspResolver.setSuffix(".jsp");
//		jspResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
//		jspResolver.setOrder(3);
//		jspResolver.setContentType("text/html;charset=UTF-8");
//		return jspResolver;
//	}

	public ViewResolver freemarkerResolver()
	{
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setPrefix("/ftl/");
		resolver.setSuffix(".ftl");
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
		resolver.setOrder(3);
		return resolver;
	}

//	@Bean
//	public FreeMarkerConfigurer freeMarkerConfigurer()
//	{
//		FreeMarkerConfigurer freemarkerConfig = new FreeMarkerConfigurer();
//		freemarkerConfig.setTemplateLoaderPath("/ftl/");
//		Properties settings = new Properties();
//		settings.setProperty("template_update_delay", "0");
//		settings.setProperty("default_encoding", "UTF-8");
//		settings.setProperty("number_format", "0.##");
//		settings.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
//		settings.setProperty("classic_compatible", "true");
//		settings.setProperty("template_exception_handler", "ignore");
//		freemarkerConfig.setFreemarkerSettings(settings);
//		return freemarkerConfig;
//	}
	
//	@Bean
//	public FreeMarkerScaner freeMarkerScaner()
//	{
//		FreeMarkerScaner fs = new FreeMarkerScaner("com.simple.freemarker.directive", "com.simple.freemarker.util", freeMarkerConfigurer());
//		//fs.doScan();
//		return fs;
//	}

	/*@Bean
	public BeanUtils beanUtils()
	{
		return new BeanUtils();
	}

	@Bean
	public DBSources dBSources()
	{
		return new DBSources();
	}

	@Bean
	public DBService dBService()
	{
		DBService dBService = new DBServiceImpl();
		return dBService;
	}

	@Bean
	public DBDao dBDao()
	{
		DBDao dBDao = new DBDao();
		return dBDao;
	}*/

//	public ViewResolver httlResolver()
//	{
//		HttlViewResolver resolver = new HttlViewResolver();
//		resolver.setSuffix(".httl");
//		resolver.setContentType("text/html;charset=UTF-8");
//		resolver.setOrder(2);
//		return resolver;
//	}

	// @Bean
//	public FilterRegistrationBean<AuthFilter> authFilter()
//	{
//		FilterRegistrationBean<AuthFilter> reg = new FilterRegistrationBean<AuthFilter>();
//		AuthFilter authFilter = new AuthFilter();
//		reg.setFilter(authFilter);
//		reg.setName("authFilter");
//		Map<String, String> initParams = new HashMap<String, String>();
//		initParams.put("router", "tpl");
//		initParams.put("type", "auth");
//		initParams.put("nofilter", "/,/index.html,/404.html,/500.html,/test");
//		reg.setInitParameters(initParams);
//		reg.addUrlPatterns("/*");
//		return reg;
//	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCorsMappings(CorsRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFormatters(FormatterRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReturnValueHandlers(
			List<HandlerMethodReturnValueHandler> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendHandlerExceptionResolvers(
			List<HandlerExceptionResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	} 
          
    
    
}
