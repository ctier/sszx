package com.seeyon.apps.caict.sendflow.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.seeyon.apps.caict.sendflow.bean.ColumnBean;
import com.seeyon.apps.caict.sendflow.bean.FormExportBean;
import com.seeyon.apps.caict.sendflow.bean.RowBean;
import com.seeyon.apps.caict.sendflow.bean.SubFormBean;
import com.seeyon.apps.caict.sendflow.bean.SummaryBean;
import com.seeyon.apps.caict.sendflow.bean.ValueBean;
import com.seeyon.apps.caict.sendflow.util.XstreamUtil;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.v3x.util.Strings;


public class SendFlowManagerImpl implements SendFlowManager {
	private static Logger LOGGER = Logger.getLogger(SendFlowManagerImpl.class);
	@Override
	public String saveFormFlow(String tokenUrl, String sendFormUrl,
			String templateCode, String subject, Map<String, Object> dataMap,
			String starter) throws BusinessException {
		List<Map<String, Object>> sonDataList = (List<Map<String, Object>>) dataMap.get("subForms");
        if (Strings.isNotEmpty(sonDataList)) {
            dataMap.remove("subForms");
        }

        FormExportBean feb = new FormExportBean();
        SummaryBean summaryBean = new SummaryBean();
        summaryBean.setId("");
        summaryBean.setName("");
        feb.setSummaryBean(summaryBean);

        ValueBean valueBean = new ValueBean();
        List<ColumnBean> colList = new ArrayList<ColumnBean>();
        if (dataMap != null) {
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                ColumnBean colBean = new ColumnBean();
                colBean.setName(entry.getKey());
                colBean.setValue(entry.getValue()==null?"":entry.getValue().toString());
                colList.add(colBean);
            }
        }
        valueBean.setColList(colList);
        feb.setValueBean(valueBean);

        if (CollectionUtils.isNotEmpty(sonDataList)) {
            SubFormBean subForm = new SubFormBean();
            valueBean = new ValueBean();
            List<RowBean> rowList = new ArrayList<RowBean>();
            for (Map<String, Object> subFormMap : sonDataList) {
                RowBean rowBean = new RowBean();
                colList = new ArrayList<ColumnBean>();
                for (Map.Entry<String, Object> entry : subFormMap.entrySet()) {
                    ColumnBean colBean = new ColumnBean();
                    colBean.setName(entry.getKey());
                    colBean.setValue(entry.getValue()==null?"":entry.getValue().toString());
                    colList.add(colBean);
                }
                rowBean.setColList(colList);
                rowList.add(rowBean);
            }
            valueBean.setRowList(rowList);
            subForm.setValueBean(valueBean);

            List<SubFormBean> subFormList = new ArrayList<SubFormBean>();
            subFormList.add(subForm);
            feb.setSubFormList(subFormList);
        }
		try {
			String dataXml = XstreamUtil.beanToXml(feb, true);
			LOGGER.info("dataXml==" + dataXml);

	        String token = getToken(tokenUrl);
	        if (token == null){
	        	return "-1";
	        }
	        RPCServiceClient serviceClient = new RPCServiceClient();
	        Options options = serviceClient.getOptions();
	        options.setTo(new EndpointReference(sendFormUrl));
	        Object[] opAddEntryArgs = new Object[] { token, starter, templateCode, subject, dataXml, null, "0" };
	        QName opAddEntry = new QName("http://impl.flow.services.v3x.seeyon.com", "launchFormCollaboration");
	        OMElement ele = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs);
	        String resultStr = ele.toString();
	        String summaryId = resultStr.substring(resultStr.indexOf(":result>") + 8, resultStr.indexOf("</ax", resultStr.indexOf(":result>")));
	        LOGGER.info("流程发送后summaryId=" + summaryId);
	        return summaryId;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}

	
	private String getToken(String tokenUrl) throws BusinessException {
        RPCServiceClient serviceClient;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
	        options.setTo(new EndpointReference(tokenUrl));
	        Object[] opAddEntryArgs = new Object[] { "service-admin", AppContext.getSystemProperty("webservice.password")};
	        //TODO 
	        QName opAddEntry = new QName("http://impl.services.v3x.seeyon.com", "authenticate");
	        OMElement ele = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs);
	        String resultStr = ele.toString();
	        String tokenStr = resultStr.substring(resultStr.indexOf(":id>") + 4, resultStr.indexOf("</ax"));
	        if ("-1".equals(tokenStr)) {
	        	LOGGER.error("获取token失败！");
	            return null;
	        } else {
	            return tokenStr;
	        }
		} catch (AxisFault e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
        
    }


	@Override
	public String saveFormFlow(String tokenUrl, String sendFormUrl,
			String templateCode, String subject, Map<String, Object> dataMap,
			String starter, Long[] attachments) throws BusinessException {
		List<Map<String, Object>> sonDataList = (List<Map<String, Object>>) dataMap.get("subForms");
        if (sonDataList != null) {
            dataMap.remove("subForms");
        }

        FormExportBean feb = new FormExportBean();
        SummaryBean summaryBean = new SummaryBean();
        summaryBean.setId("");
        summaryBean.setName("");
        feb.setSummaryBean(summaryBean);

        ValueBean valueBean = new ValueBean();
        List<ColumnBean> colList = new ArrayList<ColumnBean>();
        if (dataMap != null) {
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                ColumnBean colBean = new ColumnBean();
                colBean.setName(entry.getKey());
                colBean.setValue(entry.getValue()==null?"":entry.getValue().toString());
                colList.add(colBean);
            }
        }
        valueBean.setColList(colList);
        feb.setValueBean(valueBean);

        if (CollectionUtils.isNotEmpty(sonDataList)) {
            SubFormBean subForm = new SubFormBean();
            valueBean = new ValueBean();
            List<RowBean> rowList = new ArrayList<RowBean>();
            for (Map<String, Object> subFormMap : sonDataList) {
                RowBean rowBean = new RowBean();
                colList = new ArrayList<ColumnBean>();
                for (Map.Entry<String, Object> entry : subFormMap.entrySet()) {
                    ColumnBean colBean = new ColumnBean();
                    colBean.setName(entry.getKey());
                    colBean.setValue(entry.getValue()==null?"":entry.getValue().toString());
                    colList.add(colBean);
                }
                rowBean.setColList(colList);
                rowList.add(rowBean);
            }
            valueBean.setRowList(rowList);
            subForm.setValueBean(valueBean);

            List<SubFormBean> subFormList = new ArrayList<SubFormBean>();
            subFormList.add(subForm);
            feb.setSubFormList(subFormList);
        }
		try {
			String dataXml = XstreamUtil.beanToXml(feb, true);
			LOGGER.info("dataXml==" + dataXml);

	        String token = getToken(tokenUrl);
	        if (token == null){
	        	return "-1";
	        }
	        RPCServiceClient serviceClient = new RPCServiceClient();
	        Options options = serviceClient.getOptions();
	        options.setTo(new EndpointReference(sendFormUrl));
	        Object[] opAddEntryArgs = new Object[] { token, starter, templateCode, subject, dataXml, attachments, "0" };
	        QName opAddEntry = new QName("http://impl.flow.services.v3x.seeyon.com", "launchFormCollaboration");
	        OMElement ele = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs);
	        String resultStr = ele.toString();
	        String summaryId = resultStr.substring(resultStr.indexOf(":result>") + 8, resultStr.indexOf("</ax", resultStr.indexOf(":result>")));
	        LOGGER.info("流程发送后summaryId=" + summaryId);
	        return summaryId;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}
}
