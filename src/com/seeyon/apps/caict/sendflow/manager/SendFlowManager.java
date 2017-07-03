package com.seeyon.apps.caict.sendflow.manager;

import java.util.Map;

import com.seeyon.ctp.common.exceptions.BusinessException;

public interface SendFlowManager {
	/**
	 * 发送流程
	 * @param tokenUrl
	 * @param sendFormUrl
	 * @param templateCode
	 * @param subject
	 * @param dataMap
	 * @param starter
	 * @return
	 * @throws BusinessException
	 */
	public String saveFormFlow(String tokenUrl, String sendFormUrl, String templateCode, String subject, Map<String, Object> dataMap, String starter) throws BusinessException;
	/**
	 * 发送流程含附件上传
	 * @param tokenUrl
	 * @param sendFormUrl
	 * @param templateCode
	 * @param subject
	 * @param dataMap
	 * @param starter
	 * @return
	 * @throws BusinessException
	 */
	public String saveFormFlow(String tokenUrl, String sendFormUrl, String templateCode, String subject, Map<String, Object> dataMap, String starter,Long[] attachments) throws BusinessException;
}
