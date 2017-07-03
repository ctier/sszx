/****************************************************************************
* 函数名: queryAllWorkGroups
* 功能描述: 查询所有班组
* 参数: objectPhone - Phone对象
* 返回值:Array(所有班组描述和ID,以@分隔)
* 相关章节: 2.8.25,2.8.26
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAllWorkGroups(objectPhone) 
{
	var Phone = objectPhone;
	var workGroupsArray = new Array();
	rtn = Phone.QueryTotalWorkGroup();//查询所有班组
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.WorkGroupNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.WorkGroupNum;i++)//WorkGroupNum 属性值表示班组个数
	{
		rtn = Phone.GetWorkGroupInfoByIdx(i);//逐个取回班组的摘要信息
		workGroupsArray[i] = Phone.WorkGroupInfo_Name + "@" + Phone.WorkGroupInfo_MonitorNo;
	}
	return workGroupsArray;
}

/****************************************************************************
* 函数名: queryWorkGroupDetail
* 功能描述: 查询某班组中配置的座席工号
* 参数: objectPhone - Phone对象
*       monitorNo - 班长工号
* 返回值:Array(座席工号以@结尾,如果是班长则是工号+@+班长)
* 相关章节: 2.8.26
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
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
		agentsInWorkGroupArray[i] = workNo = Phone.GetAgentIDByIdx(i) + "@";//此查询不包括班长
	}
	agentsInWorkGroupArray[Phone.AgentNum] = monitorNo + "@" + "班长";//在数组中加上班长
	return agentsInWorkGroupArray;
}