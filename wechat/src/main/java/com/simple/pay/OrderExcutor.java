package com.simple.pay;

public interface OrderExcutor {

	/**
	 * 是否向微信发送更新说明,重复的订单、订单状态不对的不要发送
	 * @param wcTraceId : 微信订单号
	 * @param mchOrderNo: 商户订单号
	 * @return 
	 */
	public boolean executeAfterNotice(String wcTraceId,String mchOrderNo,String appId,String mchId);
}
