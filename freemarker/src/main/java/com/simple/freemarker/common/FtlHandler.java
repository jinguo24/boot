package com.simple.freemarker.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.simple.annotation.HoldBegin;
import com.simple.annotation.HoldEnd;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FtlHandler {
	
	private static final String[] CONFIG_FILES = new String[]{"classpath*:freemarker/freemarker.xml", "classpath*:freemarker/freemarker-*.xml"};
	private ClassPathXmlApplicationContext app;
	private static Configuration ftlConfig;
	private static final FtlHandler instance = new FtlHandler();
	
	private FtlHandler() {
		this.app = new ClassPathXmlApplicationContext(CONFIG_FILES);
		this.app.start();
		ftlConfig = ((FreeMarkerConfigurer)this.app.getBean(FreeMarkerConfigurer.class)).getConfiguration();
	}	
	

	private static final Log logger = LogFactory.getLog(FtlHandler.class);
	private static void initModelRequest(ModelMap model,HttpServletRequest request) {
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
					request.setAttribute((String)v, list[0]);
					model.addAttribute((String)v, list[0]);
				}
			}
		}
	}
	
	public static void make(ModelMap model,String ftl,String desFile) {
		try {
			File desf = new File(desFile);
			//读取文件内容，判断似乎包含HoldBegin,HoldEnd，之间的内容要保留
			String ignoreText = "";
			if (desf.exists()) {
				ignoreText = fileReadIgnore(desFile);
				desf.delete();
			}else {
				File parent = desf.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
			}
			model.put("ignoreText", ignoreText);
			Template t = ftlConfig.getTemplate(ftl);
			StringWriter strWriter = new StringWriter();
			t.process(model, strWriter);
			String result = strWriter.toString();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(desFile), "utf-8"));
			model.addAttribute("random", new Random().nextInt());
			writer.write(result);
			writer.flush();
			writer.close();
			model.addAttribute("message", "success");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (TemplateException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	private static String fileReadIgnore(String filepath) throws Exception {
        File file = new File(filepath);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        boolean ishold = false;
        StringBuilder retsb = new StringBuilder();
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
        	//如果还没有读到过begin,则判断是否由HoldBegin，如果由，则表示从此行开始复制
        	if ((!ishold) && (!s.contains("import com.simple.annotation.HoldBegin;")) && s.contains(HoldBegin.class.getSimpleName())) {
        		ishold = true;
        	}
        	if (ishold) {
        		retsb.append(s+"\n");
        	}
        	//如果之前是复制状态，由HoldEnd,则表示复制结束
        	if (ishold && (!s.contains("import com.simple.annotation.HoldEnd;"))  &&  s.contains(HoldEnd.class.getSimpleName())) {
        		retsb.append("\n");
        		ishold = false;
        	}
            //sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
        }
        bReader.close();
        //String str = sb.toString();
        return retsb.toString();
    }
	
	public static void main(String[] args) {
		try {
			System.out.println(HoldBegin.class.getSimpleName());
			System.out.println(fileReadIgnore("C:\\lenovo\\project\\git\\sp\\cy\\baseapi-web\\src\\main\\java\\cn\\wm\\edu\\controller\\ClientCheckinContrller.java"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
