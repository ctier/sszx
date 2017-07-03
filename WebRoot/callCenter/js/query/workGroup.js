/****************************************************************************
* ������: queryAllWorkGroups
* ��������: ��ѯ���а���
* ����: objectPhone - Phone����
* ����ֵ:Array(���а���������ID,��@�ָ�)
* ����½�: 2.8.25,2.8.26
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAllWorkGroups(objectPhone) 
{
	var Phone = objectPhone;
	var workGroupsArray = new Array();
	rtn = Phone.QueryTotalWorkGroup();//��ѯ���а���
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.WorkGroupNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.WorkGroupNum;i++)//WorkGroupNum ����ֵ��ʾ�������
	{
		rtn = Phone.GetWorkGroupInfoByIdx(i);//���ȡ�ذ����ժҪ��Ϣ
		workGroupsArray[i] = Phone.WorkGroupInfo_Name + "@" + Phone.WorkGroupInfo_MonitorNo;
	}
	return workGroupsArray;
}

/****************************************************************************
* ������: queryWorkGroupDetail
* ��������: ��ѯĳ���������õ���ϯ����
* ����: objectPhone - Phone����
*       monitorNo - �೤����
* ����ֵ:Array(��ϯ������@��β,����ǰ೤���ǹ���+@+�೤)
* ����½�: 2.8.26
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryWorkGroupDetail(objectPhone,monitorNo) 
{
	var Phone = objectPhone;
	var agentsInWorkGroupArray = new Array();
	rtn = Phone.QueryAgentsByWorkGroup(monitorNo);
	if(rtn!=0)
	{
		return ;
	}
	if(Phone.AgentNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.AgentNum;i++)
	{
		agentsInWorkGroupArray[i] = workNo = Phone.GetAgentIDByIdx(i) + "@";//�˲�ѯ�������೤
	}
	agentsInWorkGroupArray[Phone.AgentNum] = monitorNo + "@" + "�೤";//�������м��ϰ೤
	return agentsInWorkGroupArray;
}