package com.seeyon.apps.caict.wx.utils;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.manager.AppealAcceptManagerImpl;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;

public class TokenUtil {
	private static final Logger LOGGER = Logger.getLogger(AppealAcceptManagerImpl.class);
	
	/**
	 * 获取token
	 * @param tokenUrl
	 * @return
	 * @throws BusinessException
	 */
	public static String getToken(String tokenUrl) throws BusinessException {
        RPCServiceClient serviceClient;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
	        options.setTo(new EndpointReference(tokenUrl));
	        Object[] opAddEntryArgs = new Object[] { "service-admin", AppContext.getSystemProperty("webservice.password")};
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
}
