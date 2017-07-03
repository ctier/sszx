package com.seeyon.apps.caict.wx.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.joinwork.bpm.engine.wapi.WorkflowBpmContext;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.sendflow.manager.SendFlowManager;
import com.seeyon.apps.caict.wx.dao.AppealAcceptDao;
import com.seeyon.apps.caict.wx.po.AppealAcceptPo;
import com.seeyon.apps.caict.wx.po.BusinessCodePo;
import com.seeyon.apps.caict.wx.po.FilePo;
import com.seeyon.apps.caict.wx.po.HistoryPo;
import com.seeyon.apps.caict.wx.utils.AppealAcceptEnum;
import com.seeyon.apps.caict.wx.utils.Constants;
import com.seeyon.apps.caict.wx.utils.SmsUtil;
import com.seeyon.apps.colCube.event.enums.CollaborationCubeEnums;
import com.seeyon.apps.collaboration.bo.DateSharedWithWorkflowEngineThreadLocal;
import com.seeyon.apps.collaboration.dao.ColDao;
import com.seeyon.apps.collaboration.enums.ColHandleType;
import com.seeyon.apps.collaboration.event.CollaborationStopEvent;
import com.seeyon.apps.collaboration.manager.Col4IColCubeEvent;
import com.seeyon.apps.collaboration.manager.ColManager;
import com.seeyon.apps.collaboration.po.ColSummary;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.GlobalNames;
import com.seeyon.ctp.common.ModuleType;
import com.seeyon.ctp.common.appLog.AppLogAction;
import com.seeyon.ctp.common.appLog.manager.AppLogManager;
import com.seeyon.ctp.common.authenticate.domain.User;
import com.seeyon.ctp.common.constants.ApplicationCategoryEnum;
import com.seeyon.ctp.common.content.affair.AffairManager;
import com.seeyon.ctp.common.content.affair.constants.StateEnum;
import com.seeyon.ctp.common.content.affair.constants.SubStateEnum;
import com.seeyon.ctp.common.content.comment.Comment;
import com.seeyon.ctp.common.content.comment.CommentManager;
import com.seeyon.ctp.common.content.mainbody.MainbodyType;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.filemanager.manager.AttachmentManager;
import com.seeyon.ctp.common.filemanager.manager.FileManager;
import com.seeyon.ctp.common.po.affair.CtpAffair;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.ctp.common.po.filemanager.Attachment;
import com.seeyon.ctp.common.po.filemanager.V3XFile;
import com.seeyon.ctp.common.processlog.ProcessLogAction;
import com.seeyon.ctp.common.processlog.manager.ProcessLogManager;
import com.seeyon.ctp.common.supervise.enums.SuperviseEnum;
import com.seeyon.ctp.common.supervise.manager.SuperviseManager;
import com.seeyon.ctp.event.EventDispatcher;
import com.seeyon.ctp.form.modules.serialNumber.SerialNumberManager;
import com.seeyon.ctp.form.service.FormManager;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.util.DateUtil;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.UUIDLong;
import com.seeyon.ctp.util.annotation.AjaxAccess;
import com.seeyon.ctp.workflow.exception.BPMException;
import com.seeyon.ctp.workflow.wapi.WorkflowApiManager;
import com.seeyon.v3x.worktimeset.manager.WorkTimeManager;

public class AppealAcceptManagerImpl implements AppealAcceptManager {
	private static final Logger LOGGER = Logger.getLogger(AppealAcceptManagerImpl.class);
	private SerialNumberManager     	 serialNumbermanager = (SerialNumberManager)AppContext.getBean("serialNumberManager");
	private static EnumManager 			 enumManagerNew = (EnumManager) AppContext.getBean("enumManagerNew");
	private AppealAcceptDao  			 appealAcceptDao;
	private OrgManager 					 orgManager;
	private AffairManager 				 affairManager;
	private SuperviseManager   			 superviseManager;
	private CommentManager               ctpCommentManager;
	private AppLogManager                appLogManager;
    private ProcessLogManager            processLogManager;
    private FormManager                  formManager;
    private WorkflowApiManager           wapi;
    private ColDao                       colDao;
    private ColManager					 colManager;
    private FileManager					 fileManager;
    private AttachmentManager			 attachmentManager;
    private WorkTimeManager 			 workTimeManager;
    private SendFlowManager 			 sendFlowManager;
    private List<String>				sList;
    private List<String>				companyList;
	public void setCompanyList(List<String> companyList) {
		this.companyList = companyList;
	}
	public void setsList(List<String> sList) {
		this.sList = sList;
	}
	public static void setEnumManagerNew(EnumManager enumManagerNew) {
		AppealAcceptManagerImpl.enumManagerNew = enumManagerNew;
	}
	public void setSendFlowManager(SendFlowManager sendFlowManager) {
		this.sendFlowManager = sendFlowManager;
	}
	public void setWorkTimeManager(WorkTimeManager workTimeManager) {
		this.workTimeManager = workTimeManager;
	}
	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	public void setColManager(ColManager colManager) {
		this.colManager = colManager;
	}
	public void setWapi(WorkflowApiManager wapi) {
		this.wapi = wapi;
	}
	public void setSuperviseManager(SuperviseManager superviseManager) {
		this.superviseManager = superviseManager;
	}
	public void setAffairManager(AffairManager affairManager) {
		this.affairManager = affairManager;
	}
	public void setAppealAcceptDao(AppealAcceptDao appealAcceptDao) {
		this.appealAcceptDao = appealAcceptDao;
	}
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	public void setCtpCommentManager(CommentManager ctpCommentManager) {
		this.ctpCommentManager = ctpCommentManager;
	}
	public void setAppLogManager(AppLogManager appLogManager) {
		this.appLogManager = appLogManager;
	}
	public void setProcessLogManager(ProcessLogManager processLogManager) {
		this.processLogManager = processLogManager;
	}
	public void setFormManager(FormManager formManager) {
		this.formManager = formManager;
	}
	public void setColDao(ColDao colDao) {
		this.colDao = colDao;
	}

