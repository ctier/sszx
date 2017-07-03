/****************************************************************************
* ������: queryAllAgents
* ������������ѯƽ̨ĳVDN�����õ�������ϯ
* ����: objectPhone - Phone����
* ����ֵ: Array(������ϯ�Ĺ���)
* ����½�: 2.8.23
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAllAgents(objectPhone) 
{
	var Phone = objectPhone;
	var agentsArray = new Array();
	rtn = Phone.QueryTotalAgentAbstractEx();//��չ��ѯ������ϯ��Ҫ��Ϣ
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.AgentAbstractNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.AgentAbstractNum;i++)//AgentAbstractNum ����ֵ��ʾ��ϯ����
	{
		rtn = Phone.GetAgentAbstractByIdx(i);//���ȡ����ϯ��ժҪ��Ϣ
		agentsArray[i] = Phone.AgentAbstract_ID;
	}
	return agentsArray;
}

/****************************************************************************
* ������: queryLoginAgents
* ����������������ϯ״̬��ѯƽ̨ĳVDN�����õ���ϯ
* ����: objectPhone - Phone����
* 		agentStatus - ��ϯ״̬,0��ʾ��ǩ�����ϯ,1��ʾ��ͨ��̬��ϯ
* ����ֵ: Array(ĳ״̬����ϯ�Ĺ���)
* ����½�: 2.8.23
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryLoginAgents(objectPhone,agentStatus) 
{//������ϯ״̬��ѯ��ϯ
	var Phone = objectPhone;
	var agentsArray = new Array();
	rtn = Phone.QueryTotalAgentAbstractEx();//��չ��ѯ������ϯ��Ҫ��Ϣ
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.AgentAbstractNum == 0)
	{
		return ;
	}
	if(0 == agentStatus)
	{
		for(x=0;x<Phone.AgentAbstractNum;x++)//AgentAbstractNum ����ֵ��ʾ��ϯ����
		{
			rtn = Phone.GetAgentAbstractByIdx(x);//���ȡ����ϯ��ժҪ��Ϣ
			rtn = Phone.QueryAgentStatusEx(Phone.AgentAbstract_ID);
			if(Phone.AgentInfoEx_CurState != agentStatus)
			{//��ѯ��ǩ����ϯ
				agentsArray.push(Phone.AgentAbstract_ID);
			}
		}
	}
	if(4 == agentStatus)
	{
		for(x=0;x<Phone.AgentAbstractNum;x++)//AgentAbstractNum ����ֵ��ʾ��ϯ����
		{
			rtn = Phone.GetAgentAbstractByIdx(x);//���ȡ����ϯ��ժҪ��Ϣ
			rtn = Phone.QueryAgentStatusEx(Phone.AgentAbstract_ID);
			if(Phone.AgentInfoEx_CurState == agentStatus)
			{//Ӧ��̬��ϯ
				agentsArray.push(Phone.AgentAbstract_ID);
			}
		}
	}
	if(5 == agentStatus)
	{
		for(x=0;x<Phone.AgentAbstractNum;x++)//AgentAbstractNum ����ֵ��ʾ��ϯ����
		{
			rtn = Phone.GetAgentAbstractByIdx(x);//���ȡ����ϯ��ժҪ��Ϣ
			rtn = Phone.QueryAgentStatusEx(Phone.AgentAbstract_ID);
			if(Phone.AgentInfoEx_CurState == agentStatus)
			{//��ѯͨ��̬��ϯ
				agentsArray.push(Phone.AgentAbstract_ID);
			}
		}
	}
	return agentsArray;
}

/****************************************************************************
* ������: queryAgentPrivateQueues
* ��������: ��ѯ��ϯ˽�ж���
* ����: objectPhone - Phone����
* 		agentID - ��ϯ����
* ����ֵ: String(˽�ж�����Ϣ��HTML��ʽ)
* ����½�: 2.8.2
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAgentPrivateQueues(objectPhone,agentID) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryDeviceStatusEx(9,agentID);//��ѯ�豸״̬��չ(����:�豸����,�豸��)
	if(rtn!=0)
	{
		return ;
	}
	if(Phone.DeviceStatus_CallNum == 0)//����ѯ����ʱ,�����Ա�ʾ�ŶӺ�����
	{
		return "";
	}
	var str = "<br><table cellpadding='0' cellspacing='1' class='tab1'><thead><tr><td colspan='6'>˽�ж��к����б�<td></tr></thead><tbody><tr class='tr2'><td>���к���</td><td>���к���</td><td>�Ŷ�ʱ��(��)</td><td>�ͻ�����</td><td>ý������</td><td>���й켣��</td></tr>";
	for(x=0;x<Phone.DeviceStatus_CallNum;x++)
	{
		/*GetCallIDbyIdx(index)����������ȡ���б�ʶ
		 *����ֵΪ0 ��ʾʧ��,����0 ��ֵ��ʾָ����б�ʶCALLID ��ָ��
		 *QueryCallInfoEx2(callID)��ѯ������չ��ϸ��Ϣ2
		 *�÷������óɹ��󣬲�ѯ�õ��ĺ�����ϸ��Ϣ�����ֶν��Կؼ����Եķ�ʽ�ṩ��������
		 *������ϸ��Ϣϵ������ΪCallInfoEx2_XXX
		 */
		rtn = Phone.QueryCallInfoEx2(Phone.GetCallIDbyIdx(x));
		x%2==0 ?
		strtemp = 
		"<tr><td>" + Phone.CallInfoEx2_Ani + "</td>" + 
		"<td>" + Phone.CallInfoEx2_DialedNumber + "</td>" + 
		"<td>" + Phone.CallInfoEx2_WaitTime + "</td>" + 
		"<td>" + queryCustomLevelName(Phone,Phone.CallInfoEx2_Priority) + "</td>" +
		"<td>" + MediaType[Phone.CallInfoEx2_MediaType] + "</td>" +
		"<td>" + Phone.CallInfoEx2_PartyNum + "</td>" +
		"</tr>"
		:strtemp = 
			"<tr class='tr2'><td>" + Phone.CallInfoEx2_Ani + "</td>" + 
			"<td>" + Phone.CallInfoEx2_DialedNumber + "</td>" + 
			"<td>" + Phone.CallInfoEx2_WaitTime + "</td>" + 
			"<td>" + queryCustomLevelName(Phone,Phone.CallInfoEx2_Priority) + "</td>" +
			"<td>" + MediaType[Phone.CallInfoEx2_MediaType] + "</td>" +
			"<td>" + Phone.CallInfoEx2_PartyNum + "</td>" +
			"</tr>";
		str+=strtemp;
	}
	str+= "</tbody></table>";
	return str;
}

