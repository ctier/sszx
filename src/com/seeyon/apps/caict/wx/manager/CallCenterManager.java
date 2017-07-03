package com.seeyon.apps.caict.wx.manager;

import java.util.Map;

import com.seeyon.apps.caict.wx.po.EfaxPo;
import com.seeyon.apps.caict.wx.po.TelAcceptPo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;

public interface CallCenterManager {

	/**
	 * 将电子传真保存到OA服务器
	 * @param ep
	 * @return
	 * @throws BusinessException
	 */
	public void saveToLocal(EfaxPo ep) throws BusinessException;
	/**
	 * 上传附件
	 * @param senderLoginName 上传人登录账号
	 * @param fileName 文件名称
	 * @return
	 * @throws BusinessException
	 */
	public Long uploadAttachement(String senderLoginName,String fileName) throws BusinessException;
	/**
	 * 自动发送电子传真流程入口
	 * @throws BusinessException
	 */
	public void sendFaxFileFlow() throws BusinessException;
	/**
	 * 自动发送电子传真流程
	 * @param ep
	 * @throws BusinessException
	 */
	public void sendFaxFileFlow(EfaxPo ep) throws BusinessException;
	/**
	 * 保存电话受理数据
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public void saveTelData(TelAcceptPo tap) throws BusinessException;
	/**
	 * 获取语音留言数据
	 * @param flipInfo
	 * @param query
	 * @return
	 * @throws BusinessException
	 */
	public FlipInfo getVoiceData(FlipInfo flipInfo,Map<String, String> query) throws BusinessException;
	/**
	 * 定时获取呼叫中心数据库语音留言数据入口
	 * @throws BusinessException
	 */
	public void transVoiceFlow() throws BusinessException;
	/**
	 * 修改语音留言状态
	 * @param params
	 * @throws BusinessException
	 */
	public int updateVoiceState(Map<String,Object> params) throws BusinessException;
}
