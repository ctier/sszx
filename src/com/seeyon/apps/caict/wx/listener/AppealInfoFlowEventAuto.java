package com.seeyon.apps.caict.wx.listener;

import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.manager.AppealAcceptManager;
import com.seeyon.apps.caict.wx.utils.AppealAcceptEnum;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem;
import com.seeyon.ctp.workflow.event.AbstractWorkflowEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventData;

public class AppealInfoFlowEventAuto extends AbstractWorkflowEvent{

	private static Logger LOGGER = Logger.getLogger(AppealInfoFlowEventAuto.class);
	private AppealAcceptManager appealAcceptManager;
	private EnumManager enumManagerNew;
	
	public void setAppealAcceptManager(AppealAcceptManager appealAcceptManager) {
		this.appealAcceptManager = appealAcceptManager;
	}
	public void setEnumManagerNew(EnumManager enumManagerNew) {
		this.enumManagerNew = enumManagerNew;
	}

	@Override
	public String getId() {
		return "appealInfoFlowEventAuto";
	}

	@Override
	public String getLabel() {
		return "用户申诉信息单自动触发流程";
	}
	
	//结束事件
	@Override
	public void onProcessFinished(WorkflowEventData data) {
		LOGGER.info("信息单结束事件onProcessFinished================");
		Map<String, Object> dataMap = data.getBusinessData();
		try {
			if(dataMap != null){
				//首先判断是否受理，如果受理修改表单状态为受理，不受理则改为终止stop
				Object lx = dataMap.get(AppContext.getSystemProperty("appealAccept.formfield.sslx"));
				CtpEnumItem enumItem = enumManagerNew.getCacheEnumItem(Long.valueOf(lx.toString()));
				String lxLabel = enumItem.getShowvalue();
				LOGGER.info("申诉类型为："+lxLabel);
				Object sl = dataMap.get(AppContext.getSystemProperty("appealAccept.formfield.sl"));
				CtpEnumItem ei = enumManagerNew.getCacheEnumItem(Long.valueOf(sl.toString()));
				String slLabel = ei.getShowvalue();
				LOGGER.info("受理状态为："+slLabel);
				//oa流水号
				String filedOaInstanceNum = AppContext.getSystemProperty("appealAccept.formfield.oaInstanceId");
				String oaInstanceNum = dataMap.get(filedOaInstanceNum).toString();
				LOGGER.info("OA流水号为=============="+oaInstanceNum);
				String tableName = appealAcceptManager.getTabelName(oaInstanceNum,lxLabel);
				if("是".equals(slLabel) && "申诉".equals(lxLabel)){
					//申诉转到受理流程
					appealAcceptManager.updateProcessFlag(AppealAcceptEnum.ACCEPT.getValue(), oaInstanceNum,tableName);
				}else if("是".equals(slLabel) && ("调解".equals(lxLabel) || "撤诉".equals(lxLabel))){
					//调解转办理
					appealAcceptManager.updateProcessFlag(AppealAcceptEnum.TRANSACT.getValue(), oaInstanceNum,tableName);
				}else{
					//不受理,流程终止
					appealAcceptManager.updateProcessFlag(AppealAcceptEnum.STOP.getValue(), oaInstanceNum,tableName);
				}
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}
}
