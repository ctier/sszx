package com.seeyon.apps.caict.wx.extend;

import com.seeyon.ctp.form.modules.engin.field.FormFieldCustomExtendDesignManager;

/**
 * 申诉受理信息单扩展控件
 * @author Vic-WANG
 * @date   2017-3-22
 */
public class ExtPlugToAccept extends FormFieldCustomExtendDesignManager{

	@Override
	public String getId() {
		return "extPlugToAccept";
	}

	@Override
	public String getImage() {
		return "/seeyon/apps_res/v3xmain/images/personal/botton_send.png";
	}

	@Override
	public String getJsFileURL() {
		return "/seeyon/appeal/appeal.do?method=transSendSMS2Accept";
	}

	@Override
	public String getName() {
		return "申诉受理信息单扩展控件";
	}

	@Override
	public String getOnClickEvent() {
		return null;
	}

	@Override
	public int getSort() {
		return 0;
	}

	@Override
	public String getValueType() {
		return "text";
	}

	@Override
	public int getWindowHeight() {
		return 300;
	}

	@Override
	public int getWindowWidth() {
		return 400;
	}

}
