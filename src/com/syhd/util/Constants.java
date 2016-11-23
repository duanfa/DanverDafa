package com.syhd.util;

public class Constants {
	public static final String WEB_SITE_URL = "";
	public static final String ENCODE = "UTF-8";// 编码
	public static final String STATU_PRE_PAY = "prePay";// 待支付状态
	public static final String STATU_ERR = "error";// 待支付状态
	public static final String STATU_PAY_SUCCESS = "paySuccess";// 支付成功状态
	public static final String STATU_NOTIFY_SUCCESS = "notifySuccess";// 支付成功状态
	public static final String STATU_PAY_FAIL = "payFail";// 支付失败状态
	public static final String STATU_REFOUND = "refund";// 退款状态
	public static final String STATU_REFOUND_SUCCESS = "refundSuccess";// 退款成功状态
	public static final String STATU_REFOUND_FAIL = "refundFail";// 退款失败状态
	public static final String STATU_CANCEL = "cancel";// 待取消状态
	public static final String STATU_CANCEL_SUCCESS = "cancelSuccess";// 取消成功状态
	public static final String STATU_CANCEL_FAIL = "cancelFail";// 取消失败状态
	public static final int ORDER_EXPIRE_TIME = 2;

	public final static String TOP_STORE = "/syhd_storage/";// 存储顶级目录

	public final static String PIC_STORE = TOP_STORE + "pic/";// img存储目录（LOGO/APK/PIC）

	public static final int OUT_TYPE_SDK = 1;// 主动查询类型：支付插件
	public static final int OUT_TYPE_CP = 2;// 主动查询类型：CP商户

	public static final int MAX_INFINITE_NUM = 999999999;// 无限大
	public static final Integer PAGE_SIZE = 10	;
}
