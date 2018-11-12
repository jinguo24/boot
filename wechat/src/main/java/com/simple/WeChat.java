package com.simple;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simple.auth.OAuthUserInfo;
import com.simple.auth.WeChatAuth;
import com.simple.common.JsConfigInfo;
import com.simple.common.WeChatGlobalToken;
import com.simple.common.WeChatInterface;
import com.simple.common.WeChatJsApiTicket;
import com.simple.pay.OrderExcutor;
import com.simple.pay.WeChatOrderDetail;
import com.simple.pay.WeChatPay;
import com.simple.pay.WeChatRefundNoticeInfo;
import com.simple.pay.WeiXinPayConfig;
import com.simple.pay.WeiXinRefundResult;

public class WeChat {
	
	private WeChat() {
		
	}
	
	/**
	 * 微信授权地址
	 * callBackUrl:授权成功后跳转地址，授权成功之后，会在该url后面加上参数code
	 * isAuth: 
	 * 			true-显示授权：弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，
	 * 										即使在未关注的情况下，只要用户授权，也能获取其信息
	 *          false-静默授权：不弹出授权页面，直接跳转，只能获取用户openid
	 */
	public static String getAuthUrl(String appId,String callBackUrl,boolean isAuth,String state) {
		    return WeChatAuth.getAuthUrl(appId, callBackUrl, isAuth, state);
	}
	
	
	/**
	 * 获取用户信息
	 * 如果是静默授权，则只能获取到openId
	 * 如果是显示授权，则调用接口获取用户信息
	 */
	public static OAuthUserInfo getUserInfo(String appId,String secret,String code) {
			return WeChatAuth.getUserInfo(appId, secret, code);
	}
	
	/**
	 * 刷新全局token
	 */
	public static void refreshGloalToken(String appId,String secret,WeChatInterface wcInterface) {
		    WeChatGlobalToken.installAcessToken(appId, secret,wcInterface);
	}
	
	/**
	 * 获取全局token
	 */
	public static String getGloalToken(String appId,String secret,WeChatInterface wcInterface) {
		 return  WeChatGlobalToken.getGlobalAccessToken(appId, secret,wcInterface);
	}
	
	/**
	 * 刷新jsTicket
	 */
	public static void refreshJsTicket(String appId,String secret,WeChatInterface wcInterface) {
		WeChatJsApiTicket.installJsTicket(appId, secret,wcInterface);
	}
	
	/**
	 *  获取jsticket
	  * @Description: js SDK接口注入权限验证配置
	  * @author lining
	  * @date 2015年6月8日 下午3:33:46
	  * @param url 网页的URL，不包含#及其后面部分
	  * @return
	 * @throws Exception 
	 */
	public static JsConfigInfo getJsConfig(String url,String appId,String secret,WeChatInterface wcInterface) throws Exception {
		return WeChatJsApiTicket.getJsConfigInfo(url, appId, secret,wcInterface);
	}
	
	/**
	 * 在微信之外的浏览器调用支付  @link http://blog.csdn.net/goligory/article/details/78392505
	 * 交易类型trade_type=MWEB
	 * 统一下单接口返回支付相关参数给商户后台，如支付跳转url（参数名“mweb_url”），商户通过mweb_url调起微信支付中间页
	 * 
	 * @param redirectUrl[支付完成之后跳转的页面地址]
	 */
	public static String doWapOutPay(String appId,String apiKey,String mchId,String mchName,String goodTypeName,
			String mchOrderNo,int totoFee,String notifyUrl,String openId,String redirectUrl,HttpServletRequest request){
		try {
			return WeChatPay.doWapOutPay(appId, apiKey, mchId, mchName, goodTypeName, mchOrderNo, totoFee, 
					notifyUrl, openId, redirectUrl, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 在微信浏览器调用支付 @link https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7 
	 * @return
	 */
	public static WeiXinPayConfig doWapInPay(String appId,String apiKey,String mchId,String mchName,String goodTypeName,
			String mchOrderNo,int totoFee,String notifyUrl,String openId,HttpServletRequest request){
		try {
			return WeChatPay.doWapInPay(appId, apiKey, mchId, mchName, goodTypeName, mchOrderNo, totoFee, notifyUrl, openId, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * 微信支付成功通知
	 * @param excutor
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String paySuccessNotice(OrderExcutor excutor,HttpServletRequest request, HttpServletResponse response) throws Exception{
		return WeChatPay.paySuccessNotice(excutor, request, response);
	}
	
	/**
	 * 查询订单详情
	 * @param appId
	 * @param apiKey
	 * @param mchId
	 * @param mchOrderNo
	 * @return
	 * @throws Exception
	 */
	public static WeChatOrderDetail getOrderDetail(String appId,String apiKey,String mchId,String mchOrderNo) throws Exception {
		return WeChatPay.getOrderDetail(appId, apiKey, mchId, mchOrderNo);
	}
			
	/**
	 * 申请退款
	 * @param appId
	 * @param apiKey
	 * @param mchId
	 * @param mchOrderNo
	 * @param totelFee 总金额
	 * @param refundFee 退款金额
	 * @param certFile 证书地址
	 * @return
	 * @throws Exception
	 */
	public static WeiXinRefundResult refund(String appId,String apiKey,String mchId,String mchOrderNo,int totelFee,int refundFee,String certFile) throws Exception {
		return WeChatPay.refund(appId, apiKey, mchId, mchOrderNo, totelFee, refundFee, certFile);
	}
	
	/**
	 * 退款结果异步通知
	 * @param apiKey
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static WeChatRefundNoticeInfo getRefundInfo(String apiKey,HttpServletRequest request, HttpServletResponse response)  throws Exception{
		return WeChatPay.getRefundInfo(apiKey, request, response);
	}
	
}