	@Override
	public void saveAppealData2DB() throws BusinessException {
		LOGGER.info("定时任务调用接口saveAppealData2DB 开始");
		//获取两张申诉表申诉数据
		List<AppealAcceptPo> aList = this.getAppealData(AppealAcceptEnum.NEW);
		//获取两张调解表申请调解数据
		List<AppealAcceptPo> tList = this.getMediateData(AppealAcceptEnum.NEW);
		//获取两张申诉表撤诉数据
		List<AppealAcceptPo> cList = this.getAppealData(AppealAcceptEnum.CANCEL);
		List<AppealAcceptPo> dataList = new ArrayList<AppealAcceptPo>();
		if(Strings.isNotEmpty(aList)){
			LOGGER.info("申诉流程数量："+aList.size());
			dataList.addAll(aList);
		}
		if(Strings.isNotEmpty(tList)){
			LOGGER.info("调解流程数量："+tList.size());
			dataList.addAll(tList);
		}
		if(Strings.isNotEmpty(cList)){
			LOGGER.info("撤诉流程数量："+cList.size());
			dataList.addAll(cList);
		}
		if(Strings.isNotEmpty(dataList)){
			LOGGER.info("要保存的数量为："+dataList.size());
			//根据OA中的流程，判断底表分配状态字段的值，并进行保存
			this.isExistSummary(dataList);
		}
//		List<Long> peIds = appealAcceptDao.getParentEnumId();
//		for(Long companyId : peIds){
//			int count = appealAcceptDao.getCountDbData4AssigningTask();
//			if(count != 0){
//				String groupId = AppContext.getSystemProperty("appealAccept.groupId.ydrd");
//				List<String> leaveId = appealAcceptDao.getAakForLeaveId(groupId);
//				LOGGER.info("获得到未请假人员数量为："+leaveId.size());
//				int pCount = leaveId.size();
//				//每人需要分配的条数
//				int pSize = count/pCount;
//				if(pSize != 0){
//					for(int i=0;i<pCount;i++){
//						FlipInfo fpi = new  FlipInfo();
//						fpi.setSize(pSize);
//						fpi.setPage(i + 1);
//						//第i个人将要分配的人员任务ID
//						List<Long> taskIds = appealAcceptDao.getDbData4AssigningTask(companyId,fpi);
//						//第i个人的人员ID
//						String pId = leaveId.get(i);
//						// 更新底表
//						appealAcceptDao.updateDbPersonId(taskIds, Long.valueOf(pId));
//					}
//				}
//			}
//		}
		LOGGER.info("定时任务调用接口saveAppealData2DB 结束");	
	}
	@Override
	public List<AppealAcceptPo> getAppealData(AppealAcceptEnum appealAcceptEnum) throws BusinessException {
		return appealAcceptDao.getAppealData(appealAcceptEnum);
	} 
	/**
	 * 判断OA中是否存在正在流转流程<br>
	 * 首先根据争议号码和用户姓名进行判断，OA中是否存在同一个号码存在正在流转的流程，如果有正在流转的且流程发起日期在5个工作日内的，保存底表，分配状态字段为：重复数据<br>
	 * 5个工作日外，分配状态字段为：未分配<br>
	 * 不存在正在流转的流程，分配状态字段为：未分配<br>
	 * @param dataList
	 * @throws BusinessException
	 */
	private void isExistSummary(List<AppealAcceptPo> dataList) throws BusinessException{
		for(AppealAcceptPo appeal:dataList){
			String disputeNum = appeal.getDisputeNum();
			String userName = appeal.getUserName();
			//当申诉类别为申诉时，在信息单、受理单、办理单流程中，只会存在一条未结束的summary
			//当申诉类别为调解时，在信息单、受理单、办理单流程中，不会存在未结束的summary
			//当申诉类别为撤诉时，是针对于信息单、受理单其中的某一条流程进行撤诉，会存在一条未结束的summary,此时需进行申诉类别判断
			List<Map<String,Long>> ids = this.getColSummary(userName,disputeNum);
			Long summaryId = null;
			String state = appeal.getState();
			String id = appeal.getId();
			if(Strings.isNotEmpty(ids)){
				Map<String, Long> map = ids.get(0);
				String key ="";
				for(Entry<String, Long> vo :map.entrySet()){
					key = vo.getKey();
					summaryId = vo.getValue();
				}
				LOGGER.info("OA中存在正在流转的流程，summaryId======"+summaryId+"=====进行判断..");
				ColSummary summary = colManager.getSummaryById(summaryId);
				Long formRecordid = summary.getFormRecordid();
				AppealAcceptPo acceptPo = appealAcceptDao.getAppealDate(key, formRecordid);
				//60分钟*每天8小时*5天
				long deadline = Long.valueOf(Constants.WORKTIME);
				Long orgAcconutID = Long.valueOf(AppContext.getSystemProperty("appealAccept.orgAcconutID"));
				Date date = workTimeManager.getCompleteDate4Nature(acceptPo.getAppealDate(), deadline, orgAcconutID);
				if(date.before(new Date()) || Constants.CS.equals(appeal.getState())){
					//存在未结束的流程且在5个工作日之外,保存数据，分配状态为：未分配
					appeal.setFlowState(Long.valueOf(AppContext.getSystemProperty("appealAccept.flowStateEnum.wfp").toString()));
				}else{
					//存在未结束的流程且在5个工作日之内,保存数据，分配状态为：重复数据
					appeal.setFlowState(Long.valueOf(AppContext.getSystemProperty("appealAccept.flowStateEnum.cf").toString()));
				}
				//保存数据到信息底表
				Long formId = appealAcceptDao.saveAppealData2DB(appeal);
				//获取该条流程相关联的附件信息
				saveWebFile(id, formId);
				boolean b = appealAcceptDao.isExistTable(id, state);
				appealAcceptDao.updateProcessFlag4Start(AppealAcceptEnum.READ.getValue(), appeal, null, b, state);
			}else{
				appeal.setFlowState(Long.valueOf(AppContext.getSystemProperty("appealAccept.flowStateEnum.wfp").toString()));
				LOGGER.info("OA中不存在正在流转的流程..");
				Long formId = appealAcceptDao.saveAppealData2DB(appeal);
				//获取该条流程相关联的附件信息
				saveWebFile(id, formId);
				boolean b = appealAcceptDao.isExistTable(id, state);
				appealAcceptDao.updateProcessFlag4Start(AppealAcceptEnum.READ.getValue(), appeal, null, b, state);
			}
		}
	}
	private void saveWebFile(String appealId,Long formId) throws BusinessException{
		List<FilePo> files = appealAcceptDao.getAttachement4Web(appealId);
		if(Strings.isNotEmpty(files)){
			int size = files.size();
			List<V3XFile> fileList = new ArrayList<V3XFile>();
			for(int i = 0;i<size;i++){
				//将所有附件写入到OA指定位置
				V3XFile v3xFile = saveToLocal(files.get(i));
				if(v3xFile != null){
					fileList.add(v3xFile);
				}
			}
			String tableName = AppContext.getSystemProperty("appealAccept.dbFormField.formmain");
			String tableField = AppContext.getSystemProperty("appealAccept.dbFormField.fj");
			long fjId = appealAcceptDao.updateFormAttachmentColumn(formId,tableName,tableField);
			saveAttachment(fileList, formId, fjId);
		}
	}
	
