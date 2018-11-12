package com.simple;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class WeChatTest {

	private WeChatExecutor excutor = new WeChatExecutor();
	
	@Test
    public void getAuthUrl() {
		String appId = "";
		String callBackUrl = "http://www.baidu.com";
		System.out.println(WeChat.getAuthUrl(appId, callBackUrl, true, "123132"));
    }
}
