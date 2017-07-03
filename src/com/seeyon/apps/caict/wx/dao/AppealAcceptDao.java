package com.seeyon.apps.caict.wx.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.seeyon.apps.caict.wx.po.AppealAcceptPo;
import com.seeyon.apps.caict.wx.po.BusinessCodePo;
import com.seeyon.apps.caict.wx.po.FilePo;
import com.seeyon.apps.caict.wx.po.HistoryPo;
import com.seeyon.apps.caict.wx.utils.AppealAcceptEnum;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;

public interface AppealAcceptDao {

	/**
	 * 从数据库查询微信端的受理表单
	 * @param appealAcceptEnum--NEW/CALCEL
	 * @return
	 * @throws BusinessException
	 */
	public List<AppealAcceptPo> getAppealData(AppealAcceptEnum appealAcceptEnum) throws BusinessException;
	/**
	 * 根据争议号码 ，查询信息单流程Colsummary 对象<br>
	 * 级联表单动态表查询
	 * @param oaInstanceId
	 * @return
	 * @throws BusinessException
	 */
	public Long getXxColSummary(String userName,String disputeNum) throws BusinessException;
	/**
	 * 根据争议号码 ，查询受理流程Colsummary 对象<br>
	 * 级联表单动态表查询
	 * @param oaInstanceId
	 * @return
	 * @throws BusinessException
	 */
	public Long getSlColSummary(String userName,String disputeNum) throws BusinessException;
	/**
	 * 根据争议号码 ，查询办理流程Colsummary 对象<br>
	 * 级联表单动态表查询
	 * @param oaInstanceId
	 * @return
	 * @throws BusinessException
	 */
	public Long getBlColSummary(String userName,String disputeNum) throws BusinessException;
	/**
	 * 根据争议号码和用户姓名 ，查询调解流程Colsummary 对象<br>
	 * @param oaInstanceId
	 * @return
	 * @throws BusinessException
	 */
	public Long getTjColSummary(String userName,String disputeNum) throws BusinessException;
	/**
	 * 修改流程状态
	 * @param state  状态
	 * @param oaInstanceId  OA实例号
	 * @param tabelName	表名
	 * @throws BusinessException
	 */
	public void updateProcessFlag(String state, String oaInstanceId,String tabelName) throws BusinessException;
	/**
	 * 流程发起，更新数据表状态
	 * @param state
	 * @param id
	 * @param serialNumber
	 * @param b
	 * @throws BusinessException
	 */
	public void updateProcessFlag4Start(String state, AppealAcceptPo appeal,String serialNumber,boolean b,String type) throws BusinessException;
	
