package com.simple.common;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.simple.WeChatConstant;
import com.simple.WeChat;
import com.simple.util.HttpClientUtil;
import com.simple.util.PrimaryKeyUtil;
import com.simple.util.Sha1Util;

/**
 * 微信js接口凭证, js调用时需要此凭证
 * @author zhengfy1
 *
 */
public class WeChatJsApiTicket {

	private static final Log log = LogFactory.getLog(WeChatJsApiTicket.class);
	
	private static String JS_TICKET = null;//缓存2小时
	private static Object lock_js_ticket = new Object();
	
	/**
	 * 刷新ticket, 系统做定时刷新，2小时内
	 * @param appId
	 * @param secret
	 */
	public static void installJsTicket(String appId,String secret,WeChatInterface wcInterface) {
		JsApiTicket jt = getJsApiTicketFromTX(appId,secret,wcInterface);
		if ( null != jt ) {
			JS_TICKET = jt.getTicket();
			wcInterface.setJsTicket(appId,JS_TICKET);
		}
	}
	
	/**
	 * 
	 * @Description: 获取jsapi_ticket
	 * @return
	 */
	private static JsApiTicket getJsApiTicketFromTX(String appId,String secret,WeChatInterface wcInterface) {
		JsApiTicket jsapi_ticket = null;
		StringBuilder getUrl = new StringBuilder();
		getUrl.append(WeChatConstant.JSAPI_TICKET_URL).append("?access_token=").append(
				WeChat.getGloalToken(appId,secret,wcInterface))
			.append("&type=jsapi");
		try {
			String result = HttpClientUtil.doGet(getUrl.toString(), "utf-8");
			//logger.info("request jsapi success, result = {}.", result);
		    jsapi_ticket = JsApiTicket.analytic(result);
		} catch (Exception e) {
			log.error("request jsapi_ticket fail.", e);
		}
		return jsapi_ticket;
	}
	
	private static String getJsTicket(String appId,String secret,WeChatInterface wcInterface) {
		JS_TICKET = wcInterface.getJsTicket(appId);
		if (StringUtils.isEmpty(JS_TICKET)) {
			synchronized (lock_js_ticket) {
				while (StringUtils.isEmpty(JS_TICKET)) {
					installJsTicket(appId,secret,wcInterface); 
				}
			}
		}
		return JS_TICKET;
	}
	
	/**
	 *
	  * @Description: js SDK接口注入权限验证配置
	  * @author lining
	  * @date 2015年6月8日 下午3:33:46
	  * @param url 网页的URL，不包含#及其后面部分
	  * @return
	 * @throws Exception 
	 */
	public static JsConfigInfo getJsConfigInfo(String url,String appId,String secret,WeChatInterface wcInterface) throws Exception {
		if(url == null || url.equals("")) {
			return new JsConfigInfo();
		}
		String[] urls = url.split("#");
		JsConfigInfo jsConfig = new JsConfigInfo();
		jsConfig.setAppid(appId);
		String timestamp = Long.toString(new Date().getTime());
		String nonceStr = PrimaryKeyUtil.getRandomString();
		if (timestamp.length() > 10) {
			timestamp = timestamp.substring(0,10);
		}
		jsConfig.setTimestamp(timestamp);
		jsConfig.setNonceStr(nonceStr);
		String jsapi_ticket = getJsTicket(appId,secret,wcInterface);
		
	   	StringBuilder sb = new StringBuilder();
	   	sb.append("jsapi_ticket=").append(jsapi_ticket).append("&")
	   	       .append("noncestr=").append(nonceStr).append("&")
	   	       .append("timestamp=").append(timestamp).append("&")
	   	       .append("url=").append(urls[0].replaceAll("\\s*", ""));
	   	jsConfig.setSignature(Sha1Util.getSignature(sb.toString()));
//	   	SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
//		parameters.put("jsapi_ticket", jsapi_ticket);
//		parameters.put("noncestr", nonceStr);
//		parameters.put("timestamp", timestamp);
//		parameters.put("url", urls[0].replaceAll("\\s*", ""));
//		String sign = PayCommonUtil.createSign(parameters,false);
//		jsConfig.setSignature(sign);
		return jsConfig;
	}
	
}
