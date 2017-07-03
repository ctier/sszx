/****************************************************************************
* ������: queryAllSkills
* ��������: ��ѯ���м���
* ����: objectPhone - Phone����
* ����ֵ:Array(���м��ܶ���������ID,��@�ָ�)
* ����½�: 2.8.17
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAllSkills(objectPhone) 
{
	var Phone = objectPhone;
	var skillsArray = new Array();
	rtn = Phone.QueryAcdID();//��ѯ���к��ж��У�ACD����ID
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.AcdNumber == 0)
	{
		return ;
	}
	for(i=0;i<Phone.AcdNumber;i++)//AcdNumber ����ֵ��ʾ���ܸ���
	{
		var strtemp;
		rtn = Phone.GetAcdIDByIdx(i);//���ȡ�ؼ��ܵ�ժҪ��Ϣ
		rtn = Phone.QueryAcdSkillEx(Phone.AcdID);
		skillsArray[i] = Phone.AcdSkillDesc + "@" + Phone.AcdID;
	}
	return skillsArray;
}

/****************************************************************************
* ������: querySkillDesc
* ��������: ��ѯָ�����ܶ�������
* ����: objectPhone - Phone����
*       skillID - ���ܶ���ID
* ����ֵ:String(ĳ���ܶ�������)
* ����½�: 2.8.19
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function querySkillDesc(objectPhone,skillID) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryAcdSkillEx(skillID);
	if(rtn!= 0)
	{
		return "";
	}
	return Phone.AcdSkillDesc;
}

/****************************************************************************
* ������: querySkillInfo
* ��������: ��ѯĳһ���ܶ�����Ϣ
* ����: objectPhone - Phone����
*       skillID - ���ܶ���ID
* ����ֵ:String(ĳ���ܶ�����Ϣ��HTML��ʽ)
* ����½�: 2.8.19
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function querySkillInfo(objectPhone,skillID) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryStatInfoByVDNSkillID(Phone.VdnID,skillID);//��ѯָ�����е�ͳ����Ϣ
	if(rtn!= 0)
	{
		return ;
	}
	return str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab2'><thead><tr><td colspan='6' style='height: 25px'>���ܶ�����Ϣ����</td></tr></thead><tbody>" + 
	"<tr><td>ǩ��ҵ�������:</td><td> " + Phone.VDNSkillAgentLoginNum + "</td>" + 
	"<td>ͨ��ҵ�������:</td><td>" + Phone.VDNSkillAgentTalkingNum + "</td>" + 
	"<td>����ҵ�������:</td><td>" + Phone.VDNSkillAgentIdleNum + "</td></tr>" + 
	"<tr><td>����ҵ�������:</td><td>" + Phone.VDNSkillAgentAvailableNum + "</td>" + 
	"<td>ʾæҵ�������:</td><td> " + Phone.VDNSkillAgentSetBusyNum + "</td>" + 
	"<td>��Ϣҵ�������:</td><td>" + Phone.VDNSkillAgentRestNum + "</td></tr>" + 
	"<tr><td>�ҵ��������ʱ��(��):</td><td>" + Phone.VDNSkillAgentMaxIdletime + "</td>" +
	"<td>���к�����:</td><td>" + Phone.VDNSkillAllCallNum + "</td>" +
	"<td>�Ŷӵĺ�����:</td><td>" + Phone.VDNSkillCallWaitNum + "</td></tr>" + 
	"<tr><td>���ڴ���ĺ�����:</td><td> " + Phone.VDNSkillProcCallNum + "</td>" +
	"<td>�Ѿ�����ĺ�����:</td><td> " + Phone.VDNSkillTotalCallNum + "</td>" + 
	"<td>ƽ��ͨ��ʱ��(��):</td><td> " + Phone.VDNSkillEvenCallTime + "</td></tr>" + 
	"<tr><td>20 ���ڽ�ͨ��:</td><td> " + Phone.VDNSkillConnNumIn20 + "</td>" +
	"<td>����еȴ�ʱ��(��):</td><td>" + Phone.VDNSkillMaxCallWaitTime + "</td>" + 
	"<td>ƽ���ȴ�ʱ��(��):</td><td>" + Phone.VDNSkillEvenWaitTime + "</td></tr>" + 
	"</tbody></table><li>��ͨ����Ա���Բ�ѯ���������ŶӺ�����,20 ���ڽ�ͨ��,���к�����,�����������,�Ѵ��������</li>" +
	"<li>�ʼ�Ա�ͼ��Ա���Բ�ѯ��ȫ��ͳ����Ϣ</li></div>";
}

/****************************************************************************
* ������: querySkillDetail
* ��������: ��ѯָ�����ܶ�����ǩ�����ϯ
* ����: objectPhone - Phone����
*       skillID - ���ܶ���ID
* ����ֵ:Array(��ϯ��ID)
* ����½�: 2.8.35
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function querySkillDetail(objectPhone,skillID) 
{
	var Phone = objectPhone;
	var agentsInSkillArray = new Array();
	rtn = Phone.QueryAgentsByAcdIDEx(skillID);//��ѯ�ܴ���ָ�����ж����к��е���ϯ����
	if(rtn!=0)
	{
		return ;
	}
	if(Phone.AgentNum == 0)
	{
		return ;
	}
	var str = "";
	for(i=0;i<Phone.AgentNum;i++)
	{
		agentsInSkillArray[i] = Phone.GetAgentIDByIdx(i);
	}
	return agentsInSkillArray;
}

/****************************************************************************
* ������: queryWaitCallsInSkill
* ��������: ��ѯָ�����ܶ����µȴ��ĺ�����Ϣ
* ����: objectPhone - Phone����
*       skillID - ���ܶ���ID
* ����ֵ:String(�ŶӺ�����Ϣ��HTML��ʽ)
* ����½�: 2.8.1
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryWaitCallsInSkill(objectPhone,skillID)
{
	var Phone = objectPhone;
	rtn = Phone.QueryDeviceStatusEx(1,skillID);//��ѯ�豸״̬��չ(����:�豸����,�豸��)
	if(rtn!=0)
	{
		return ;
	}
	if(Phone.DeviceStatus_CallNum == 0)//����ѯ����ʱ,�����Ա�ʾ�ŶӺ�����
	{
		return "";
	}
	var str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab1'><tbody><tr class='tr2'><td>���к���</td><td>���к���</td><td>�Ŷ�ʱ��(��)</td><td>�ͻ�����</td><td>ý������</td><td>���й켣��</td></tr>";
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
		strtemp = "<tr><td>" + Phone.CallInfoEx2_Ani + "</td>" + 
					"<td>" + Phone.CallInfoEx2_DialedNumber + "</td>" + 
					"<td>" + Phone.CallInfoEx2_WaitTime + "</td>" + 
					"<td>" + queryCustomLevelName(Phone,Phone.CallInfoEx2_Priority) + "</td>" +
					"<td>" + MediaType[Phone.CallInfoEx2_MediaType] + "</td>" +
					"<td>" + Phone.CallInfoEx2_PartyNum + "</td>" + 
					"</tr>"
		:strtemp = "<tr class='tr2'><td>" + Phone.CallInfoEx2_Ani + "</td>" + 
					"<td>" + Phone.CallInfoEx2_DialedNumber + "</td>" + 
					"<td>" + Phone.CallInfoEx2_WaitTime + "</td>" + 
					"<td>" + queryCustomLevelName(Phone,Phone.CallInfoEx2_Priority) + "</td>" +
					"<td>" + MediaType[Phone.CallInfoEx2_MediaType] + "</td>" +
					"<td>" + Phone.CallInfoEx2_PartyNum + "</td>" + 
					"</tr>";
		str+=strtemp
		//alert(i);
	}
	str+= "</tbody></table></div>";
	return str;
}