	/**
	 * 判断OA实例号在哪张表中存在
	 * @param oaInstanceId
	 * @return
	 * @throws BusinessException
	 */
	public String getTabelName(String oaInstanceId,String type) throws BusinessException;
	/**
	 * 获取网站上传的附件
	 * @param Id
	 * @return
	 * @throws BusinessException
	 */
	public List<FilePo> getAttachement4Web(String Id) throws BusinessException;
	/**
	 * 根据表单中附件字段的ID获取所有附件url
	 * @param Id
	 * @return
	 * @throws BusinessException
	 */
	public List<FilePo> getAllFileUrlByReference(Long id) throws BusinessException;
	/**
	 * 判断流程数据来自哪张表，是微信还是网站
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public boolean isExistTable(String id,String type) throws BusinessException;
	/**
	 * 手动发起申诉受理的数据，进行数据保存，用户可以从微信或者网站查询到相关信息
	 * @param aap
	 * @throws BusinessException
	 */
	public void saveAppealData(AppealAcceptPo aap) throws BusinessException;
	/**
	 * 获取调解数据
	 * @param appealAcceptEnum NEW
	 * @return
	 * @throws BusinessException
	 */
	public List<AppealAcceptPo> getMediateData(AppealAcceptEnum appealAcceptEnum) throws BusinessException;
	/**
	 * 修改已发流程表单中的附件字段，赋予ID与其他表关联
	 * @param id
	 * @throws BusinessException
	 */
	public long updateFormAttachmentColumn(Long id,String tableName,String tableFiel) throws BusinessException;
	/**
	 * 获取对应流程的申诉日期字段值
	 * @param i
	 * @param formId
	 * @return
	 * @throws BusinessException 
	 */
	public AppealAcceptPo getAppealDate(String key,Long formId) throws BusinessException;
	/**
	 * 流程发起保存申诉信息到底表中
	 * @param appeal
	 * @throws BusinessException
	 */
	public Long saveAppealData2DB(AppealAcceptPo appeal) throws BusinessException;
	/**
	 * 根据所属组id获取人员id
	 * @param groupId   所属组ids
	 * @return
	 * @throws SQLException
	 */
	public List<String>  getAakForLeaveId(String groupId) throws BusinessException;
	/**
	 * 根据运营商ID获取该运营商未分配的数据
	 * @param companyId
	 * @return
	 * @throws BusinessException
	 */
	public List<Long> getDbData4AssigningTask(Long companyId,FlipInfo fpi) throws BusinessException;
	/**
	 * 获取未分配的任务
	 * @param companyId
	 * @return
	 * @throws BusinessException
	 */
	public List<AppealAcceptPo> getCountDbData4AssigningTask() throws BusinessException;
	/**
	 * 获取运营商ID,子类除外
	 * @return
	 * @throws BusinessException
	 */
	public List<Long> getParentEnumId() throws BusinessException;
	/**
	 * 根据底表未分配任务ID更新人员ID
	 * @param taskIds
	 * @param personId
	 * @throws BusinessException
	 */
	public void updateDbPersonId(Long taskId,Long personId,String code) throws BusinessException;
	/**
	 * 获取当前流程表单中的运营商
	 * @param formRecordId
	 * @return
	 * @throws BusinessException
	 */
	public Long getFormCompanyId(String formRecordId,String tableName,String companyField) throws BusinessException;
	/**
	 * 根据模板ID获取某个人收到过的事项总数
	 * @param memberId
	 * @param templeteId
	 * @return
	 * @throws BusinessException
	 */
	public int getHandleFormCount(String memberId,String templeteId) throws BusinessException;
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
	 * 从底表获取满足条件的数据进行自动发起流程
	 * @return
	 * @throws BusinessException
	 */
	public List<AppealAcceptPo> getTask4DB() throws BusinessException;
	/**
	 * 根据企业ID获取企业人员
	 * @param qyId 主投企业一级ID
	 * @param xnId 当b为true时--部门ID，当b为false时--主投企业二级ID
	 * @param b  true--省中心，false--虚拟运营商
	 * @return
	 * @throws BusinessException
	 */
	public Long getXnqyPersonId(Long qyId,Long xnId,boolean b) throws BusinessException;
	/**
	 * 获取申诉记录的处理人ID
	 * @param userName
	 * @param userPhone
	 * @param disputeNum
	 * @return
	 * @throws BusinessException
	 */
	public Long getProcessorId(String userName,String userPhone,String disputeNum) throws BusinessException;
	/**
	 * 获取案件拆分对应的回复人员ID
	 * @param tableName
	 * @param formId
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Long> getMemberId4CaseSplit(String tableName,String formId) throws BusinessException;
	/**
	 * 根据省份ID获取对应的认定员
	 * @param areaId 所属地区ID
	 * @return
	 * @throws BusinessException
	 */
	public Long getRdPersonId4Province(String areaId) throws BusinessException;
	/**
	 * 根据争议号码与用户姓名查找最近一条申诉记录
	 * @param disputeNum
	 * @param userName
	 * @return
	 * @throws BusinessException
	 */
	public BusinessCodePo getBusinessCode(String disputeNum,String userName) throws BusinessException;
	/**
	 * 根据用户姓名和争议号码查找底表中的历史记录
	 * @param disputeNum
	 * @param userName
	 * @return
	 * @throws BusinessException
	 */
	public List<HistoryPo> getHistoryData(String disputeNum,String userName) throws BusinessException;
}

