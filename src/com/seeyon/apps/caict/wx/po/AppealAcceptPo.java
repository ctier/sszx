package com.seeyon.apps.caict.wx.po;

import java.util.Date;

public class AppealAcceptPo {
	/**
	 * ID
	 */
	private String id;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 身份证号
	 */
	private String carId;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 固定电话
	 */
	private String tel;
	/**
	 * 企业投诉编码
	 */
	private String compNum;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 通信地址
	 */
	private String userAddress;
	/**
	 * 邮政编码
	 */
	private String postalCode;
	/**
	 * 工作单位
	 */
	private String workAddress;
	/**
	 * 申诉日期
	 */
	private Date appealDate;
	/**
	 * 申诉来源
	 */
	private String appealSource;
	/**
	 * 被申诉企业
	 */
	private String respEnterprise;
	/**
	 * 虚拟运营商
	 */
	private String xnCompany;
	/**
	 * 争议号码
	 */
	private String disputeNum;
	/**
	 * 所属地区一级
	 */
	private String localAreaFir;
	/**
	 * 所属地区二级
	 */
	private String localAreaSec;
	/**
	 * 申诉内容
	 */
	private String appealContent;
	/**
	 * OA实例号
	 */
	private String oaInstanceNum;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 流程分配状态ID
	 */
	private Long flowState;
	/**
	 * 省份简称
	 */
	private String provinceShort;
	/**
	 * 认定员
	 */
	private Long personId;
	/**
	 * 认定员人员编号
	 */
	private String code;
	/**
	 * 附件ID
	 */
	private Long fjId;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getFjId() {
		return fjId;
	}
	public void setFjId(Long fjId) {
		this.fjId = fjId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getProvinceShort() {
		return provinceShort;
	}
	public void setProvinceShort(String provinceShort) {
		this.provinceShort = provinceShort;
	}
	public Long getFlowState() {
		return flowState;
	}
	public void setFlowState(Long flowState) {
		this.flowState = flowState;
	}
	public String getOaInstanceNum() {
		return oaInstanceNum;
	}
	public void setOaInstanceNum(String oaInstanceNum) {
		this.oaInstanceNum = oaInstanceNum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getXnCompany() {
		return xnCompany;
	}
	public void setXnCompany(String xnCompany) {
		this.xnCompany = xnCompany;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCompNum() {
		return compNum;
	}
	public void setCompNum(String compNum) {
		this.compNum = compNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public Date getAppealDate() {
		return appealDate;
	}
	public void setAppealDate(Date appealDate) {
		this.appealDate = appealDate;
	}
	public String getAppealSource() {
		return appealSource;
	}
	public void setAppealSource(String appealSource) {
		this.appealSource = appealSource;
	}
	public String getRespEnterprise() {
		return respEnterprise;
	}
	public void setRespEnterprise(String respEnterprise) {
		this.respEnterprise = respEnterprise;
	}
	public String getDisputeNum() {
		return disputeNum;
	}
	public void setDisputeNum(String disputeNum) {
		this.disputeNum = disputeNum;
	}
	public String getAppealContent() {
		return appealContent;
	}
	public void setAppealContent(String appealContent) {
		this.appealContent = appealContent;
	}
	public String getLocalAreaFir() {
		return localAreaFir;
	}
	public void setLocalAreaFir(String localAreaFir) {
		this.localAreaFir = localAreaFir;
	}
	public String getLocalAreaSec() {
		return localAreaSec;
	}
	public void setLocalAreaSec(String localAreaSec) {
		this.localAreaSec = localAreaSec;
	}
}
