package com.seeyon.apps.caict.wx.manager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seeyon.apps.caict.wx.dao.AppealAcceptDao;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.organization.bo.V3xOrgMember;
import com.seeyon.ctp.organization.manager.OrgManager;
import com.seeyon.ctp.workflow.wapi.WorkflowCustomRoleManager;

public class TjGroupRoleManager implements WorkflowCustomRoleManager{
	private static Logger LOGGER = Logger.getLogger(TjGroupRoleManager.class);
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
//			String formRecordId = (String)data.get("CustomRoleFormMasterId");
//			String companyField = AppContext.getSystemProperty("appealAccept.tformfield.bssqy");
//			String tableName = AppContext.getSystemProperty("appealAccept.tformfield.formmain");
//			Long companyId = appealAcceptDao.getFormCompanyId(formRecordId,tableName,companyField);
//			String groupId = EnumConverUtil.getGroupId(companyId, Constants.TJGROUP);
			String groupId = AppContext.getSystemProperty("appealAccept.groupId.sh");
			List<String> pIds = appealAcceptDao.getAakForLeaveId(groupId);
			String templeteId = AppContext.getSystemProperty("appealAccept.templateId.tj");
			String minId = "";
			int minCount = 0;
			for(int i = 0;i<pIds.size();i++){
				String id= pIds.get(i);
				int count = appealAcceptDao.getHandleFormCount(id, templeteId);
				if(i == 0){
					minCount = count;
					minId = id;
				}
				if(count < minCount || pIds.size() == 1){
					minCount = count;
					minId = id;
				}
			}
			V3xOrgMember member = orgManager.getMemberById(Long.valueOf(minId));
			memberList.add(member);
			return memberList;
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getRoleName() {
		return "tjGroupRoleManager";
	}

	@Override
	public String getRoleText() {
		return "【调解员平均分配节点】";
	}

}
