package com.simple.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.simple.WeChatConstant;
import com.simple.util.HttpClientUtil;

/**
 * 微信授权
 * @author zhengfy1
 *
 */
public class WeChatAuth {

	private static final Log log = LogFactory.getLog(WeChatAuth.class);
	/**
	 * 第一步
	 * 
	 * 微信授权地址
	 * callBackUrl:授权成功后跳转地址，授权成功之后，会在该url后面加上参数code
	 * isAuth: 
	 * 			true-显示授权：弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，
	 * 										即使在未关注的情况下，只要用户授权，也能获取其信息
	 *          false-静默授权：不弹出授权页面，直接跳转，只能获取用户openid
	 */
	public static String getAuthUrl(String appId,String callBackUrl,boolean isAuth,String state) {
			try {
					if (isAuth) {
						return getOAuthURL(appId,callBackUrl, ScopeType.snsapi_userinfo.name(), state);
					}else {
						return getOAuthURL(appId,callBackUrl, ScopeType.snsapi_base.name(), state);
					}
			} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * 
	 * @Description: 网页授权，生成OAuth2.0接口请求地址
	 * @param callbackUrl 回调地址
	 * @param scope 应用授权作用域
	 * @param state 回调地址会带上state参数，可以填写a-zA-Z0-9的参数值，最多128字节
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getOAuthURL(String appId,String callbackUrl, String scope, String state)
			throws UnsupportedEncodingException {
			StringBuilder url = new StringBuilder();
			url.append(WeChatConstant.OAUTH_PAGE_CONNECT_URL).append("?appid=").append(appId)
				.append("&redirect_uri=").append(URLEncoder.encode(callbackUrl, "UTF-8"))
				.append("&response_type=code&scope=").append(scope);
			if(state != null) {
				url.append("&state=").append(state);
			}
			url.append("#wechat_redirect");
			return url.toString();
	}
	
	/**
	 * 获取用户信息
	 * 如果是静默授权，则只能获取到openId
	 * 如果是显示授权，则调用接口获取用户信息
	 */
	public static OAuthUserInfo getUserInfo(String appId,String secret,String code) {
			//1.通过code获取token
			OAuthAccessToken token = getOAuthAccessToken(appId,secret,code);
			if ( null != token ) {
				 if (token.getScope().equals(ScopeType.snsapi_userinfo)) {
					 return getOAuthUserInfo(token.getAccess_token(),token.getOpenid());
				 }else {
					 OAuthUserInfo ui = new OAuthUserInfo();
					 ui.setOpenid(token.getOpenid());
					 return ui;
				 }
			}
			return null;
	}
	
	
	
	/**
	 * 通过code获取token
	 * 
	 * @Description: 网页授权，通过code换取网页授权access_token
	 * @param code
	 * @return
	 */
	private static OAuthAccessToken getOAuthAccessToken(String appId,String secret,String code) {
				OAuthAccessToken oaat = null;
				StringBuilder getUrl = new StringBuilder();
				getUrl.append(WeChatConstant.OAUTH_ACCESS_TOKEN_URL).append("?appid=").append(appId)
					.append("&secret=").append(secret).append("&code=").append(code).append("&grant_type=authorization_code");
				try {
					String result = HttpClientUtil.doGet(getUrl.toString(), "utf-8");
					oaat = OAuthAccessToken.analyticToken(result);
				} catch (Exception e) {
					log.error(" get oauth_access_token error:" + e.toString(), e);
				}
				return oaat;
	}
	
	/**
	 * 
	 * @Description: 网页授权，获取用户信息
	 * @param access_token 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openId
	 * @return
	 */
	private static OAuthUserInfo getOAuthUserInfo(String access_token, String openId) {
		OAuthUserInfo userinfo = null;
		StringBuilder getUrl = new StringBuilder();
		getUrl.append(WeChatConstant.OAUTH_USER_INFO_URL).append("?access_token=").append(access_token)
			.append("&openid=").append(openId).append("&lang=zh_CN");
		try {
			String result = HttpClientUtil.doGet(getUrl.toString(), "utf-8");
			//logger.info("request success, result = {}.", result);
			userinfo = OAuthUserInfo.analytic(result);
		} catch (Exception e) {
			log.error("请求腾讯获取oauth_access_token失败:" + e.toString(), e);
		}
		return userinfo;
	}
}
