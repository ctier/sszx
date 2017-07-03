package com.seeyon.apps.caict.wx.utils;

import java.util.UUID;

import cn.poweru.api.sms.webservice.SMS;
import cn.poweru.api.sms.webservice.SMSPortType;

public class SmsUtil {

	/**
	 * 生成16位唯一短信ID
	 * @return
	 */
	public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }
	/**
	 * 发送短信
	 * @param phone  手机号
	 * @param content 短信内容
	 * @return
	 */
	public static String sendSms(String phone,String content){
		
		SMSPortType smsHttpPort = new SMS().getSMSHttpPort();
		StringBuffer xml = new StringBuffer();
		String num = SmsUtil.getOrderIdByUUId();
		xml.append("<?xml version=\"").append("1.0\" ").append("encoding=\"").append("utf-8").append("\"?>");
		xml.append("<SMS type=\"send\">");
		xml.append("<Message SmsID=\"").append("UE").append(num).append(0).append("\" ");
		xml.append("Bid=\"").append("system").append("\" ");
		xml.append("RecvNum=\"").append(phone).append("\" ");
		xml.append("Content=\"").append(content).append("\"");
		xml.append(" SendLevel=\"").append(1).append("\"/>");
		xml.append("</SMS>");
		String result = smsHttpPort.addSMSList("POWERU-SMS", xml.toString());
		return result;
	}
}
