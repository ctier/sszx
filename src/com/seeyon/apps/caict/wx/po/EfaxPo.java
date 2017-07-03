package com.seeyon.apps.caict.wx.po;

import java.util.Date;

public class EfaxPo {

	/**
	 * 传真记录ID
	 */
	private String id;
	/**
	 * 传真发起号码
	 */
	private String callerno;
	/**
	 * 传真接收号码
	 */
	private String calleeno;
	/**
	 * 传真时间
	 */
	private Date time;
	/**
	 * 传真文件
	 */
	private String faxFile;
	/**
	 * 传真地址
	 */
	private String path;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCallerno() {
		return callerno;
	}
	public void setCallerno(String callerno) {
		this.callerno = callerno;
	}
	public String getCalleeno() {
		return calleeno;
	}
	public void setCalleeno(String calleeno) {
		this.calleeno = calleeno;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getFaxFile() {
		return faxFile;
	}
	public void setFaxFile(String faxFile) {
		this.faxFile = faxFile;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
