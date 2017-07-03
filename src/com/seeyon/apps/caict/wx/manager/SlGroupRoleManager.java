package com.seeyon.apps.caict.wx.manager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.dao.AppealAcceptDao;
import com.seeyon.apps.caict.wx.utils.Constants;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.workflow.wapi.WorkflowCustomRoleManager;

public class SlGroupRoleManager implements WorkflowCustomRoleManager{
	private static Logger LOGGER = Logger.getLogger(SlGroupRoleManager.class);
	private OrgManager 			orgManager;
	private AppealAcceptDao  	appealAcceptDao;
	
	public void setAppealAcceptDao(AppealAcceptDao appealAcceptDao) {
		this.appealAcceptDao = appealAcceptDao;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	/**
	 * 获取角色匹配的人员（所在部门或上级部门）
	 * @param nodeMemberId 节点成员Id（人的话就是人员id，部门的话就是bumenid）（发起者或上节点的节点成员id）
	 * @param nodeMemberType 节点成员类型（发起者或上节点的类型）
	 * @param accountId 单位id（发起者或上节点所在的登录单位id）
	 * @param up 是否向上级部门查找
	 * @param startAccountId 发起者单位id
	 * @param sender 发起者id
	 * @param currentNodeUserId 当前处理人id
	 * @param data 其他数据（表单数据和综合办公的数据）
	 * @return
	 */
	@Override
	public List<V3xOrgMember> getMatcherMember(String nodeMemberId, String nodeMemberType, Long accountId, boolean up, String startAccountId, 
			String senderId, String currentNodeUserId, Map<String, Object> data) {
		LOGGER.info("进入审核组自动分配getMatcherMember方法中=========");
		try {
			List<V3xOrgMember> memberList = new ArrayList<V3xOrgMember>();
			//主表ID
			String formRecordId = (String)data.get("CustomRoleFormMasterId");
			LOGGER.info("主表ID-------"+formRecordId);
			Map<String,Long> map = appealAcceptDao.getMemberId4CaseSplit(Constants.SLFORMMAIN, formRecordId);
			Long areaId = map.get("AREA");
			V3xOrgMember member;
			Long ydId = map.get("ZGYD");
			LOGGER.info("移动ID-----"+ydId);
			if(ydId != null){
				Long ydPersonId = appealAcceptDao.getXnqyPersonId(ydId, areaId,true);
				member = orgManager.getMemberById(ydPersonId);
				memberList.add(member);
			}
			Long ltId = map.get("ZGLT");
			LOGGER.info("联通ID-----"+ltId);
			if(ltId != null){
				Long ltPersonId = appealAcceptDao.getXnqyPersonId(ltId, areaId,true);
				member = orgManager.getMemberById(ltPersonId);
				memberList.add(member);
			}
			Long dxId = map.get("ZGDX");
			LOGGER.info("电信ID-----"+dxId);
			if(dxId != null){
				Long dxPersonId = appealAcceptDao.getXnqyPersonId(dxId, areaId,true);
				member = orgManager.getMemberById(dxPersonId);
				memberList.add(member);
			}
			Long ttId = map.get("ZYTT");
			LOGGER.info("铁通ID-----"+ttId);
			if(ttId != null){
				Long ttPersonId = appealAcceptDao.getXnqyPersonId(ttId, areaId,true);
				member = orgManager.getMemberById(ttPersonId);
				memberList.add(member);
			}
			//-------------------------------------案件拆分去掉互联网与三网试点企业----------------------------------------
//			Long internetId = map.get("HLWQY");
//			LOGGER.info("互联网ID-----"+internetId);
//			if(internetId != null){
//				Long ltPersonId = appealAcceptDao.getXnqyPersonId(internetId, null);
//				member = orgManager.getMemberById(ltPersonId);
//				memberList.add(member);
//			}
//			Long sweId = map.get("SWE");
//			LOGGER.info("三网ID-----"+sweId);
//			if(sweId != null){
//				Long swyId = map.get("SWY");
//				LOGGER.info("三网一级ID===="+swyId);
//				Long swPersonId = appealAcceptDao.getXnqyPersonId(swyId, sweId,false);
//				member = orgManager.getMemberById(swPersonId);
//				memberList.add(member);
//			}
			//-------------------------------------案件拆分去掉互联网与三网试点企业----------------------------------------
			Long xneId = map.get("XNE");
			LOGGER.info("虚拟ID-----"+xneId);
			if(xneId != null){
				Long xnyId = map.get("XNY");
				LOGGER.info("虚拟运营商一级ID===="+xnyId);
				Long xnPersonId = appealAcceptDao.getXnqyPersonId(xnyId, xneId,false);
				member = orgManager.getMemberById(xnPersonId);
				memberList.add(member);
			}
			Long xnPid = map.get("XNPERSONID");
			LOGGER.info("底表虚拟回复人员ID-----"+xnPid);
			if(xnPid != null){
				member = orgManager.getMemberById(xnPid);
				memberList.add(member);
			}
			LOGGER.info("企业回复人数量："+memberList.size());
			return memberList;
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getRoleName() {
		return "slGroupRoleManager";
	}

	@Override
	public String getRoleText() {
		return "【受理单企业回复人员节点】";
	}

}
