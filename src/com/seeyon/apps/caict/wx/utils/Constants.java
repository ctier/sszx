package com.seeyon.apps.caict.wx.utils;

import com.seeyon.ctp.common.AppContext;

public class Constants {

	public static final String SHZ 				= "审核中";
	public static final String SHWTG 			= "审核未通过";
	public static final String BLZ 				= "办理中";
	public static final String YWJ 				= "已完结";
	public static final String YSL 				= "已受理";
	public static final String YCS 				= "已撤诉";
	public static  final String MSG_CANCEL 		= "用户撤诉,该流程自动终止！";
	public static final String SS				= "申诉";
	public static final String TJ				= "调解";
	public static final String CS				= "撤诉";
	public static final String RDGROUP			= "认定组";
	public static final String SHGROUP			= "审核组";
	public static final String BLGROUP			= "办理组";
	public static final String TJGROUP			= "调解组";
	public static final String WEBURL			="http://10.3.65.40:8080/elec_sys_war";
	public static final String CALLCENTERURL	="http://192.168.1.104/";
	public static final String WORKTIME			= String.valueOf(60*8*5);
	public static final String CHONGQING		= "重庆";
	public static final String SHANGHAI			= "上海";
	public static final String XINJIANG			= "新疆";
	public static final String JIANGSU			= "江苏";
	public static final String FUJIAN			= "福建";
	public static final String DIR			    = "d:/";
	public static final String SLFORMMAIN = AppContext.getSystemProperty("appealAccept.sformfield.formmain");
	public static final String BLFORMMAIN = AppContext.getSystemProperty("appealAccept.bformField.formmain");
	public static final String TJFORMMAIN = AppContext.getSystemProperty("appealAccept.tformField.formmain");
	
}
