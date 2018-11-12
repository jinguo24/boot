package com.simple.pay;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;

import com.alibaba.fastjson.JSON;
import com.simple.WeChatConstant;
import com.simple.util.AESEncrypt;
import com.simple.util.Base64;
import com.simple.util.HttpClientUtil;
import com.simple.util.IpUtil;
import com.simple.util.PrimaryKeyUtil;
import com.simple.util.XMLUtil;
/**
 * 微信支付
 * @author zhengfy1
 * @link 微信浏览器之外的支付 https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=15_4
 * 
 */
public class WeChatPay {
	
	private static final Log log = LogFactory.getLog(WeChatPay.class);
	
	/**
	 * 创建微信订单
	 * 统一下单 
	 * @param apiKey[apiKey],mchId[商户编号],mchName[商户名称],goodTypeName[商品分类名称],
	 *        mchOrderNo[商户系统订单号],totoFee[金额，分],clientIp[客户端访问ip],
	 *        notifyUrl[支付成功回调地址，不能带参数,接收微信消息]
	 * 
	 * 
	 * @throws IOException 
	 * @throws JDOMException 
	 * @link https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_1
	 */
	private static WeChatOrderResult createWechatOrder(String appId,String apiKey,String mchId,String mchName,String goodTypeName,
			String mchOrderNo,int totoFee,String clientIp,String notifyUrl,String traceType,String openId) throws Exception{
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", appId);
		parameters.put("mch_id", mchId);
		parameters.put("nonce_str", PrimaryKeyUtil.getRandomString());
		parameters.put("body", mchName+"-"+goodTypeName);
		parameters.put("out_trade_no", mchOrderNo);
		parameters.put("total_fee", String.valueOf(totoFee));
		parameters.put("spbill_create_ip",clientIp);
		parameters.put("notify_url", notifyUrl);
		parameters.put("trade_type", traceType);
		parameters.put("openid", openId);
		String sign = PayUtil.createPaySign(parameters,apiKey);
		parameters.put("sign", sign);
		String requestXML = PayUtil.getRequestXml(parameters);
		String result =HttpClientUtil.doPost(WeChatConstant.CREATOR_ORDER_URL, requestXML, "utf-8");
		Map<String, String> map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
		return JSON.parseObject(JSON.toJSONString(map),WeChatOrderResult.class);
	}
	
	/**
	 * 在微信浏览器调用支付 @link https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7
	 */
	public static WeiXinPayConfig doWapInPay(String appId,String apiKey,String mchId,String mchName,String goodTypeName,
			String mchOrderNo,int totoFee,String notifyUrl,String openId,HttpServletRequest request) throws Exception{
		//微信下单
		WeChatOrderResult orderResult = createWechatOrder(appId, apiKey, mchId, mchName, goodTypeName, 
				mchOrderNo, totoFee, IpUtil.getClientIp(request), notifyUrl, "JSAPI", openId);
		if ( null != orderResult) {
			return getPayConfig(apiKey, orderResult, request);
		}
		return null;
	}
	
	private static WeiXinPayConfig getPayConfig(String apiKey,WeChatOrderResult result,HttpServletRequest request) {
		SortedMap<Object,Object> params = new TreeMap<Object,Object>();
        params.put("appId", result.getAppid());
        String timeStamp = Long.toString(new Date().getTime());
        params.put("timeStamp", timeStamp);
        String nonceStr = PrimaryKeyUtil.getRandomString();
        params.put("nonceStr", nonceStr);
        params.put("package", "prepay_id="+result.getPrepay_id());
        params.put("signType", "MD5");
        String paySign =  PayUtil.createPaySign(params,apiKey);
        params.put("packageValue", "prepay_id="+result.getPrepay_id());    //这里用packageValue是预防package是关键字在js获取值出错
        params.put("paySign", paySign);                                                          //paySign的生成规则和Sign的生成规则一致
        String userAgent = request.getHeader("user-agent");
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);
        params.put("agent", new String(new char[]{agent}));//微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
        