/****************************************************************************
* ������: queryAgentOpsStat
* ��������: ��ѯָ����ϯ��OPS״̬
* ����: objectPhone - Phone����
* 		agentID - ��ϯ����
* ����ֵ: String(OPS״̬)
* ����½�: 2.8.28
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAgentOpsStat(objectPhone,agentID)
{
	var Phone = objectPhone;
	rtn = Phone.QueryTotalAgentAbstractEx();//��չ��ѯ������ϯ��Ҫ��Ϣ
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.AgentAbstractNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.AgentAbstractNum;i++)//AgentAbstractNum ����ֵ��ʾ��ϯ����
	{
		rtn = Phone.GetAgentAbstractByIdx(i);//���ȡ����ϯ��ժҪ��Ϣ
		if(agentID == Phone.AgentAbstract_ID)
		{//����AgentOpsState���../comm/enum.js
			return AgentOpsState[Phone.AgentAbstract_Status];
		}
	}
	return "";
}

/****************************************************************************
* ������: queryAgentDetail
* ������������ѯ��ϯ������Ϣ
* ����: objectPhone - Phone����
* 		agentID - ��ϯ����
* ����ֵ: String(��ϯ��ϸ��Ϣ��HTML��ʽ)
* ����½�: 2.8.22,2.8.27,2.8.29
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAgentDetail(objectPhone,agentID) 
{
	var Phone = objectPhone;
	var str = "";
	var strSkill = "<table cellpadding='0' cellspacing='1' class='tab2'><thead><tr><td colspan='8' style='height: 25px'>ҵ���������Ϣ(�Ӵֱ�ʾǩ��ļ���)</td></tr></thead><tbody>";
	var strSkillTmp = "";
	rtn = Phone.QueryAgentStatusEx(agentID);
	if(rtn!=0)
	{
		return ;
	}
	//��ѯ��ϯ������Ϣ(���õļ�����ǩ��ļ���)
	rtn = Phone.QueryAgentSkillsEx(agentID);
	if(rtn!=0)
	{
		return ;
	}
	for(i=0;i<Phone.AgentSkillExNum;i++)//AgentSkillExNum����ֵ��ʾ��ѯ�õ��ļ��ܸ���
	{
		/*AgentSkillEx_IsConfiged���Ա�ʾ��ϯ�Ƿ������˸ü��ܶ��еļ���
		 *AgentSkillEx_IsSignin���Ա�ʾ��ϯ�Ƿ�ǩ���˸ü��ܶ��еļ���
		 *ѭ���ж�,3������һ����ʾ ,�Ӵֵļ��ܱ�ʾ��ϯǩ��ļ���
		 *querySkillDesc(objectPhone,skillID)�������skill.js
		 */
		if(Phone.GetAgentSkillExByIdx(i) == 0)
		{
			strSkillTmp += Phone.AgentSkillEx_IsConfiged 
					? ((
						(i+1)%3==0) 
						 ? (Phone.AgentSkillEx_IsSignin ? "<td><strong>" + querySkillDesc(Phone,Phone.AgentSkillEx_SkillID) + "</strong></td></tr>": "<td>" + querySkillDesc(Phone,Phone.AgentSkillEx_SkillID) + "</td></tr>") 
						 : ((i%3==0) 
								 ? (Phone.AgentSkillEx_IsSignin ? "<tr><td><strong>" + querySkillDesc(Phone,Phone.AgentSkillEx_SkillID) + "</strong></td>": "<td>" + querySkillDesc(Phone,Phone.AgentSkillEx_SkillID) + "</td>")
								 : (Phone.AgentSkillEx_IsSignin ? "<td><strong>" + querySkillDesc(Phone,Phone.AgentSkillEx_SkillID) + "</strong></td>": "<td>" + querySkillDesc(Phone,Phone.AgentSkillEx_SkillID) + "</td>")
							)
					   )
					: "";
		}
	}
	strSkill += (strSkillTmp==""? strSkillTmp="δ���ü���" : strSkillTmp) +"</tdody></table>";
	//��ѯ��ϯIP
	Phone.AgentInfoEx_CurState != 0 ? IP = Phone.QueryAgentIPAddress(agentID) : IP = "";
	//��ѯ��ϯ����������
	Phone.GetWorkGroupInfoByIdx(Phone.AgentInfoEx_WorkGroupID -1)==0 ? workGroupName = Phone.WorkGroupInfo_Name : workGroupName = "";
	return str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab2'><thead><tr><td colspan='8' style='height: 25px'>ҵ����������Ϣ</td></tr></thead><tbody>" + 
				"<tr><th>����:</th><td> " + Phone.AgentInfoEx_Name + "</td>" + 
				"<th>����:</th><td>" + workGroupName + "</td>" + 
				"<th>IP:</th><td>" + IP + "</td>" + 
				"<th>��ǰ״̬:</th><td>" + queryAgentOpsStat(objectPhone,agentID) + "</td>" + 
				"</tr>" + 
				"<tr><th>����:</th><td> " + Phone.AgentInfoEx_ID + "</td>" + 
				"<th>����:</th><td>" + (Phone.AgentInfoEx_CurState == 0 ? "δ֪����" : AgentType[Phone.AgentInfoEx_AgentType]) + "</td>" + 
				"<th>����ʱ��:</th><td>" + (Phone.AgentInfoEx_CurState == 0 ? "δ��½" : longToTime(Phone.AgentInfoEx_LogonTime*1000)) + "</td>" +
				"<th>����ʱ��(��):</th><td>" + Phone.AgentInfoEx_CurStateTime + "</td>" +
				"</tr>" + 
				"</tdody></table><br>" + strSkill + "<br>" +
				"<table cellpadding='0' cellspacing='1' class='tab2'><thead><tr><td colspan='6' style='height: 25px'>ҵ���������Ϣ</td></tr></thead><tbody>" + 
				"<tr><td>���ִ���:</td><td> " + Phone.AgentInfoEx_KeepNums + "</td>" + 
				"<td>�ض������:</td><td>" + Phone.AgentInfoEx_RedirectNums + "</td>" + 
				"<td>��ٴ���:</td><td>" + Phone.AgentInfoEx_RestNums + "</td>" + 
				"</tr>" + 
				"<tr><td>Ӧ�����:</td><td> " + Phone.AgentInfoEx_AnswerNums + "</td>" + 
				"<td>�ڲ����д���:</td><td>" + Phone.AgentInfoEx_InterCallNums + "</td>" + 
				"<td>���ٴ���:</td><td>" + Phone.AgentInfoEx_RestOutNums + "</td>" +
				"</tr>" + 
				"<tr><td>����ͨ������:</td><td> " + Phone.AgentInfoEx_ConferenceNums + "</td>" + 
				"<td>�ò�Ӧ�����:</td><td>" + Phone.AgentInfoEx_NoAnswerNums + "</td>" + 
				"<td>���ʱ��(��):</td><td>" + Phone.AgentInfoEx_RestTime + "</td>" +
				"</tr>" + 
				"<tr><td>�����ɹ�����:</td><td> " + Phone.AgentInfoEx_CallOutNums + "</td>" + 
				"<td>ʾæ����:</td><td>" + Phone.AgentInfoEx_BusyTime + "</td>" + 
				"<td>��Ϣʵ��ʱ��(��):</td><td>" + Phone.AgentInfoEx_ActualRestTime + "</td>" +
				"</tr>" + 
				"<tr><td>ת�ƴ���(�����ڲ�):</td><td> " + Phone.AgentInfoEx_TransferNums + "</td>" + 
				"<td>ʾ�д���:</td><td>" + Phone.AgentInfoEx_IdleTime + "</td>" + 
				"<td>����ʱ��(��):</td><td>" + Phone.AgentInfoEx_RestOutTime + "</td>" +
				"</tr>" + 
				"<tr><td>ת������:</td><td> " + Phone.AgentInfoEx_TransferOutNums + "</td>" + 
				"<td>��ͨ��ʱ��(��):</td><td>" + Phone.AgentInfoEx_TotalTalkingTimes + "</td>" + 
				"<td>�������ߴ���:</td><td>" + Phone.AgentInfoEx_AgentRelease + "</td>" +
				"</tr>" + 
				"<tr><td>��ͨ������:</td><td> " + (Phone.AgentInfoEx_AnswerNums + Phone.AgentInfoEx_CallOutNums + Phone.AgentInfoEx_InterCallNums) + "</td>" + 
				"<td>ƽ��ͨ��ʱ��(��):</td><td>" + Math.round((Phone.AgentInfoEx_AnswerNums + Phone.AgentInfoEx_CallOutNums + Phone.AgentInfoEx_InterCallNums) > 0 ? Phone.AgentInfoEx_TotalTalkingTimes/(Phone.AgentInfoEx_AnswerNums + Phone.AgentInfoEx_CallOutNums + Phone.AgentInfoEx_InterCallNums) : 0) + "</td>" + 
				"<td>�ܺ�����:</td><td>" + (Phone.AgentInfoEx_CallOutNums + Phone.AgentInfoEx_InterCallNums) + "</td>" +
				"</tr>" + 
				"<tbody></table></div>";
}