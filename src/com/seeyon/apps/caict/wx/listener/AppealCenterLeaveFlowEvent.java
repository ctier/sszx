package com.seeyon.apps.caict.wx.listener;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.manager.AppealAcceptManager;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.workflow.event.AbstractWorkflowEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventData;

public class AppealCenterLeaveFlowEvent extends AbstractWorkflowEvent{

	private static Logger LOGGER = Logger.getLogger(AppealCenterLeaveFlowEvent.class);
	private AppealAcceptManager appealAcceptManager;
	
	public void setAppealAcceptManager(AppealAcceptManager appealAcceptManager) {
		this.appealAcceptManager = appealAcceptManager;
	}

	@Override
	public String getId() {
		return "appealCenterLeaveFlowEvent";
	}

	@Override
	public String getLabel() {
		return "申诉中心请假流程";
	}
	
	//结束事件
	@Override
	public void onProcessFinished(WorkflowEventData data) {
		LOGGER.info("诉中心请假流程结束事件onProcessFinished================");
		Map<String, Object> dataMap = data.getBusinessData();
		try {
			if(dataMap != null){
				//请假人id
				Long memberId = Long.valueOf(dataMap.get(AppContext.getSystemProperty("appealAccept.leave.userName")).toString());
				String code = dataMap.get(AppContext.getSystemProperty("appealAccept.leave.userCode")).toString();
				Date startTime = (Date)dataMap.get(AppContext.getSystemProperty("appealAccept.leave.startTime"));
				Date endTime = (Date)dataMap.get(AppContext.getSystemProperty("appealAccept.leave.endTime"));
				appealAcceptManager.updateLeaveInfo2DB(memberId, code, startTime, endTime);
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}
}
