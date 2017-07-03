package com.seeyon.apps.caict.wx.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.seeyon.apps.caict.wx.po.AppealAcceptPo;
import com.seeyon.apps.caict.wx.utils.AppealAcceptEnum;
import com.seeyon.ctp.common.exceptions.BusinessException;

public interface AppealAcceptManager {
	
	/**
	 * 定时保存网站、微信申诉数据到底表，包含申诉、调解、撤诉的信息
	 * @throws BusinessException
	 */
	public void saveAppealData2DB() throws BusinessException;
	/**
	 * 获取微信&网站申诉表数据
	 * @param appealAcceptEnum  NEW
	 * @return
	 * @throws BusinessException
	 */
	public List<AppealAcceptPo> getAppealData(AppealAcceptEnum appealAcceptEnum) throws BusinessException;
	/**
	 * 获取调解数据
	 * @param appealAcceptEnum NEW
	 * @return
	 * @throws BusinessException
	 */
	public List<AppealAcceptPo> getMediateData(AppealAcceptEnum appealAcceptEnum) throws BusinessException;
	/**
	 * 流程自动发起更新数据状态
	 * @param state 流程发起后要修改为的状态READ
	 * @param id 源数据ID
	 * @param serialNumber 流水号
	 * @param type 申诉类型：申诉/调解
	 * @return
	 * @throws BusinessException
	 */
	public void updateProcessFlag4Start(String state,AppealAcceptPo appeal,String serialNumber,String type) throws BusinessException;
	/**
	 * 根据OA实例号 ，查询Colsummary 对象<br>
	 * 级联表单动态表查询
	 * @param oaInstanceId
	 * @return
	 * @throws BusinessException
	 */
	public List<Map<String,Long>> getColSummary(String userName,String oaInstanceId) throws BusinessException;
	/**
	 * 更新流程状态
	 * @param state
	 * @param oaInstanceId
	 * @param tabelName
	 * @throws BusinessException
	 */
	public void updateProcessFlag(String state, String oaInstanceId,String tabelName) throws BusinessException;
	/**
	 * 判断OA实例号在哪张表中存在
	 * @param oaInstanceId
	 * @return 
	 * @throws BusinessException
	 */
	public String getTabelName(String oaInstanceId,String type) throws BusinessException;
	/**
	 * 手动发起申诉受理的数据，进行数据保存，用户可以从微信或者网站查询到相关信息
	 * @param aap
	 * @throws BusinessException
	 */
	public void saveAppealData(AppealAcceptPo aap) throws BusinessException;
	/**
	 * 发送短信
	 * @param obj
	 * @return
	 * @throws BusinessException
	 */
	public String transSendSMS(Map<String, String> obj) throws BusinessException;
	/**
	 * 用户撤诉，终止流程
	 * @param disputeNum
	 * @param oaInstanceNum
	 * @throws BusinessException
	 */
	public void stopOAflow(String userName,String disputeNum,String oaInstanceNum) throws BusinessException;
	/**
	 * 请假审批流程结束，将请假时间写入到请休假人员信息底表
	 * @param memberId
	 * @param code
	 * @param startTime
	 * @param endTime
	 * @throws BusinessException
	 */
	public void updateLeaveInfo2DB(Long memberId,String code,Date startTime,Date endTime) throws BusinessException;
	/**
	 * 根据数据ID更新自动触发流程后的状态
	 * @param dataId
	 * @throws BusinessException
	 */
	public void updateDbState(String dataId) throws BusinessException;
	/**
	 * 定时发送已分配好的任务
	 * @param appeal
	 * @throws BusinessException
	 */
	public void sendAppealDataFlow(AppealAcceptPo appeal) throws BusinessException;
	/**
	 * 定时发送任务
	 * @throws BusinessException
	 */
	public void sendTaskFlow() throws BusinessException;
	/**
	 * 得到分配任务最少的认定员ID
	 * @param area 所属地区
	 * @return
	 * @throws BusinessException
	 */
	public String getRdPersonId(String area) throws BusinessException;
	/**
	 * 定时自动分配任务
	 * @throws BusinessException
	 */
	public void transAutoAssigningTask() throws BusinessException;
}
