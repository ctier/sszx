package com.seeyon.apps.caict.wx.po;

import java.util.Date;

public class HistoryPo {

	/**
	 * 申诉信息流水号
	 */
	private String lsh;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 申诉时间
	 */
	private Date appealDate;
	/**
	 * 申诉内容
	 */
	private String appealContent;
	/**
	 * 申诉来源
	 */
	private Long appealSource; 
	/**
	 * 处理状态
	 */
	private Long clzt;
	/**
	 * 被申诉企业
	 */
	private Long bssqy;
	/**
	 * 所属地区
	 */
	private Long area;
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getAppealDate() {
		return appealDate;
	}
	public void setAppealDate(Date appealDate) {
		this.appealDate = appealDate;
	}
	public String getAppealContent() {
		return appealContent;
	}
	public void setAppealContent(String appealContent) {
		this.appealContent = appealContent;
	}
	public Long getAppealSource() {
		return appealSource;
	}
	public void setAppealSource(Long appealSource) {
		this.appealSource = appealSource;
	}
	public Long getClzt() {
		return clzt;
	}
	public void setClzt(Long clzt) {
		this.clzt = clzt;
	}
	public Long getBssqy() {
		return bssqy;
	}
	public void setBssqy(Long bssqy) {
		this.bssqy = bssqy;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	
}
