package com.simple.ali;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.simple.ali.pay.AliPay;
import com.simple.ali.pay.OrderExcuter;

public class Ali {

	/**
	 * 手机网站支付alipay.trade.wap.pay：
     *  对于页面跳转类API，SDK不会也无法像系统调用类API一样自动请求支付宝并获得结果，
     *  而是在接受request请求对象后，为开发者生成前台页面请求需要的完整form表单的html（包含自动提交脚本），
     *  商户直接将这个表单的String输出到http response中即可。
     *  @param appId [商户appid]
     *  @param appKey [私钥 pkcs8格式的 MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDdOVbNE5aif5rV/xV5kKfCPFtR5jZST5+NTHpBBaIYoUEvT3mkK+Y46wvqMBsq3F/yw2qGV4RdJmv/MHwFWce03mnCk3EKLrpZKwqIdq9qLbVo8sHeiL1fUFeeQwFJkK0A4GntJ8b+U9/IYeYTWwC57tm2PjVJ5fFjMT3s9P8nXd/z4inM+P8/m5itK10i7Ff453OLFlXLx/ewRmaeolcGeUr9QKPnSUDvkpGjq7YgkYAQTV8N+yWkpxlrEcRE9h7YgvRorJ3ppBj99MxJ0xMe1qz8xs1Ps1sYXNYfC5J8HRViL5pqoEANzN/ZfTNXcogQA3lQNqAUG3By7GUrI2wXAgMBAAECggEBAIx2t9KGuYJ8G0f9KxmA68O8pw8daShiPiBtisJ2XTHeUZFEmSvQY+GH/Iy59sYEHzf5NE4Qi47JspDZrCWJl/ATuHs/OusIOHIdjhaPpVl5pFH6sXYmxHccgEWQreST5AVGeHuIYiViBBkrb/aiNZfZPIsmfJdrVRVyUqt98/R9y6cp1tS7O2dVnzofiNB74jBnP4WKrMMux5WensrMvXlW95RW3CfIwP7gCUU3AhZGrsh7SWuIduZmaz1HK1ybgpZ9R2sxhH7uqlWL9jK+zGDTPERmBpMFVs6Ys8PJ/VqV0S0t0XExg8x4rVxbWvSVx9wdr8mooJzGEKFGV7iQa0ECgYEA/tBOD/fTEUTfr8cfr57wB+CjdfnGtfxemjh++u1nGnP9A+CVz9PRiq7J8JGP/dmmYy/8iLKXGs9CXZSBDQpVIe0Z0leXl3L+8w7ZZDntQF6DIe38m8lHfvuSOAeR5u3Pc3x9voKo0nRWRWC6acfOcF7d7f+24XpCpLbWNXropQkCgYEA3kEAN6qpXJPwW96+nHY/+B5PdVscLcOT66CjyQwjJLbHrAKd4aS/1YIDkKyGCCwXNE73bHmMiFfClGSW6cBO2bepB3AKLMaaQ3ZwQecRBCR1t6Z2EGVZK77KdJMSaCApYsh9rF52OoAdJBl5I5Qde4Qg6x5YQkVy1cdprRMH8B8CgYEA8jRh3v+7mpCS5mwpjC9RDMvT1BHldZo6+fB1kmISPKy/dO4GuJMoe+AEhyx1Z994LpOwQOcwhgU3mefMeu61WRgcETm9F/HwsGwO6xu8jhDIKHokQJgj42mQ5L0dTBoDtExC4dEkU1bNogh9aIrJiTpDTTRhSxWBPzujhRYdCLECgYBRL+VocEsVqmTu6lsuxHNxq4DTGckdXog2VqP92RUtYCdoSjxqJMh+sdOjkaIUcia30PDJC02usIA/vM7z7G/hUBnKwb61xIathiJTsJiirr7hq0kNjDgapsNF+kOLDdSQZTSjNo5xqEL84OFDgeOTLxznRlpvOvdL6GBN/Nm1eQKBgQDWpB17czcWdSpDKd0BlxARx5pDaHPF6c6rCZrR3FEqL0K+P6HCqtPf3FefUeu40GoO4wJa5H+6x0pwLKvwrUrSlj7O1L4HnwYQqYsCLOpvT5vEw4NlxENgww3yMMimSPVPL05NvQ99gRsbaUoXvsq+xztJvcCJ4WBE/oy/KNm1Lw==]
	 *  @param pubKey [支付宝公钥 MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3QdXD9Vd/gFu+uUkYfG2nqC6X+khLZPyK1a12aYCiR3BtZogsLIWOKcpjKlbUFZesxSi2LyHYCQAUO0VhY8VwCv9vOFK235XgYCFWGWylh11TLPRl1juHZ+WfBiD3TIJd0vFMVA8LZq55RXa8stT0ByM1Jtpbl835CHxFv4DDhIrPJCUZHVla4b/hWLbm7JHmG27TUG0jYIbUVRuDAZB/hVYoCTzlnvykLe1evgBo9d1qaEtna5cLKjT776do2xPNsoGEzrdSzhG1KHFqgJQanbilP7+83r6zwVPETVKongte6iiIcI5muJnJyNsZABwCVLoRy936bn6I7+lE1T9kwIDAQAB]
	 *  @param returnUrl [支付完成返回页面地址]
	 *  @param notifyUrl  [支付结果异步通知接口地址]
	 *  @param orderNo  [商户订单号，必须保证唯一]
	 *  @param total  [支付金额]
	 *  @param title  [支付类型说明]
	 * @throws AlipayApiException 
	 * @throws IOException 
	 */
	public static void doWapPay(String appId,String appKey,String pubKey,String returnUrl,String notifyUrl,String orderNo,BigDecimal total,String title,
			HttpServletRequest request,HttpServletResponse response) throws AlipayApiException, IOException {
		 AliPay.doWapPay(appId, appKey, pubKey, returnUrl, notifyUrl, orderNo, total, title, request, response);
	}
	
	/**
	 * aliPay异步通知
	 * @param request
	 * @param pubKey[支付宝公钥]
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AlipayApiException 
	 */
	public void aliNotifyPay(String pubKey,OrderExcuter orderExcuter,HttpServletRequest request) throws Exception{
		AliPay.aliNotifyPay(pubKey, orderExcuter, request);
	}
	
	/**
	 * 交易查询
	 * @param request
	 * @param pubKey[支付宝公钥]
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AlipayApiException 
	 */
	public static AlipayTradeQueryResponse tradeDetail(String appId,String appKey,String pubKey,String orderNo) throws Exception{
		return AliPay.tradeDetail(appId, appKey, pubKey, orderNo);
	}
	
	/**
	 * 交易退款
	 * @param request
	 * @param pubKey[支付宝公钥]
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws AlipayApiException 
	 */
	public static AlipayTradeRefundResponse  refund(String appId,String appKey,String pubKey,String orderNo,BigDecimal refundFee) throws Exception{
		return AliPay.refund(appId, appKey, pubKey, orderNo, refundFee);
	}
}
