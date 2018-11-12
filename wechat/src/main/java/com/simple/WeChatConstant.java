package com.simple;

public class WeChatConstant {

	/**网页授权获，OAuth2.0请求接口地址*/
	public static final String OAUTH_PAGE_CONNECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
	/**网页授权获，code换取网页授权access_token*/
	public static final String OAUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/**网页授权获，取用户信息*/
	public static final String OAUTH_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	
	/**全局access_token获取地址*/
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	
	/**微信JS接口的临时票据获取地址*/
	public static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	/**统一下单接口*/
	public static final String CREATOR_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**申请退款接口*/
	public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
}
