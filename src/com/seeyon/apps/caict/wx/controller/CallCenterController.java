package com.seeyon.apps.caict.wx.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.seeyon.apps.caict.wx.manager.CallCenterManager;
import com.seeyon.apps.caict.wx.po.TelAcceptPo;
import com.seeyon.apps.caict.wx.utils.Constants;
import com.seeyon.apps.caict.wx.utils.EnumConverUtil;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.authenticate.domain.User;
import com.seeyon.ctp.common.controller.BaseController;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.util.DateUtil;
import com.seeyon.v3x.util.Strings;

public class CallCenterController extends BaseController{

	private static final Logger LOGGER = Logger.getLogger(CallCenterController.class);
	private CallCenterManager callCenterManager;
	private OrgManager orgManager;
	
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	public void setCallCenterManager(CallCenterManager callCenterManager) {
		this.callCenterManager = callCenterManager;
	}
	/**
     * 呼叫中心页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("callCenter/frameset/index");
    	return modelAndView;
    }
    /**
     * 呼叫中心签入
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView join(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //也可以return new ModelAndView(new InternalResourceView("/WEB-INF/jsp/extFormPlug.jsp"));
        return new ModelAndView("callCenter/frameset/join");
    }
    /**
     * 呼叫中心电话受理main
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView mainTel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("callCenter/frameset/main");
    	User user = AppContext.getCurrentUser();
        Long memberId = user.getId();
        V3xOrgMember member = orgManager.getMemberById(memberId);
        modelAndView.addObject("member", member);
    	return modelAndView;
    }
    /**
     * 呼叫中心电话受理main
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView mainVoice(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("callCenter/frameset/voiceMain");
    	User user = AppContext.getCurrentUser();
        Long memberId = user.getId();
        V3xOrgMember member = orgManager.getMemberById(memberId);
        modelAndView.addObject("member", member);
    	return modelAndView;
    }
    /**
     * 呼叫中心顶端电话条
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView top(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //也可以return new ModelAndView(new InternalResourceView("/WEB-INF/jsp/extFormPlug.jsp"));
        return new ModelAndView("callCenter/frameset/top");
    }
    /**
     * 保存电话受理数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LOGGER.info("进入保存电话受理数据的方法save().....");
    	PrintWriter out = response.getWriter();
    	try{
    		TelAcceptPo tap = new TelAcceptPo();
    		String acceptMemberId = request.getParameter("sly");
    		LOGGER.info("受理员："+acceptMemberId);
    		if(Strings.isNotBlank(acceptMemberId)){
    			tap.setAppealMemberId(Long.valueOf(acceptMemberId));
    		}
    		String ldsj = request.getParameter("ldsj");
    		LOGGER.info("来电时间："+ldsj);
    		if(Strings.isNotBlank(ldsj)){
    			Date date = DateUtil.parse(ldsj);
    			tap.setAppealDate(date);
    		}
    		String appealSource = request.getParameter("appealSource");
    		LOGGER.info("申诉来源："+appealSource);
    		Long lyId = EnumConverUtil.sslyEnumConver(appealSource);
    		tap.setAppealSource(lyId);
    		String userName = request.getParameter("userName");
    		LOGGER.info("用户姓名："+userName);
    		if(Strings.isNotBlank(userName)){
    			tap.setUserName(userName);
    		}
    		String workAdd = request.getParameter("workAdd");
    		if(Strings.isNotBlank(workAdd)){
    			tap.setWorkAdd(workAdd);
    		}
    		String phone = request.getParameter("phone");
    		if(Strings.isNotBlank(phone)){
    			tap.setPhone(phone);
    		}
    		String disputeNum = request.getParameter("disputeNum");
    		if(Strings.isNotBlank(disputeNum)){
    			tap.setDisputeNum(disputeNum);
    		}
    		String userAdd = request.getParameter("userAdd");
    		if(Strings.isNotBlank(userAdd)){
    			tap.setUserAdd(userAdd);
    		}
    		String code = request.getParameter("code");
    		if(Strings.isNotBlank(code)){
    			tap.setPostalCode(code);
    		}
    		String appealContent = request.getParameter("appealContent");
    		if(Strings.isNotBlank(appealContent)){
    			tap.setAppealContent(appealContent);
    		}
    		String appealMode = request.getParameter("appealMode");
    		if(Strings.isNotBlank(appealMode)){
    			Long modeId = EnumConverUtil.appealModeEnumConver(appealMode);
    			tap.setAppealMode(modeId);
    		}
    		String processMode = request.getParameter("processMode");
    		if(Strings.isNotBlank(processMode)){
    			Long modeId = EnumConverUtil.processModeEnumConver(processMode);
    			tap.setProcessMode(modeId);
    		}
    		String firstCompany = request.getParameter("firstCompany");
    		if(Strings.isNotBlank(firstCompany)){
    			Long enumId = EnumConverUtil.qyEnumConver(firstCompany);
    			tap.setFirstCompany(enumId);
    		}
    		String secondCompany = request.getParameter("secondCompany");
    		if(Strings.isNotBlank(firstCompany) && !"请选择".equals(secondCompany)){
    			Long enumId = EnumConverUtil.qyEnumConver(firstCompany);
    			tap.setSecondCompany(enumId);
    		}
    		String province = request.getParameter("s_province");
    		if(Strings.isNotBlank(province)){
    			Long enumId = EnumConverUtil.locEnumConver(province);
    			tap.setProvince(enumId);
    		}
    		String city = request.getParameter("s_city");
    		if(Strings.isNotBlank(city)){
    			Long enumId = EnumConverUtil.locEnumConver(city);
    			tap.setCity(enumId);
    		}
    		String classifyFirst = request.getParameter("classifyFirst");
    		if(Strings.isNotBlank(classifyFirst) && !"请选择".equals(classifyFirst)){
    			Long enumId = EnumConverUtil.flEnumConver(classifyFirst);
    			tap.setClassifyFirst(enumId);
    		}
    		String classifySecond = request.getParameter("classifySecond");
    		if(Strings.isNotBlank(classifySecond) && !"请选择".equals(classifySecond)){
    			Long enumId = EnumConverUtil.flEnumConver(classifySecond);
    			tap.setClassifySecond(enumId);
    		}
    		String classifyThird = request.getParameter("classifyThird");
    		if(Strings.isNotBlank(classifyThird) && !"请选择".equals(classifyThird)){
    			Long enumId = EnumConverUtil.flEnumConver(classifyThird);
    			tap.setClassifyThird(enumId);
    		}
    		String businessFirst = request.getParameter("businessFirst");
    		if(Strings.isNotBlank(businessFirst) && !"请选择".equals(businessFirst)){
    			Long enumId = EnumConverUtil.ywmEnumConver(businessFirst);
    			tap.setBusinessFirst(enumId);
    		}
    		String businessSecond = request.getParameter("businessSecond");
    		if(Strings.isNotBlank(businessSecond) && !"请选择".equals(businessSecond)){
    			Long enumId = EnumConverUtil.ywmEnumConver(businessSecond);
    			tap.setBusinessSecond(enumId);
    		}
    		String businessThird = request.getParameter("businessThird");
    		if(Strings.isNotBlank(businessThird) && !"请选择".equals(businessThird)){
    			Long enumId = EnumConverUtil.ywmEnumConver(businessThird);
    			tap.setBusinessThird(enumId);
    		}
    		String businessFourth = request.getParameter("businessFourth");
    		if(Strings.isNotBlank(businessFourth) && !"请选择".equals(businessFourth)){
    			Long enumId = EnumConverUtil.ywmEnumConver(businessFourth);
    			tap.setBusinessFourth(enumId);
    		}
    		String remark = request.getParameter("remark");
    		if(Strings.isNotBlank(remark)){
    			tap.setRemark(remark);
    		}
    		String sjqy = request.getParameter("sjqy");
    		if(Strings.isNotBlank(sjqy)){
    			tap.setSjqy(sjqy);
    		}
    		callCenterManager.saveTelData(tap);
    		out.write("true");
    	}catch(Exception e){
    		out.write("false");
    		e.printStackTrace();
    		LOGGER.error(e.getMessage(), e);
    	}finally{
    		out.close();
    	}
        
    }
    /**
     * 语音留言页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView voiceIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("callCenter/frameset/voiceIndex");
    	return modelAndView;
    }
    /**
     * 语音留言列表页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("callCenter/frameset/page");
    	return modelAndView;
    }
    /**
     * 下载语音留言记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.info("====================进入下载语音留言方法download=======================");
		String u = request.getParameter("url");
		String fileName = request.getParameter("fileName");
		String strUrl = Constants.WEBURL + u;
		URL url = new URL(strUrl);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        conn.setRequestMethod("GET");  
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();//通过输入流获取数据  
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("GBK"),"ISO8859-1"));
        byte[] buffer = new byte[4096];
        int len = 0;
        OutputStream outputStream = response.getOutputStream();
        while ((len = inStream.read(buffer)) > 0){
        	outputStream.write(buffer, 0, len);
        }
        inStream.close();
        outputStream.close();
		return null;
	}
}
