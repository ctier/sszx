package com.seeyon.apps.caict.wx.manager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.sendflow.manager.SendFlowManager;
import com.seeyon.apps.caict.wx.dao.CallCenterDao;
import com.seeyon.apps.caict.wx.po.EfaxPo;
import com.seeyon.apps.caict.wx.po.TelAcceptPo;
import com.seeyon.apps.caict.wx.utils.Constants;
import com.seeyon.apps.caict.wx.utils.TokenUtil;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.Strings;
import com.seeyon.ctp.util.annotation.AjaxAccess;

public class CallCenterManagerImpl implements CallCenterManager {

	private static final Logger LOGGER = Logger.getLogger(CallCenterManagerImpl.class);
	private CallCenterDao				callCenterDao;
	private OrgManager 					orgManager;
	private SendFlowManager 			sendFlowManager;
	
	public void setSendFlowManager(SendFlowManager sendFlowManager) {
		this.sendFlowManager = sendFlowManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public void setCallCenterDao(CallCenterDao callCenterDao) {
		this.callCenterDao = callCenterDao;
	}

	@Override
	public void saveToLocal(EfaxPo ep) throws BusinessException {
		LOGGER.info("进入获取保存电子传真到OA服务器方法saveToLocal....");
		String outDir = AppContext.getSystemProperty("appealAccept.uploadFilePath.callCenter");
		File file = new File(outDir);
		if(!file.exists()){
			file.mkdirs();
		}
		URL url = null;
		URLConnection conn = null;
		InputStream in = null;
		try {
			url = new URL(Constants.CALLCENTERURL+ep.getPath());
			conn = url.openConnection();
			in = conn.getInputStream(); 
			int len;
			byte[] buffer = new byte[1024];
			FileOutputStream outStream = new FileOutputStream(outDir+ep.getFaxFile());
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
	}

	@Override
	public Long uploadAttachement(String senderLoginName, String fileName)
			throws BusinessException {

		if(Strings.isBlank(senderLoginName) || Strings.isBlank(fileName)){
			return null;
		}
		String tokenUrl = AppContext.getSystemProperty("appealAccept.tokenUrl");
		String token = TokenUtil.getToken(tokenUrl);
		Long attaId = 0l;
		String BOUNDARY = "---------------------------7d4a6d158c9"; // 分隔符
		String end = "\r\n";  
	    String twoHyphens = "--";  
		try {
			if (!token.equals("-1")) {
				LOGGER.info("获取token表示有效!!!token=" + token);
				String ws = AppContext.getSystemProperty("cght.oaUploadFileService");
				URL preUrl = new URL(ws + "&senderLoginName="+ senderLoginName + "&token=" + token);
				URLConnection uc = preUrl.openConnection();
				HttpURLConnection hc = (HttpURLConnection) uc;
				hc.setDoOutput(true);
				hc.setDoInput(true);
				hc.setUseCaches(false);
				hc.setRequestProperty("contentType", "charset=utf-8");
				hc.setRequestMethod("POST");
			    hc.setRequestProperty("Content-Type","multipart/form-data;boundary="+BOUNDARY);
				
			    
			    DataOutputStream ds = new DataOutputStream(hc.getOutputStream());  

				
				File file = new File(AppContext.getSystemProperty("appealAccept.uploadFilePath.callCenter") + fileName);
				if(file !=null && file.exists()){	
					StringBuffer sb = new StringBuffer();
			    	//设置上传附件标记头
			    	sb.append(twoHyphens).append(BOUNDARY).append(end);
					
					sb.append("Content-Disposition: form-data; ");
					sb.append("name=\"file" ).append("\";filename=\"").append(fileName);
					sb.append("\"").append(end);
					sb.append("Content-Type: application/pdf").append(end).append(end);
					LOGGER.info("上传附件，输出流设置信息：" + sb.toString());

					ds.write(sb.toString().getBytes("utf-8"));
					FileInputStream fStream = new FileInputStream(file);  
					int bufferSize = 1024;  
					byte[] buffer = new byte[bufferSize];  
					int length = -1;  
					while ((length = fStream.read(buffer)) != -1) {  
						ds.write(buffer, 0, length);  
					}  
					ds.writeBytes(end);
					fStream.close(); 
				}
			
			    //设置上传附件结束标志
			    byte[] end_data = (twoHyphens + BOUNDARY + twoHyphens + end).getBytes();
				ds.write(end_data);
				ds.flush();
			    InputStream is = hc.getInputStream();
				BufferedReader in=new BufferedReader(new InputStreamReader(is,"UTF-8"));

				String inputLine;
				StringBuffer bankXmlBuffer = new StringBuffer();
				while((inputLine = in.readLine())!=null){
					LOGGER.info("读取到多少个附件" + inputLine);
					bankXmlBuffer.append(inputLine);
				}
				
				in.close();
				String tempStr = bankXmlBuffer.toString();
				if(Strings.isNotBlank(tempStr)){
					LOGGER.info("返回单个附件ID==============" + tempStr);
					attaId = Long.parseLong(tempStr);
				}
				
				ds.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		return attaId;
	}

	@Override
	public void sendFaxFileFlow() throws BusinessException {
		LOGGER.info("定时发起电子传真文件流程开始....");
		//查询需要发起流程的电子传真并保存到OA服务器
		List<EfaxPo> list = callCenterDao.getEfax();
		LOGGER.info("获取到的电子传真数量为："+list.size());
		for(EfaxPo ep : list){
			this.saveToLocal(ep);
			sendFaxFileFlow(ep);
		}
		LOGGER.info("定时发起电子传真文件流程结束....");
	}

	@Override
	public void sendFaxFileFlow(EfaxPo ep) throws BusinessException {
		LOGGER.info("发起电子传真文件流程开始sendFaxFileFlow(EfaxPo ep)....");
		try {
			String startMemberId = AppContext.getSystemProperty("appealAccept.startMemberId");
			V3xOrgMember member = orgManager.getMemberById(Long.valueOf(startMemberId));
			String senderLoginName = member.getLoginName();
			Long attId = this.uploadAttachement(senderLoginName, ep.getId()+ep.getFaxFile());
			LOGGER.info("上传附件返回的ID为==========="+attId);
			Long[] attachments = new Long[1];
			attachments[0] = attId;
			String tokenUrl = AppContext.getSystemProperty("appealAccept.tokenUrl");
	    	String sendFormUrl = AppContext.getSystemProperty("appealAccept.sendFormUrl");
	    	String templateCode = AppContext.getSystemProperty("appealAccept.templateCode.fax");
			String summaryId = sendFlowManager.saveFormFlow(tokenUrl, sendFormUrl, templateCode, null, 
					null, senderLoginName, attachments);
			LOGGER.info("自动发起电子传真流程===========summaryId:"+summaryId);
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
		LOGGER.info("=======结束电子传真发起流程");
	}

	@Override
	public void saveTelData(TelAcceptPo tap) throws BusinessException {
		LOGGER.info("****进入保存电话受理数据方法saveTelData******");
		callCenterDao.saveTelData(tap);
	}

	@Override
	public FlipInfo getVoiceData(FlipInfo flipInfo, Map<String, String> query)
			throws BusinessException {
		return callCenterDao.getVoiceData(flipInfo, query);
	}

	@Override
	public void transVoiceFlow() throws BusinessException {
		LOGGER.info("定时获取呼叫中心数据库中语音留言记录数据开始============");
		List<EfaxPo> voiceList = callCenterDao.getVoiceData();
		List<String> voiceIdList = callCenterDao.getVoiceIdData();
		List<EfaxPo> saveList = new ArrayList<EfaxPo>();
		for(EfaxPo ep :voiceList){
			if(!voiceIdList.contains(ep.getId())){
				saveList.add(ep);
			}
		}
		if(Strings.isNotEmpty(saveList)){
			callCenterDao.saveVoiceData(saveList);
		}
		LOGGER.info("定时获取呼叫中心数据库中语音留言记录数据结束============");
	}

	@Override
	@AjaxAccess
	public int updateVoiceState(Map<String, Object> params)
			throws BusinessException {
		LOGGER.info("修改语音留言状态开始=========");
		String id = params.get("id").toString();
		int row = callCenterDao.updateVoiceState(id);
		return row;
	}

}
