package com.seeyon.apps.caict.wx.listener;

import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.manager.AppealAcceptManager;
import com.seeyon.apps.caict.wx.po.AppealAcceptPo;
import com.seeyon.apps.caict.wx.utils.AppealAcceptEnum;
import com.seeyon.apps.caict.wx.utils.Constants;
import com.seeyon.apps.collaboration.bo.DateSharedWithWorkflowEngineThreadLocal;
import com.seeyon.apps.collaboration.po.ColSummary;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.content.mainbody.MainbodyType;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.ctp.form.bean.FormDataMasterBean;
import com.seeyon.ctp.form.modules.serialNumber.SerialNumberManager;
import com.seeyon.ctp.form.service.FormService;
import com.seeyon.ctp.workflow.event.AbstractWorkflowEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventData;

public class AppealAcceptFlowEvent extends AbstractWorkflowEvent{

	private static Logger LOGGER = Logger.getLogger(AppealAcceptFlowEvent.class);
	private AppealAcceptManager 		appealAcceptManager;
	private EnumManager 				enumManagerNew;
	private SerialNumberManager     	serialNumbermanager = (SerialNumberManager)AppContext.getBean("serialNumberManager");
	
	public void setAppealAcceptManager(AppealAcceptManager appealAcceptManager) {
		this.appealAcceptManager = appealAcceptManager;
	}

	public void setEnumManagerNew(EnumManager enumManagerNew) {
		this.enumManagerNew = enumManagerNew;
	}

	@Override
	public String getId() {
		return "appealAcceptFlowEvent";
	}

	@Override
	public String getLabel() {
		return "用户申诉受理流程";
	}
	//发起事件
	@Override
	public void onStart(WorkflowEventData data) {
		LOGGER.info("受理单发起事件onStart....");
		try {
			ColSummary summary = (ColSummary) data.getSummaryObj();
			if (summary != null && String.valueOf(MainbodyType.FORM.getKey()).equals(
							summary.getBodyType())) {
				Long masterId = summary.getFormRecordid();
				Long formAppId = summary.getFormAppid();
				LOGGER.info("手动发起申诉受理流程masterId：" + masterId + " formAppId:"+ formAppId);
				String serialNumber = "";
				String serialNumberId = "";
				// 获得主表数据
				FormDataMasterBean masterBean = FormService.findDataById(masterId, formAppId);
				Object fieldValue = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.sformfield.bssdq"));
				CtpEnumItem item = enumManagerNew.getCacheEnumItem(Long.valueOf(fieldValue.toString()));
				String label = item.getShowvalue();
				if(label.contains(Constants.CHONGQING)){
					serialNumberId = AppContext.getSystemProperty("appealAccept.sformfield.lsh.c");
					serialNumber = serialNumbermanager.getSerialNumberValue(Long.parseLong(serialNumberId));
				}else if(label.contains(Constants.SHANGHAI)){
					serialNumberId = AppContext.getSystemProperty("appealAccept.sformfield.lsh.s");
					serialNumber = serialNumbermanager.getSerialNumberValue(Long.parseLong(serialNumberId));
				}else if(label.contains(Constants.XINJIANG)){
					serialNumberId = AppContext.getSystemProperty("appealAccept.sformfield.lsh.x");
					serialNumber = serialNumbermanager.getSerialNumberValue(Long.parseLong(serialNumberId));
				}else if(label.contains(Constants.JIANGSU)){
					serialNumberId = AppContext.getSystemProperty("appealAccept.sformfield.lsh.j");
					serialNumber = serialNumbermanager.getSerialNumberValue(Long.parseLong(serialNumberId));
				}else if(label.contains(Constants.FUJIAN)){
					serialNumberId = AppContext.getSystemProperty("appealAccept.sformfield.lsh.f");
					serialNumber = serialNumbermanager.getSerialNumberValue(Long.parseLong(serialNumberId));
				}else{
					serialNumberId = AppContext.getSystemProperty("appealAccept.sformfield.lsh.b");
					serialNumber = serialNumbermanager.getSerialNumberValue(Long.parseLong(serialNumberId));
				}
				masterBean.addFieldValue(AppContext.getSystemProperty("appealAccept.sformfield.sllsh"), serialNumber);
				FormService.saveOrUpdateFormData(masterBean, formAppId);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	//终止
	@Override
	public void onStop(WorkflowEventData data) {
		LOGGER.info("受理单终止事件onStop================");
		ColSummary summary = (ColSummary) DateSharedWithWorkflowEngineThreadLocal.getColSummary();
		LOGGER.info("受理单流程终止事件要终止的流程：" + summary);
		if(summary != null){
			try {
				FormDataMasterBean masterBean = FormService.findDataById(summary.getFormRecordid(), summary.getFormAppid());
				if(masterBean != null){
					//oa流水号
					String oaInstanceNum = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.sformfield.xxdlsh")).toString();
					LOGGER.info("受理单流程对应的信息单OA流水号为=============="+oaInstanceNum);
					String tableName = appealAcceptManager.getTabelName(oaInstanceNum,Constants.SS);
					appealAcceptManager.updateProcessFlag(AppealAcceptEnum.STOP.getValue(), oaInstanceNum,tableName);
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
				e.printStackTrace();
			}
		}
	}

	//结束事件
	@Override
	public void onProcessFinished(WorkflowEventData data) {
		LOGGER.info("受理单结束事件onProcessFinished================");
		Map<String, Object> dataMap = data.getBusinessData();
		if(dataMap != null){
			try {
				String filedOaInstanceNum = AppContext.getSystemProperty("appealAccept.sformfield.xxdlsh");
				//oa流水号
				String oaInstanceNum = dataMap.get(filedOaInstanceNum).toString();
				LOGGER.info("受理单流程对应的信息单OA流水号为=============="+oaInstanceNum);
				//首先判断是否受理，如果受理修改表单状态为受理，不受理则改为终止stop
				Object cljg = dataMap.get(AppContext.getSystemProperty("appealAccept.sformfield.cljg"));
				CtpEnumItem ei = enumManagerNew.getCacheEnumItem(Long.valueOf(cljg.toString()));
				String cljgLabel = ei.getShowvalue();
				String tableName = appealAcceptManager.getTabelName(oaInstanceNum,Constants.SS);
				if("转办".equals(cljgLabel)){
					//受理流程结束，将状态改为办理
					appealAcceptManager.updateProcessFlag(AppealAcceptEnum.TRANSACT.getValue(), oaInstanceNum,tableName);
				}else if("办结".equals(cljgLabel)){
					//流程办结，不触发办理流程
					appealAcceptManager.updateProcessFlag(AppealAcceptEnum.FINISH.getValue(), oaInstanceNum,tableName);
				}
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage(),e);
				e.printStackTrace();
			}
		}
	}
}
	
	
	