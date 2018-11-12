package com.simple.common;

/**
 * 需要调用方实现的接口
 * @author zhengfy1
 *
 */
public interface WeChatInterface {

	public void setGlobalToken(String appId,String token);
	
	public String getGlobalToken(String appId);
	
	public void setJsTicket(String appId,String ticket);
	
	public String getJsTicket(String appId);
}