        WeiXinPayConfig wc = new WeiXinPayConfig();
        wc.setAppId(result.getAppid());
        wc.setTimeStamp(timeStamp);
        wc.setNonceStr(nonceStr);
        wc.setLpackage("prepay_id="+result.getPrepay_id());
        wc.setSignType("MD5");
        wc.setPackageValue("prepay_id="+result.getPrepay_id());
        wc.setPaySign(paySign);
        wc.setAgent(new String(new char[]{agent}));
        return wc;
	}
	
	/**
	 * 在微信之外的浏览器调用支付  @link http://blog.csdn.net/goligory/article/details/78392505
	 * 交易类型trade_type=MWEB
	 * 统一下单接口返回支付相关参数给商户后台，如支付跳转url（参数名“mweb_url”），商户通过mweb_url调起微信支付中间页
	 * 
	 * @param redirectUrl[支付完成之后跳转的页面地址]
	 */
	public static String doWapOutPay(String appId,String apiKey,String mchId,String mchName,String goodTypeName,
			String mchOrderNo,int totoFee,String notifyUrl,String openId,String redirectUrl,HttpServletRequest request) throws Exception{
		//微信下单
		WeChatOrderResult orderResult = createWechatOrder(appId, apiKey, mchId, mchName, goodTypeName, mchOrderNo,
				totoFee, IpUtil.getClientIp(request), notifyUrl, "MWEB", openId);
		if ( null != orderResult) {
			String return_code = orderResult.getReturn_code();  
	        String return_msg = orderResult.getReturn_msg();  
	        if (!PayUtil.codeIsOK(return_code)) {  
	            throw new RuntimeException(return_msg);  
	        }  
	        String result_code = orderResult.getResult_code();  
	        if (!PayUtil.codeIsOK(result_code)) {  
	            throw new RuntimeException(return_msg);  
	        }  
	        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回  
	        String prepay_id = orderResult.getPrepay_id();  
	        String mweb_url = orderResult.getMweb_url(); 
	        if (StringUtils.isBlank(redirectUrl)) {
	        	return mweb_url;
	        }
	        return mweb_url+"&redirect_url="+URLEncoder.encode(redirectUrl,"utf-8"); 
		}
		return null;
	}
	
	private static String getRequestString(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			StringBuilder result = new StringBuilder();
			br = request.getReader();
			for (String line; (line=br.readLine())!=null;) {
				if (result.length() > 0) {
					result.append("\n");
				}
				result.append(line);
			}
			return result.toString();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 支付成功，微信异步通知
	 * @param excutor
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String paySuccessNotice(OrderExcutor excutor,HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String result = getRequestString(request);
			Map<Object, Object> params = XMLUtil.doXMLParse(result.toString());
			String result_code  = (String) params.get("result_code");  
		    String return_msg =  (String) params.get("return_msg");
		    if (!("SUCCESS").equals(result_code)) {
		    	throw new RuntimeException("paySuccessNotice error :"+return_msg);
		    }
			String appid  = (String) params.get("appid");  
	//	      //商户号  
		      String mch_id  = (String) params.get("mch_id");  
		      
	//	      String openId      = params.get("openid");  
	//	      //交易类型  
	//	      String trade_type      = params.get("trade_type");  
	//	      //付款银行  
	//	      String bank_type      = params.get("bank_type");  
	//	      // 总金额  
	//	      String total_fee     = params.get("total_fee");  
	//	      //现金支付金额  
	//	      String cash_fee     = params.get("cash_fee");  
	//	      // 微信支付订单号  
		      String transaction_id      = (String) params.get("transaction_id");  
	//	      // 商户订单号  
		      String out_trade_no      = (String) params.get("out_trade_no");  
	//	      // 支付完成时间，格式为yyyyMMddHHmmss  
	//	      String time_end      = params.get("time_end");  
		        /////////////////////////////以下是附加参数///////////////////////////////////  
		        String attach      = (String) params.get("attach");  
	//	      String fee_type      = params.get("fee_type");  
	//	      String is_subscribe      = params.get("is_subscribe");  
	//	      String err_code      = params.get("err_code");  
	//	      String err_code_des      = params.get("err_code_des"); 
	// 		注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态 ,避免已经成功、关闭、退款的订单被再次更新
		    boolean ispass = excutor.executeAfterNotice(transaction_id,out_trade_no,appid,mch_id);   
		    if (ispass) {  
                //更新订单信息  
                log.warn("更新订单信息:"+attach);  
                //发送通知等  
                Map<String, String> xml = new HashMap<String, String>();  
                xml.put("return_code", "SUCCESS");  
                xml.put("return_msg", "OK");  
                return PayUtil.toXml(xml);  
            }   
		    return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 订单详情
	 * @param appId
	 * @param apiKey
	 * @param mchId
	 * @param mchOrderNo
	 * @return
	 * @throws Exception
	 */
	public static WeChatOrderDetail getOrderDetail(String appId,String apiKey,String mchId,String mchOrderNo) throws Exception {
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", appId);
		parameters.put("mch_id", mchId);
		//parameters.put("transaction_id", transactionId);
		parameters.put("out_trade_no", mchOrderNo);
		parameters.put("mch_id", mchId);
		parameters.put("nonce_str", PrimaryKeyUtil.getRandomString());
		String sign = PayUtil.createPaySign(parameters,apiKey);
		parameters.put("sign", sign);
		String requestXML = PayUtil.getRequestXml(parameters);
		String result =HttpClientUtil.doPost(WeChatConstant.CREATOR_ORDER_URL, requestXML, "utf-8");
		Map<String, String> map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
		return JSON.parseObject(JSON.toJSONString(map),WeChatOrderDetail.class);
	}
	
	/**
	 * 退款
	 * @param appId
	 * @param apiKey
	 * @param mchId
	 * @param mchOrderNo
	 * @param totelFee
	 * @param refundFee
	 * @param certFile
	 * @return
	 * @throws Exception
	 */
	public static WeiXinRefundResult refund(String appId,String apiKey,String mchId,String mchOrderNo,int totelFee,int refundFee,String certFile) throws Exception {
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", appId);
		parameters.put("mch_id", mchId);
		parameters.put("nonce_str", PrimaryKeyUtil.getRandomString());
		parameters.put("out_trade_no", mchOrderNo);
		parameters.put("out_refund_no", mchOrderNo);
		parameters.put("total_fee", String.valueOf(totelFee));
		parameters.put("refund_fee", String.valueOf(refundFee));
		parameters.put("op_user_id", mchId);
		String sign = PayUtil.createPaySign(parameters,apiKey);
		parameters.put("sign", sign);
		String requestXML = PayUtil.getRequestXml(parameters);
		String result =ClientCustomSSL.doRefund(mchId,certFile, WeChatConstant.REFUND_URL, requestXML);
		//String result =WeixinRefundSSL.refund("https://api.mch.weixin.qq.com/secapi/pay/refund", "1377344802", "D://apiclient_cert.p12", requestXML);
		Map<String, String> map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
		return JSON.parseObject(JSON.toJSONString(map),WeiXinRefundResult.class);
	}
	
	public static WeChatRefundNoticeInfo getRefundInfo(String apiKey,HttpServletRequest request, HttpServletResponse response)  throws Exception{
			String result = getRequestString(request);
			Map<Object, Object> params = XMLUtil.doXMLParse(result.toString());
			String result_code  = (String) params.get("result_code");  
			String return_msg  = (String) params.get("return_msg");  
			if (!("SUCCESS".equals(result_code))) {
				throw new RuntimeException("refundinfo error:"+return_msg);
			}
			String appid  = (String) params.get("appid");  
	        //商户号  
	        String mch_id  = (String) params.get("mch_id");  
	        String nonce_str  = (String) params.get("nonce_str");  
	        String req_info = (String) params.get("req_info"); 
	        String info = Base64.getFromBase64(req_info);
	        String content = AESEncrypt.Aes256Decode(info, apiKey);
	        return JSON.parseObject(content,WeChatRefundNoticeInfo.class);
	}
}