	/**
	 * 保存附件信息到ctp_attacment表，并进行数据关联
	 * @param fileList 附件集合
	 * @param summaryId 流程ID
	 * @param formFiledId 表单上传附件字段对应的ID
	 * @throws BusinessException
	 */
	private void saveAttachment(List<V3XFile> fileList,Long summaryId,Long formFiledId) throws BusinessException{
		if(Strings.isNotEmpty(fileList)){
			List<Attachment> attaList = new ArrayList<Attachment>();
			for(V3XFile file :fileList){
				Attachment atta = new Attachment();
				atta.setId(UUIDLong.longUUID());
				atta.setCategory(file.getCategory());
				atta.setCreatedate(file.getCreateDate());
				atta.setDescription(file.getDescription());
				atta.setFilename(file.getFilename());
				atta.setFileUrl(file.getId());
				atta.setIdIfNew();
				atta.setMimeType(file.getMimeType());
				atta.setReference(summaryId);
				atta.setSubReference(formFiledId);
				atta.setSize(file.getSize());
				atta.setSort(0);
				atta.setType(file.getType());
				attaList.add(atta);
			}
			attachmentManager.saveAsAtt(attaList);
		}
	}
	/**
	 * 从网站服务器获取流程附件，同时保存到ctp_file表中
	 * @param fp
	 * @return
	 * @throws BusinessException
	 */
	private V3XFile saveToLocal(FilePo fp) throws BusinessException{
		String outDir = AppContext.getSystemProperty("appealAccept.uploadFilePath.web");
		File file = new File(outDir);
		if(!file.exists()){
			file.mkdirs();
		}
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		V3XFile v3xFile = null;
		try {
			url = new URL(Constants.WEBURL+fp.getFilePath());
			conn = (HttpURLConnection) url.openConnection();
			int code = conn.getResponseCode();
			LOGGER.info("服务器响应流的返回值=============="+code);
			in = conn.getInputStream();
			//保存到ctp_file表
			v3xFile = fileManager.save(in, ApplicationCategoryEnum.form , fp.getFileName(), new Date(), true);
			int len;
			byte[] buffer = new byte[1024];
			FileOutputStream outStream = new FileOutputStream(outDir+fp.getFileId());
			while ((len = in.read(buffer)) != -1) {
				outStream.write(buffer,0,len);
			}
			outStream.flush();
			outStream.close();
			in.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			throw  new BusinessException(e);
		}
		return v3xFile;
	}
	@Override
	public void sendTaskFlow() throws BusinessException{
		LOGGER.info("定时任务发起用户申诉信息流程开始.....");
		List<AppealAcceptPo> taskList = appealAcceptDao.getTask4DB();
		if(Strings.isNotEmpty(taskList)){
			for(AppealAcceptPo aap : taskList){
				sendAppealDataFlow(aap);
			}
		}
		LOGGER.info("定时任务发起用户申诉信息流程结束.....");
	}
	@Override
	public void sendAppealDataFlow(AppealAcceptPo appeal) throws BusinessException {
		LOGGER.info("=======进入申诉信息单发起流程====");
		try {
			Map<String, Object> formDataMap = new HashMap<String, Object>();
           	String serialNumberId = AppContext.getSystemProperty("appealAccept.serialNumberId");
            // 流水号
            String serialNumber = serialNumbermanager.getSerialNumberValue(Long.parseLong(serialNumberId));
            
            LOGGER.info("数据表："+appeal.getAppealSource()+",ID:"+appeal.getId()+",地区："+appeal.getLocalAreaFir());
            String d = DateUtil.format(appeal.getAppealDate(), DateUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN);
            String userName = appeal.getUserName();
            formDataMap.put("用户姓名", userName);
            formDataMap.put("工作单位", appeal.getWorkAddress());
            formDataMap.put("申诉日期", d);
            formDataMap.put("联系电话", appeal.getPhone());
            formDataMap.put("电子邮件地址", appeal.getEmail());
            formDataMap.put("邮件编码", appeal.getPostalCode());
            formDataMap.put("身份证号码", appeal.getCarId());
            formDataMap.put("申诉来源", appeal.getAppealSource());
            String disputeNum = appeal.getDisputeNum();
            formDataMap.put("申诉涉及号码",disputeNum);
            formDataMap.put("用户地址", appeal.getUserAddress());
            Long firstQyId = Long.valueOf(appeal.getRespEnterprise());
            formDataMap.put("被申诉企业", firstQyId);
            String xnCompany = appeal.getXnCompany();
            Long secQyId = null;
            if(Strings.isNotBlank(xnCompany)){
            	secQyId = Long.valueOf(xnCompany);
            }
            formDataMap.put("二级被申诉企业", secQyId);
            formDataMap.put("投诉受理单号", appeal.getCompNum());
            String areaFir = appeal.getLocalAreaFir();
            formDataMap.put("运营企业地区-一级", appeal.getLocalAreaFir());
            formDataMap.put("运营企业地区-二级", appeal.getLocalAreaSec());
            formDataMap.put("申诉内容", appeal.getAppealContent());
            String lx = appeal.getState();
            formDataMap.put("案件类型", lx);
			if (AppContext.getSystemProperty("appealAccept.formfield.tj").equals(lx)
					|| AppContext.getSystemProperty("appealAccept.formfield.cs").equals(lx)) {
				LOGGER.info("申诉类型为："+lx+" 进行业务码与分类码的回写！");
				BusinessCodePo codePo = appealAcceptDao.getBusinessCode(disputeNum, userName);
				formDataMap.put("业务码一", codePo.getBusinessCodeOne());
				formDataMap.put("业务码二", codePo.getBusinessCodeTwo());
				formDataMap.put("业务码三", codePo.getBusinessCodeThree());
				formDataMap.put("业务码四", codePo.getBusinessCodeFour());
				formDataMap.put("分类码一", codePo.getClassificationCodeOne());
				formDataMap.put("分类码二", codePo.getClassificationCodeTwo());
				formDataMap.put("分类码三", codePo.getClassificationCodeThree());
			}
            formDataMap.put("撤诉时使用流水号", appeal.getOaInstanceNum());
            formDataMap.put("省份简称", appeal.getProvinceShort());
            formDataMap.put("认定员", appeal.getPersonId());
            formDataMap.put("数据ID", appeal.getId());
            formDataMap.put("申诉信息单流水号", serialNumber);
            Long xnqyPersonId = null;
            //主投企业存在集合中且属于省中心，此时虚拟企业回复人员为省中心的回复人员
            if(companyList.contains(String.valueOf(firstQyId)) && sList.contains(areaFir)){
            	xnqyPersonId = appealAcceptDao.getXnqyPersonId(firstQyId,Long.valueOf(areaFir),true);
            }else if(!companyList.contains(firstQyId) && secQyId != null){
            	//主投企业不在集合中，属于虚拟运营商或者三网试点企业，此时每家企业只有一个企业回复人员
            	xnqyPersonId = appealAcceptDao.getXnqyPersonId(firstQyId,secQyId,false);
            }
//            Long ydId = Long.valueOf(AppContext.getSystemProperty("appealAccept.ztqyEnumId.yd"));
//            if(firstQyId == ydId){
//            	xnqyPersonId = appealAcceptDao.getXnqyPersonId(ydId,secQyId);
//            }else{
//            	xnqyPersonId = appealAcceptDao.getXnqyPersonId(firstQyId,secQyId);
//            }
            formDataMap.put("虚拟运营商回复人员", xnqyPersonId);
            formDataMap.put("固定电话", appeal.getTel());
            formDataMap.put("认定员人员编号", appeal.getCode());
            List<HistoryPo> historyData = appealAcceptDao.getHistoryData(disputeNum, userName);
            if(Strings.isNotEmpty(historyData)){
            	LOGGER.info("====开始回填相应的历史记录信息=====");
            	List<Map<String, Object>> sonDataList = new ArrayList<Map<String, Object>>();
            	for(HistoryPo hp : historyData){
            		Map<String, Object> sonDataMap = new HashMap<String, Object>();
            		sonDataMap.put("申诉信息流水号-历史信息", hp.getLsh());
            		sonDataMap.put("用户姓名-历史信息", hp.getUserName());
            		sonDataMap.put("联系电话-历史信息", hp.getPhone());
            		sonDataMap.put("申诉时间-历史信息", hp.getAppealDate());
            		sonDataMap.put("申诉内容-历史信息", hp.getAppealContent());
            		sonDataMap.put("申诉来源-历史信息", hp.getAppealSource());
            		sonDataMap.put("处理状态-历史信息", hp.getClzt());
            		sonDataMap.put("被申诉企业-历史信息", hp.getBssqy());
            		sonDataMap.put("所属地区-历史信息", hp.getArea());
            		sonDataList.add(sonDataMap);
            	}
            	formDataMap.put("subForms", sonDataList);
            }
            
        	//发起人ID
        	String startMemberId = AppContext.getSystemProperty("appealAccept.startMemberId");
        	V3xOrgMember member = orgManager.getMemberById(Long.parseLong(startMemberId));
        	if (member == null) {
        		LOGGER.error("发起人为空，无法发送！");
        		return;
        	}
            String tokenUrl = AppContext.getSystemProperty("appealAccept.tokenUrl");
        	String sendFormUrl = AppContext.getSystemProperty("appealAccept.sendFormUrl");
        	String templateCode = AppContext.getSystemProperty("appealAccept.templateCode.appeal");
        	LOGGER.info("发起流程tokenUrl=" + tokenUrl);
        	LOGGER.info("发起流程sendFormUrl=" + sendFormUrl);
        	LOGGER.info("发起流程templateCode=" + templateCode);
        	LOGGER.info("发起流程LoginName=" + member.getLoginName());
        	
        	String summaryId = sendFlowManager.saveFormFlow(tokenUrl, sendFormUrl, templateCode, null, 
        			formDataMap, member.getLoginName());
        	LOGGER.info("用户受理表单发起流程===========summaryId:"+summaryId);
        	if (Strings.isNotBlank(summaryId) && !"-1".equals(summaryId) && !"0".equals(summaryId)) {
        		//底表中附件字段ID
        		Long dbfjId = appeal.getFjId();
        		if(dbfjId != null){
        			List<FilePo> fileUrlList = appealAcceptDao.getAllFileUrlByReference(dbfjId);
        			List<V3XFile> fileList = new ArrayList<V3XFile>();
        			for(FilePo fp : fileUrlList){
        				V3XFile file = fileManager.getV3XFile(Long.valueOf(fp.getFileId()));
        				fileList.add(file);
        			}
        			ColSummary summary = colManager.getColSummaryById(Long.valueOf(summaryId));
        			String tableName = AppContext.getSystemProperty("appealAccept.formfield.formmain");
        			String tableField = AppContext.getSystemProperty("appealAccept.formfield.fj");
        			long fjId = appealAcceptDao.updateFormAttachmentColumn(summary.getFormRecordid(),tableName,tableField);
        			saveAttachment(fileList, Long.valueOf(summaryId), fjId);
        		}
        		String state = appeal.getState();
        		CtpEnumItem item = enumManagerNew.getCacheEnumItem(Long.valueOf(state));
        		// 更新 数据状态
        		this.updateProcessFlag4Start(AppealAcceptEnum.READ.getValue(),appeal,serialNumber,item.getShowvalue());
        		//流程发起，更新数据状态为 已分配
        		this.updateDbState(appeal.getId());
                Thread.currentThread().sleep(2000);
        	}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		LOGGER.info("=======结束用户申诉信息发起流程");
	}
	@Override
	public void updateProcessFlag4Start(String state, AppealAcceptPo appeal, String serialNumber,String type)
			throws BusinessException {
		boolean b = appealAcceptDao.isExistTable(appeal.getId(),type);
		appealAcceptDao.updateProcessFlag4Start(state,appeal,serialNumber,b,type);
	}
	
	public void stopOAflow(String userName,String disputeNum,String oaInstanceNum) throws BusinessException {
		LOGGER.info("=========开始执行终止方法=====stopOAflow=============");
		//查询到是哪条流程
		List<Map<String,Long>> summary = this.getColSummary(userName,disputeNum);
		if(Strings.isNotEmpty(summary)){
			for(Map<String,Long> map :summary){
				Long summaryId = null;
				for(Entry<String, Long> vo : map.entrySet()){ 
					summaryId = vo.getValue(); 
				}
				LOGGER.info("流程终止的方法stopOAflow,summaryId="+summaryId+"");
				if(summaryId == null){
					continue;
				}
				//查询待办的事项，取其中一个人的待办来进行终止操作    ctp_affair表中
				List<CtpAffair> affairs = affairManager.getAffairsByObjectIdAndState(summaryId, StateEnum.col_pending);
				LOGGER.info("申诉受理流程终止的方法stopOAflow，summaryId="+summaryId+",查询的待办数据" + affairs);
				CtpAffair currentAffair = new CtpAffair();
				if(Strings.isNotEmpty(affairs)){
					currentAffair = affairs.get(0);
				}
				this.transStepStop(currentAffair);
				//更新是CANCEL状态
				String tabelName = getTabelName(oaInstanceNum,Constants.SS);
				updateProcessFlag(AppealAcceptEnum.STOP.getValue(), oaInstanceNum, tabelName);
				LOGGER.info("=========结束执行终止方法=====stopOAflow=============");
			}
		}
	}

	private String transStepStop(CtpAffair affair) throws BusinessException {
		LOGGER.info("****************受理单流程终止方法****************");

        if (affair == null || affair.getId() == null) {
        	LOGGER.info("流程终止方法 transStepStop 入参为空！");
            return null;
        }
        String processId = null;
        try {
        	V3xOrgMember member = orgManager.getMemberById(affair.getMemberId());
            User user = new User();
            user.setId(member.getId());
            user.setDepartmentId(member.getOrgDepartmentId());
            user.setAccountId(member.getOrgAccountId());
            user.setName(member.getName());
            Comment comment = new Comment();
            comment.setContent(Constants.MSG_CANCEL);
            
            ColSummary summary = getSummaryById(affair.getObjectId());
            processId = summary.getProcessId();
            String subject = summary.getSubject();
            summary.setSubject(subject+"("+Constants.YCS+")");
            //更新summary标题
            colManager.updateColSummary(summary);
            List<CtpAffair> affairs = affairManager.getAffairsByAppAndObjectId(ApplicationCategoryEnum.collaboration, summary.getId());
            
           if(Strings.isNotEmpty(affairs)){
        	   List<CtpAffair> affairList = new ArrayList<CtpAffair>();
        	   for(CtpAffair af :affairs){
        		   af.setSubject(af.getSubject()+"("+Constants.YCS+")");
        		   affairList.add(af);
        	   }
        	   LOGGER.info("更新ctp_affair 标题数量："+affairList.size());
        	   //更新ctp_affair 标题
        	   affairManager.updateAffairs(affairList);
        	   
           }
            //设置消息推送
            DateSharedWithWorkflowEngineThreadLocal.setPushMessageMembers(comment.getPushMessageToMembersList());
            //保存终止时的意见
            comment.setModuleId(summary.getId());
            comment.setCreateDate(new Timestamp(System.currentTimeMillis()));
            if (user.isAdmin()) {
                comment.setCreateId(user.getId());
            } else {
                comment.setCreateId(affair.getMemberId());
                comment.setAffairId(affair.getId());
            }
            //comment.setExtAtt1(tempMap.get("extAtt1").toString());
            comment.setExtAtt3("collaboration.dealAttitude.termination");
            if(!user.getId().equals(affair.getMemberId()) && !user.isAdmin()){
                comment.setExtAtt2(user.getName());
            }
            comment.setModuleType(ModuleType.collaboration.getKey());
            comment.setPid(0L);
            comment.setPushMessage(false);
            comment = saveOrUpdateComment(comment);
            
            Long _workitemId = affair.getSubObjectId();
            
            //将终止流程的当前summary放入ThreadLocal，便于工作流中发送消息时获取代理信息。
            DateSharedWithWorkflowEngineThreadLocal.setColSummary(summary);
            //添加意见
            DateSharedWithWorkflowEngineThreadLocal.setFinishWorkitemOpinionId(comment.getId(), false, comment.getContent(), 2,false);
            
            //ContentUtil.colStepStop(_workitemId);
            this.colStepStop(_workitemId, affair.getMemberId()+"");
            //工作流中更新了状态信息，重新获取，表单会用state字段
            summary = (ColSummary) DateSharedWithWorkflowEngineThreadLocal.getColSummary();
            
            //终止 流程结束时更新督办状态
            superviseManager.updateStatusBySummaryIdAndType(summary.getId(),SuperviseEnum.superviseState.supervised,SuperviseEnum.superviseType.summary);
            //调用表单万能方法,更新状态，触发子流程等
            if(String.valueOf(MainbodyType.FORM.getKey()).equals(summary.getBodyType())){
                try {
                   List<Comment> commentList = this.ctpCommentManager.getCommentList(ModuleType.collaboration, affair.getObjectId()); 
                   formManager.updateDataState(summary,affair,ColHandleType.stepStop,commentList);
                } catch (Exception e) {
                   LOGGER.error("更新表单相关信息异常",e);
                }
            }
            //记录流程日志
            if (user.isAdministrator() || user.isGroupAdmin() || user.isSystemAdmin()) {
                processLogManager.insertLog(user, Long.parseLong(summary.getProcessId()), 1l, 
                        ProcessLogAction.stepStop);
            } else {
                processLogManager.insertLog(user, Long.parseLong(summary.getProcessId()), affair.getActivityId(),
                        ProcessLogAction.stepStop);
            }
            //记录应用日志
            appLogManager.insertLog(user, AppLogAction.Coll_Flow_Stop, user.getName(), summary.getSubject());
            //流程正常结束通知
            CollaborationStopEvent stopEvent = new CollaborationStopEvent(this);
            stopEvent.setSummaryId(summary.getId());
            stopEvent.setUserId(user.getId());
            EventDispatcher.fireEvent(stopEvent);
            
            //触发协同立方事件
            Col4IColCubeEvent colevent = new Col4IColCubeEvent(this,affair,CollaborationCubeEnums.col_done);
            EventDispatcher.fireEvent(colevent);
            
            // 若做过指定回退的操作.做过回退发起者则被回退者的状态要从待发改为已发
            CtpAffair sendAffair = affairManager.getSenderAffair(summary.getId());
            if (sendAffair.getSubState() == SubStateEnum.col_pending_specialBacked.getKey()
                    || sendAffair.getSubState() == SubStateEnum.col_pending_specialBackToSenderCancel.getKey()) {
                sendAffair.setState(StateEnum.col_sent.getKey());
                sendAffair.setUpdateDate(new java.util.Date());
                affairManager.updateAffair(sendAffair);
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        	LOGGER.error(e.getMessage(), e);
            throw new BusinessException(e);
        }finally{
            this.wapi.releaseWorkFlowProcessLock(processId, String.valueOf(AppContext.currentUserId()));
        }
        
        return null;
	}
	
	  /**
     * 非回复 和 非发起人附言的时候走这个地方，比如撤销，回退，终止，指定回退等。
     * @param c
     * @return
     * @throws BusinessException
     */
   private Comment saveOrUpdateComment(Comment c) throws BusinessException{
	   Comment comment = c;
	   c.setPushMessage(false);
       if(c.getId()==null){
    	   comment = ctpCommentManager.insertComment(c);
       }else{
    	   ctpCommentManager.updateComment(c);
       }
       return comment;
   }
   
	private ColSummary getSummaryById(Long summaryId) throws BusinessException {
    	ColSummary s = colDao.getColSummaryById(summaryId);
    	ColDao  colDaoFK = (ColDao)AppContext.getBean("colDaoFK");
    	if(s == null && colDaoFK != null){
    		s = colDaoFK.getColSummaryByIdHis(summaryId);
    	}
    	return s;
    }
	
	@Override
	public List<Map<String,Long>> getColSummary(String userName,String disputeNum) throws BusinessException {
		List<Map<String,Long>> ids = new ArrayList<Map<String,Long>>();
		
		Long xxColSummary = appealAcceptDao.getXxColSummary(userName,disputeNum);
		Map<String,Long> map = new HashMap<String,Long>();
		if(xxColSummary != null){
			map.put("xx", xxColSummary);
			ids.add(map);
		}
		Long slColSummary = appealAcceptDao.getSlColSummary(userName,disputeNum);
		if(slColSummary != null){
			map.put("sl", slColSummary);
			ids.add(map);
		}
		Long blColSummary = appealAcceptDao.getBlColSummary(userName,disputeNum);
		if(blColSummary != null){
			map.put("bl", blColSummary);
			ids.add(map);
		}
		Long tjColSummary = appealAcceptDao.getTjColSummary(userName,disputeNum);
		if(blColSummary != null){
			map.put("tj", tjColSummary);
			ids.add(map);
		}
		return ids;
	}
    /**
     * 协同流程终止
     * @param caseId
     * @throws BPMException
     */
	private void colStepStop(Long workItemId,String curUserId) throws BPMException {
		WorkflowBpmContext context = new WorkflowBpmContext();
		context.setCurrentWorkitemId(workItemId);
		context.setAppName("collaboration");
		context.setBusinessData("operationType", 8);
		context.setCurrentUserId(curUserId);
		context.setBusinessData("autoStop", true);
		User user = new User();
		user.setId(Long.parseLong(curUserId));
        //定时任务运行时，是获取不到上下文的，需要手动添加以下
		AppContext.putThreadContext(GlobalNames.SESSION_CONTEXT_USERINFO_KEY, user);
		wapi.stopCase(context);	
        AppContext.removeThreadContext(GlobalNames.SESSION_CONTEXT_USERINFO_KEY);
    }
	@Override
	public void updateProcessFlag(String state, String oaInstanceId,String tabelName) throws BusinessException {
		appealAcceptDao.updateProcessFlag(state,oaInstanceId,tabelName);
	}
	
	@Override
	public String getTabelName(String oaInstanceId,String type) throws BusinessException {
		return appealAcceptDao.getTabelName(oaInstanceId,type);
		
	}
	@Override
	public void saveAppealData(AppealAcceptPo aap) throws BusinessException {
		appealAcceptDao.saveAppealData(aap);
	}
	@Override
	public List<AppealAcceptPo> getMediateData(AppealAcceptEnum appealAcceptEnum)
			throws BusinessException {
		return appealAcceptDao.getMediateData(appealAcceptEnum);
	}
	@Override
	@AjaxAccess
	public String transSendSMS(Map<String, String> obj) throws BusinessException {
		String phone = obj.get("phone");
		String content = obj.get("content");
		String result = SmsUtil.sendSms(phone, content);
		return result;
	}
	@Override
	public void updateLeaveInfo2DB(Long memberId, String code, Date startTime,
			Date endTime) throws BusinessException {
		appealAcceptDao.updateLeaveInfo2DB(memberId, code, startTime, endTime);
		
	}
	@Override
	public void updateDbState(String dataId) throws BusinessException {
		appealAcceptDao.updateDbState(dataId);
	}
	@Override
	public String getRdPersonId(String area) throws BusinessException {
		String pid = "";
		if(Strings.isNotBlank(area)){
			Long personId4Province = appealAcceptDao.getRdPersonId4Province(area);
			LOGGER.info("省份的认定员ID为=============="+personId4Province);
			if(personId4Province != null){
				pid = personId4Province + "";
			}else{
				String groupId = AppContext.getSystemProperty("appealAccept.groupId.rd");
				List<String> leaveId = appealAcceptDao.getAakForLeaveId(groupId);
				LOGGER.info("获得到未请假人员数量为："+leaveId.size());
				int minCount = 0;
				String templeteId = AppContext.getSystemProperty("appealAccept.templateId.xx");
				for(int i = 0;i<leaveId.size();i++){
					String id = leaveId.get(i);
					int c = appealAcceptDao.getHandleFormCount(id, templeteId);
					if(i == 0){
						minCount = c;
						pid = id;
					}
					if(c < minCount){
						minCount = c;
						pid = id;
					}
				}
				LOGGER.info("分配任务最少的认定员的ID===="+pid);
			}
		}
		return pid;
	}
	@Override
	public void transAutoAssigningTask() throws BusinessException {
		LOGGER.info("--------------定时自动分配任务开始--------------");
		List<AppealAcceptPo> apList = appealAcceptDao.getCountDbData4AssigningTask();
		LOGGER.info("获取未分配的任务数量============"+apList.size());
		if(Strings.isNotEmpty(apList)){
			for(AppealAcceptPo ap : apList){
				Long rdPersonId = null;
				String lx = ap.getState();
				CtpEnumItem item = enumManagerNew.getCacheEnumItem(Long.valueOf(lx));
				if(Constants.TJ.equals(item.getShowvalue()) || Constants.CS.equals(item.getShowvalue())){
					LOGGER.info("****进入调解或者撤诉确定认定员分支****");
					rdPersonId = appealAcceptDao.getProcessorId(ap.getUserName(), ap.getPhone(), ap.getDisputeNum());
				}else{
					LOGGER.info("****进入申诉确定认定员分支****");
					rdPersonId = Long.valueOf(this.getRdPersonId(ap.getLocalAreaFir()));
				}
				if(rdPersonId == null){
					LOGGER.info("认定员ID 为空，跳出此条数据！");
					continue;
				}
				if(rdPersonId != null){
					V3xOrgMember member = orgManager.getMemberById(rdPersonId);
					String code = member.getCode();
					LOGGER.info("认定员人员编号----"+code);
					appealAcceptDao.updateDbPersonId(Long.valueOf(ap.getId()), rdPersonId,code);
					this.sendTaskFlow();
				}
			}
		}
		LOGGER.info("----------------------定时自动分配任务结束------------------");
	}
}

