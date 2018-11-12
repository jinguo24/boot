package com.simple.codemake.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.simple.codemake.AppConfig;
import com.simple.codemake.CodeMake;
import com.simple.codemake.CodeMakeApplication;

/**
 * 
 * @author soldi
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { CodeMakeApplication.class,AppConfig.class})
@AutoConfigureMockMvc
public class AppTest
{

	@Autowired
	private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

	@Autowired
	private WebApplicationContext wac; // 注入WebApplicationContext

	@Before // 在测试开始前初始化工作
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Autowired
	CodeMake codeMake;

	@Test
	public void test() 
	{
		try {
			//项目目录名称
			String project = "hxybaks";
			String db = "hxy";
			//生成项目文件,需要该db下有sys_permission，sys_role,sys_role_permission,sys_user_role,user_info
			codeMake.makeProjectFile(project, db);
			
//			//生成数据库下所有的表
			codeMake.make(project, db, true, null);
			
//			生成数据库下某些表
//			List<String> tables = new ArrayList<String>();
//			tables.add("teamDevelopment");
//			tables.add("sys_role");
//			codeMake.make(project, db, false, tables);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
