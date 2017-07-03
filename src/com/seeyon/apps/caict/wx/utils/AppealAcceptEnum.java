package com.seeyon.apps.caict.wx.utils;


public enum AppealAcceptEnum {
	 NEW("用户申诉数据-新建", "NEW"), 
	 READ("OA读取申诉数据状态回写状态-已读", "READ"), 
	 FINISH("OA流程正常结束", "FINISH"), 
	 CANCEL("用户撤诉", "CANCEL"),
	 TRANSACT("受理转办理", "TRANSACT"),
	 ACCEPT("已受理", "ACCEPT"),
	 FINISH_CANCEL("已撤诉", "FINISH_CANCEL"),
	 STOP("停止","STOP");
     // 成员变量
     private String name;
     private String value;
     private AppealAcceptEnum(String name,String value){
    	 this.name = name;
    	 this.value = value;
     }
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
     
}
