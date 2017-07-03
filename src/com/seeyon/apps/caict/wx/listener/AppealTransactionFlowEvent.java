package com.seeyon.apps.caict.wx.listener;

import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.manager.AppealAcceptManager;
import com.seeyon.apps.caict.wx.utils.AppealAcceptEnum;
import com.seeyon.apps.caict.wx.utils.Constants;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.workflow.event.AbstractWorkflowEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventData;

public class AppealTransactionFlowEvent extends AbstractWorkflowEvent{

	private static Logger LOGGER = Logger.getLogger(AppealTransactionFlowEvent.class);
	private AppealAcceptManager appealAcceptManager;
	
	public void setAppealAcceptManager(AppealAcceptManager appealAcceptManager) {
		this.appealAcceptManager = appealAcceptManager;
	}

	@Override
	public String getId() {
		return "appealTransactionFlowEvent";
	}

	@Override
	public String getLabel() {
		return "用户申诉办理流程";
	}

	@Override
	public void onProcessFinished(WorkflowEventData data) {
		LOGGER.info("办理单结束事件onProcessFinished================");
		Map<String, Object> dataMap = data.getBusinessData();
		if(dataMap != null){
			String foin = AppContext.getSystemProperty("appealAccept.bformField.aInstanceId");
			//oa流水号
			String oaInstanceNum = dataMap.get(foin).toString();
			LOGGER.info("信息单中的OA流水号为=============="+oaInstanceNum);
			try {
				String tableName = appealAcceptManager.getTabelName(oaInstanceNum,Constants.SS);
				//办理流程结束，将状态改为finish
				appealAcceptManager.updateProcessFlag(AppealAcceptEnum.FINISH.getValue(), oaInstanceNum,tableName);
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage(),e);
				e.printStackTrace();
			}
		}
	}
}
	
	
	