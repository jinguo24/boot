package com.simple.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.simple.WeChatConstant;
import com.simple.util.HttpClientUtil;

/**
 * 微信公共token, 调用微信功能接口需要此token
 * @author zhengfy1
 *
 */
public class WeChatGlobalToken {
	private static final Log log = LogFactory.getLog(WeChatGlobalToken.class);
	
	private static String GLOBAL_ACCESS_TOKEN = null;//全局token,有效期2个小时
	private static Object lock_global_token = new Object();
	
	/**
	 * 刷新token,项目中做定时刷新此方法, 2个小时内刷新此token
	 * @param appId
	 * @param secret
	 */
	public static void installAcessToken(String appId,String secret,WeChatInterface wcInterface) {
		GLOBAL_ACCESS_TOKEN = getGlobalAccessTokenByRequest(appId,secret);
		wcInterface.setGlobalToken(appId,GLOBAL_ACCESS_TOKEN);
	}
	
	private static String getGlobalAccessTokenByRequest(String appId,String secret) {
		GlobalAccessToken token = getAccessTokenFromTX(appId,secret);
		return token.getAccess_token();
	}
	/**
	 * @Description:获取access_token
	 * @return access_token
	 */
	private static GlobalAccessToken getAccessTokenFromTX(String appId,String secret) {
		GlobalAccessToken access_token = null;
		// 创建请求
		try {
			String result = HttpClientUtil.doGet(String.format(WeChatConstant.ACCESS_TOKEN_URL,appId,secret), "utf-8");
			log.info("request success, result = {}."+result);
			access_token = GlobalAccessToken.analyticToken(result);
		} catch (Exception e) {
			log.error("request access_token fail.", e);
		}
		return access_token;
	}
	
	public static String getGlobalAccessToken(String appId, String secret ,WeChatInterface wcInterface) {
		GLOBAL_ACCESS_TOKEN = wcInterface.getGlobalToken(appId);
		if (StringUtils.isEmpty(GLOBAL_ACCESS_TOKEN)) {
			synchronized (lock_global_token) {
				while (StringUtils.isEmpty(GLOBAL_ACCESS_TOKEN)) {
					installAcessToken(appId,secret,wcInterface); 
				}
			}
		}
		return GLOBAL_ACCESS_TOKEN;
	}
}
