package com.syhd.util.mt2Notifier;

/**
 * 支付结果推送请求数据(商户)
 * 
 * @author puci
 *
 */
public class PayNotifyRequestData extends RequestData {
	private String corpId;
	private String appId;
	private String xiaoyTradeNo;
	private String outTradeNo;
	private String payChannel;// 支付渠道
	private String orderCreateTime;// 订单生成时间
	private String orderExpireTime;// 订单失效时间
	private int totalFee;// 订单总金额（单位：分）
	private int cashFee;// 现金支付金额（单位：分）
	private String attach;// 商户下单附加数据
	private String sign;

	public PayNotifyRequestData(String corpId, String appId,
			String xiaoyTradeNo, String outTradeNo, String payChannel,
			String orderCreateTime, String orderExpireTime, int totalFee,
			int cashFee,String attach) {
		this.setCorpId(corpId);
		this.setAppId(appId);
		this.setXiaoyTradeNo(xiaoyTradeNo);
		this.setOutTradeNo(outTradeNo);
		this.setPayChannel(payChannel);
		this.setOrderCreateTime(orderCreateTime);
		this.setOrderExpireTime(orderExpireTime);
		this.setTotalFee(totalFee);
		this.setCashFee(cashFee);
		this.setAttach(attach);
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getXiaoyTradeNo() {
		return xiaoyTradeNo;
	}

	public void setXiaoyTradeNo(String xiaoyTradeNo) {
		this.xiaoyTradeNo = xiaoyTradeNo;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getOrderExpireTime() {
		return orderExpireTime;
	}

	public void setOrderExpireTime(String orderExpireTime) {
		this.orderExpireTime = orderExpireTime;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getCashFee() {
		return cashFee;
	}

	public void setCashFee(int cashFee) {
		this.cashFee = cashFee;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
