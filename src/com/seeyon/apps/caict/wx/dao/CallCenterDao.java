package com.seeyon.apps.caict.wx.dao;

import java.util.List;
import java.util.Map;

import com.seeyon.apps.caict.wx.po.EfaxPo;
import com.seeyon.apps.caict.wx.po.TelAcceptPo;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;

public interface CallCenterDao {

	/**
	 * 获取当天的传真文件信息
	 * @return
	 * @throws BusinessException
	 */
	public List<EfaxPo> getEfax() throws BusinessException;
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
	 * 从呼叫中心的数据库中获取当天的语音留言记录
	 * @return
	 * @throws BusinessException
	 */
	public List<EfaxPo> getVoiceData() throws BusinessException;
	/**
	 * 获取OA表中的语音留言数据ID
	 * @return
	 * @throws BusinessException
	 */
	public List<String> getVoiceIdData() throws BusinessException;
	/**
	 * 保存语音留言数据
	 * @param dataList
	 * @throws BusinessException
	 */
	public void saveVoiceData(List<EfaxPo> dataList) throws BusinessException;
	/**
	 * 更新语音留言状态
	 * @param voiceId
	 * @throws BusinessException
	 */
	public int updateVoiceState(String voiceId) throws BusinessException;
}
