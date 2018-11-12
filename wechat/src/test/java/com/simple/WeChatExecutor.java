package com.simple;

import com.simple.common.WeChatInterface;

/**
 * 具体实现，可用缓存、数据库。。。来存储
 * @author zhengfy1
 *
 */
public class WeChatExecutor implements WeChatInterface{

	//private redis;
	
	@Override
	public void setGlobalToken(String appId, String token) {
		//redis.set();
	}

	@Override
	public String getGlobalToken(String appId) {
		//redis.get()
		return null;
	}

	@Override
	public void setJsTicket(String appId, String ticket) {
		//redis.set();
	}

	@Override
	public String getJsTicket(String appId) {
		//redis.get()
		return null;
	}
}
