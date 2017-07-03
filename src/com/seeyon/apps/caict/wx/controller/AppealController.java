package com.seeyon.apps.caict.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.seeyon.ctp.common.controller.BaseController;

public class AppealController extends BaseController{

	/**
     * 申诉信息单发送短信需要的扩展控件<br>
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView transSendSMS2Info(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //也可以return new ModelAndView(new InternalResourceView("/WEB-INF/jsp/extFormPlug.jsp"));
        return new ModelAndView("apps/appealAccept/sendSMS2Info");
    }
    
    /**
     * 受理单发送短信需要的扩展控件<br>
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView transSendSMS2Accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //也可以return new ModelAndView(new InternalResourceView("/WEB-INF/jsp/extFormPlug.jsp"));
        return new ModelAndView("apps/appealAccept/sendSMS2Accept");
    }
    /**
     * 办理单发送短信需要的扩展控件<br>
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView transSendSMS2Transaction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //也可以return new ModelAndView(new InternalResourceView("/WEB-INF/jsp/extFormPlug.jsp"));
        return new ModelAndView("apps/appealAccept/sendSMS2Transaction");
    }
    /**
     * 调解单发送短信需要的扩展控件<br>
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView transSendSMS2Mediate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //也可以return new ModelAndView(new InternalResourceView("/WEB-INF/jsp/extFormPlug.jsp"));
        return new ModelAndView("apps/appealAccept/sendSMS2Mediate");
    }
    /**
     * 撤诉单发送短信需要的扩展控件<br>
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView transSendSMS2Cancel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //也可以return new ModelAndView(new InternalResourceView("/WEB-INF/jsp/extFormPlug.jsp"));
        return new ModelAndView("apps/appealAccept/sendSMS2Cancel");
    }
}
