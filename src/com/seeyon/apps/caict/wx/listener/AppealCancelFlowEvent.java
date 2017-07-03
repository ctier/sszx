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

public class AppealCancelFlowEvent extends AbstractWorkflowEvent{

	private static Logger LOGGER = Logger.getLogger(AppealCancelFlowEvent.class);
	private AppealAcceptManager appealAcceptManager;
	
	public void setAppealAcceptManager(AppealAcceptManager appealAcceptManager) {
		this.appealAcceptManager = appealAcceptManager;
	}

	@Override
	public String getId() {
		return "appealCancelFlowEvent";
	}

	@Override
	public String getLabel() {
		return "申请信息撤诉流程";
	}

	@Override
	public void onProcessFinished(WorkflowEventData data) {
		LOGGER.info(" 撤诉单结束事件onProcessFinished================");
		Map<String, Object> dataMap = data.getBusinessData();
		if(dataMap != null){
			String sslsh = AppContext.getSystemProperty("appealAccept.cformField.sslsh");
			String disNum = AppContext.getSystemProperty("appealAccept.cformField.disputeNum");
			String clsh = AppContext.getSystemProperty("appealAccept.cformField.ssxxlsh");
			String um = AppContext.getSystemProperty("appealAccept.cformField.userName");
			//oa流水号
			String oaInstanceNum = dataMap.get(sslsh).toString();
			String disputeNum = dataMap.get(disNum).toString();
			String userName = dataMap.get(um).toString();
			String csNum = dataMap.get(clsh).toString();
			LOGGER.info("撤诉流程中申诉信息流水号为=============="+oaInstanceNum);
			try {
				String tableName = appealAcceptManager.getTabelName(oaInstanceNum,Constants.CS);
				//撤诉流程结束，将状态改为finish
				appealAcceptManager.updateProcessFlag(AppealAcceptEnum.FINISH_CANCEL.getValue(), oaInstanceNum,tableName);
				appealAcceptManager.stopOAflow(userName,disputeNum, csNum);
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage(),e);
				e.printStackTrace();
			}
		}
	}
}
	
	
	