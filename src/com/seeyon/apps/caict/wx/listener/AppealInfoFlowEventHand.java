package com.seeyon.apps.caict.wx.listener;

import java.util.Date;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.manager.AppealAcceptManager;
import com.seeyon.apps.caict.wx.po.AppealAcceptPo;
import com.seeyon.apps.collaboration.po.ColSummary;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.content.mainbody.MainbodyType;
import com.seeyon.ctp.common.ctpenumnew.manager.EnumManager;
import com.seeyon.ctp.form.bean.FormDataMasterBean;
import com.seeyon.ctp.form.service.FormService;
import com.seeyon.ctp.util.DateUtil;
import com.seeyon.ctp.workflow.event.AbstractWorkflowEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventData;

public class AppealInfoFlowEventHand extends AbstractWorkflowEvent{

	private static Logger LOGGER = Logger.getLogger(AppealInfoFlowEventHand.class);
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
		return "appealInfoFlowEventHand";
	}

	@Override
	public String getLabel() {
		return "用户申诉信息单手动发起流程";
	}
	
	//手动发起流程时触发此事件，将数据回写数据库，使用户可以在网站或者微信上可以查询到相关信息
	@Override
	public void onStart(WorkflowEventData data) {
		LOGGER.info("手动发起触发的发起事件onStart================");
		try {
			ColSummary summary = (ColSummary) data.getSummaryObj();
			if (summary != null && String.valueOf(MainbodyType.FORM.getKey()).equals(
							summary.getBodyType())) {
				Long masterId = summary.getFormRecordid();
				Long formAppId = summary.getFormAppid();
				LOGGER.info("手动发起申诉受理流程masterId：" + masterId + " formAppId:"+ formAppId);
				// 获得主表数据
				FormDataMasterBean masterBean = FormService.findDataById(masterId, formAppId);
				AppealAcceptPo formData = getFormData(masterBean);
				appealAcceptManager.saveAppealData(formData);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private AppealAcceptPo getFormData(FormDataMasterBean masterBean) throws Exception{
		AppealAcceptPo aap = new AppealAcceptPo();
		
		Object field;
		//数据ID
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.dataId"));
		if(field != null && !"".equals(field)){
			aap.setId(field.toString());
		}
		//用户姓名
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.userName"));
		if(field != null && !"".equals(field)){
			aap.setUserName(field.toString());
		}
		//工作单位
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.workAdd"));
		if(field != null && !"".equals(field)){
			aap.setWorkAddress(field.toString());
		}
		//申诉日期
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.appealDate"));
		if(field != null && !"".equals(field)){
			Date date = DateUtil.parse(field.toString());
			LOGGER.info("格式化后申诉日期："+date);
			aap.setAppealDate(date);
		}
		//联系电话
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.userPhone"));
		if(field != null && !"".equals(field)){
			aap.setPhone(field.toString());
		}
		//电子邮件
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.email"));
		if(field != null && !"".equals(field)){
			aap.setEmail(field.toString());
		}
		//邮政编码
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.poscode"));
		if(field != null && !"".equals(field)){
			aap.setPostalCode(field.toString());
		}
		//身份证号码
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.usercarId"));
		if(field != null && !"".equals(field)){
			aap.setCarId(field.toString());
		}
		com.seeyon.ctp.common.po.ctpenumnew.CtpEnumItem cacheEnumItem;
		//申诉来源
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.appealSource"));
		if(field != null && !"".equals(field)){
			cacheEnumItem = enumManagerNew.getCacheEnumItem(Long.valueOf(field.toString()));
			String label = cacheEnumItem.getShowvalue();
			LOGGER.info("申诉来源："+label);
			aap.setAppealSource(label);
		}
		//申诉涉及号码
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.disputeNum"));
		if(field != null && !"".equals(field)){
			aap.setDisputeNum(field.toString());
		}
		//用户地址
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.userAdd"));
		if(field != null && !"".equals(field)){
			aap.setUserAddress(field.toString());
		}
		//被申诉企业一级
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.bssqy"));
		if(field != null && !"".equals(field)){
			cacheEnumItem = enumManagerNew.getCacheEnumItem(Long.valueOf(field.toString()));
			String label = cacheEnumItem.getShowvalue();
			LOGGER.info("被申诉企业一级"+label);
			aap.setRespEnterprise(label);
		}
		//被申诉企业二级
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.ejssqy"));
		if(field != null && !"".equals(field)){
			cacheEnumItem = enumManagerNew.getCacheEnumItem(Long.valueOf(field.toString()));
			String label = cacheEnumItem.getShowvalue();
			LOGGER.info("被申诉企业二级"+label);
			aap.setXnCompany(label);
		}
		//企业投诉编码
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.qytsbm"));
		if(field != null && !"".equals(field)){
			aap.setCompNum(field.toString());
		}
		//地区一级
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.dqyj"));
		String yjdq = null;
		if(field != null && !"".equals(field)){
			cacheEnumItem = enumManagerNew.getCacheEnumItem(Long.valueOf(field.toString()));
			yjdq = cacheEnumItem.getShowvalue();
			LOGGER.info("地区一级"+yjdq);
		}
		//地区二级
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.dqej"));
		String ejdq = null;
		if(field != null && !"".equals(field)){
			cacheEnumItem = enumManagerNew.getCacheEnumItem(Long.valueOf(field.toString()));
			ejdq = cacheEnumItem.getShowvalue();
			LOGGER.info("地区二级"+ejdq);
		}
		aap.setLocalAreaFir(yjdq+"-"+ejdq);
		//申诉内容
		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfieldHand.appealContext"));
		if(field != null && !"".equals(field)){
			aap.setAppealContent(field.toString());
		}
//		//oa流水号
//		field = masterBean.getFieldValue(AppContext.getSystemProperty("appealAccept.formfield.oaInstanceId"));
//		if(field != null && !"".equals(field)){
//			aap.setOaInstanceNum(field.toString());
//		}
		return aap;
	}
}
