/****************************************************************************
* 函数名: queryAllAgents
* 功能描述：查询平台某VDN下配置的所有座席
* 参数: objectPhone - Phone对象
* 返回值: Array(所有座席的工号)
* 相关章节: 2.8.23
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAllAgents(objectPhone) 
{
	var Phone = objectPhone;
	var agentsArray = new Array();
	rtn = Phone.QueryTotalAgentAbstractEx();//扩展查询所有座席简要信息
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.AgentAbstractNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.AgentAbstractNum;i++)//AgentAbstractNum 属性值表示座席个数
	{
		rtn = Phone.GetAgentAbstractByIdx(i);//逐个取回座席的摘要信息
		agentsArray[i] = Phone.AgentAbstract_ID;
	}
	return agentsArray;
}

/****************************************************************************
* 函数名: queryLoginAgents
* 功能描述：根据座席状态查询平台某VDN下配置的座席
* 参数: objectPhone - Phone对象
* 		agentStatus - 座席状态,0表示查签入的座席,1表示查通话态座席
* 返回值: Array(某状态下座席的工号)
* 相关章节: 2.8.23
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryLoginAgents(objectPhone,agentStatus) 
{//根据座席状态查询座席
	var Phone = objectPhone;
	var agentsArray = new Array();
	rtn = Phone.QueryTotalAgentAbstractEx();//扩展查询所有座席简要信息
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
		for(x=0;x<Phone.AgentAbstractNum;x++)//AgentAbstractNum 属性值表示座席个数
		{
			rtn = Phone.GetAgentAbstractByIdx(x);//逐个取回座席的摘要信息
			rtn = Phone.QueryAgentStatusEx(Phone.AgentAbstract_ID);
			if(Phone.AgentInfoEx_CurState != agentStatus)
			{//查询已签入座席
				agentsArray.push(Phone.AgentAbstract_ID);
			}
		}
	}
	if(4 == agentStatus)
	{
		for(x=0;x<Phone.AgentAbstractNum;x++)//AgentAbstractNum 属性值表示座席个数
		{
			rtn = Phone.GetAgentAbstractByIdx(x);//逐个取回座席的摘要信息
			rtn = Phone.QueryAgentStatusEx(Phone.AgentAbstract_ID);
			if(Phone.AgentInfoEx_CurState == agentStatus)
			{//应答态座席
				agentsArray.push(Phone.AgentAbstract_ID);
			}
		}
	}
	if(5 == agentStatus)
	{
		for(x=0;x<Phone.AgentAbstractNum;x++)//AgentAbstractNum 属性值表示座席个数
		{
			rtn = Phone.GetAgentAbstractByIdx(x);//逐个取回座席的摘要信息
			rtn = Phone.QueryAgentStatusEx(Phone.AgentAbstract_ID);
			if(Phone.AgentInfoEx_CurState == agentStatus)
			{//查询通话态座席
				agentsArray.push(Phone.AgentAbstract_ID);
			}
		}
	}
	return agentsArray;
}

/****************************************************************************
* 函数名: queryAgentPrivateQueues
* 功能描述: 查询座席私有队列
* 参数: objectPhone - Phone对象
* 		agentID - 座席工号
* 返回值: String(私有队列信息的HTML格式)
* 相关章节: 2.8.2
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAgentPrivateQueues(objectPhone,agentID) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryDeviceStatusEx(9,agentID);//查询设备状态扩展(参数:设备类型,设备号)
	if(rtn!=0)
	{
		return ;
	}
	if(Phone.DeviceStatus_CallNum == 0)//当查询队列时,该属性表示排队呼叫数
	{
		return "";
	}
	var str = "<br><table cellpadding='0' cellspacing='1' class='tab1'><thead><tr><td colspan='6'>私有队列呼叫列表<td></tr></thead><tbody><tr class='tr2'><td>主叫号码</td><td>被叫号码</td><td>排队时长(秒)</td><td>客户级别</td><td>媒体类型</td><td>呼叫轨迹数</td></tr>";
	for(x=0;x<Phone.DeviceStatus_CallNum;x++)
	{
		/*GetCallIDbyIdx(index)根据索引号取呼叫标识
		 *返回值为0 表示失败,大于0 的值表示指向呼叫标识CALLID 的指针
		 *QueryCallInfoEx2(callID)查询呼叫扩展详细信息2
		 *该方法调用成功后，查询得到的呼叫详细信息各个字段将以控件属性的方式提供给调用者
		 *呼叫详细信息系列属性为CallInfoEx2_XXX
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
* 函数名: queryAgentOpsStat
* 功能描述: 查询指定座席的OPS状态
* 参数: objectPhone - Phone对象
* 		agentID - 座席工号
* 返回值: String(OPS状态)
* 相关章节: 2.8.28
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAgentOpsStat(objectPhone,agentID)
{
	var Phone = objectPhone;
	rtn = Phone.QueryTotalAgentAbstractEx();//扩展查询所有座席简要信息
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.AgentAbstractNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.AgentAbstractNum;i++)//AgentAbstractNum 属性值表示座席个数
	{
		rtn = Phone.GetAgentAbstractByIdx(i);//逐个取回座席的摘要信息
		if(agentID == Phone.AgentAbstract_ID)
		{//数组AgentOpsState详见../comm/enum.js
			return AgentOpsState[Phone.AgentAbstract_Status];
		}
	}
	return "";
}

/****************************************************************************
* 函数名: queryAgentDetail
* 功能描述：查询座席具体信息
* 参数: objectPhone - Phone对象
* 		agentID - 座席工号
* 返回值: String(座席详细信息的HTML格式)
* 相关章节: 2.8.22,2.8.27,2.8.29
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAgentDetail(objectPhone,agentID) 
{
	var Phone = objectPhone;
	var str = "";
	var strSkill = "<table cellpadding='0' cellspacing='1' class='tab2'><thead><tr><td colspan='8' style='height: 25px'>业务代表技能信息(加粗表示签入的技能)</td></tr></thead><tbody>";
	var strSkillTmp = "";
	rtn = Phone.QueryAgentStatusEx(agentID);
	if(rtn!=0)
	{
		return ;
	}
	//查询座席技能信息(配置的技能与签入的技能)
	rtn = Phone.QueryAgentSkillsEx(agentID);
	if(rtn!=0)
	{
		return ;
	}
	for(i=0;i<Phone.AgentSkillExNum;i++)//AgentSkillExNum属性值表示查询得到的技能个数
	{
		/*AgentSkillEx_IsConfiged属性表示座席是否配置了该技能队列的技能
		 *AgentSkillEx_IsSignin属性表示座席是否签入了该技能队列的技能
		 *循环判断,3个技能一行显示 ,加粗的技能表示座席签入的技能
		 *querySkillDesc(objectPhone,skillID)方法详见skill.js
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
	strSkill += (strSkillTmp==""? strSkillTmp="未配置技能" : strSkillTmp) +"</tdody></table>";
	//查询座席IP
	Phone.AgentInfoEx_CurState != 0 ? IP = Phone.QueryAgentIPAddress(agentID) : IP = "";
	//查询座席所属班组名
	Phone.GetWorkGroupInfoByIdx(Phone.AgentInfoEx_WorkGroupID -1)==0 ? workGroupName = Phone.WorkGroupInfo_Name : workGroupName = "";
	return str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab2'><thead><tr><td colspan='8' style='height: 25px'>业务代表基本信息</td></tr></thead><tbody>" + 
				"<tr><th>姓名:</th><td> " + Phone.AgentInfoEx_Name + "</td>" + 
				"<th>班组:</th><td>" + workGroupName + "</td>" + 
				"<th>IP:</th><td>" + IP + "</td>" + 
				"<th>当前状态:</th><td>" + queryAgentOpsStat(objectPhone,agentID) + "</td>" + 
				"</tr>" + 
				"<tr><th>工号:</th><td> " + Phone.AgentInfoEx_ID + "</td>" + 
				"<th>类型:</th><td>" + (Phone.AgentInfoEx_CurState == 0 ? "未知类型" : AgentType[Phone.AgentInfoEx_AgentType]) + "</td>" + 
				"<th>进入时间:</th><td>" + (Phone.AgentInfoEx_CurState == 0 ? "未登陆" : longToTime(Phone.AgentInfoEx_LogonTime*1000)) + "</td>" +
				"<th>持续时长(秒):</th><td>" + Phone.AgentInfoEx_CurStateTime + "</td>" +
				"</tr>" + 
				"</tdody></table><br>" + strSkill + "<br>" +
				"<table cellpadding='0' cellspacing='1' class='tab2'><thead><tr><td colspan='6' style='height: 25px'>业务代表话务信息</td></tr></thead><tbody>" + 
				"<tr><td>保持次数:</td><td> " + Phone.AgentInfoEx_KeepNums + "</td>" + 
				"<td>重定向次数:</td><td>" + Phone.AgentInfoEx_RedirectNums + "</td>" + 
				"<td>请假次数:</td><td>" + Phone.AgentInfoEx_RestNums + "</td>" + 
				"</tr>" + 
				"<tr><td>应答次数:</td><td> " + Phone.AgentInfoEx_AnswerNums + "</td>" + 
				"<td>内部呼叫次数:</td><td>" + Phone.AgentInfoEx_InterCallNums + "</td>" + 
				"<td>超假次数:</td><td>" + Phone.AgentInfoEx_RestOutNums + "</td>" +
				"</tr>" + 
				"<tr><td>三方通话次数:</td><td> " + Phone.AgentInfoEx_ConferenceNums + "</td>" + 
				"<td>久不应答次数:</td><td>" + Phone.AgentInfoEx_NoAnswerNums + "</td>" + 
				"<td>请假时长(秒):</td><td>" + Phone.AgentInfoEx_RestTime + "</td>" +
				"</tr>" + 
				"<tr><td>呼出成功次数:</td><td> " + Phone.AgentInfoEx_CallOutNums + "</td>" + 
				"<td>示忙次数:</td><td>" + Phone.AgentInfoEx_BusyTime + "</td>" + 
				"<td>休息实际时长(秒):</td><td>" + Phone.AgentInfoEx_ActualRestTime + "</td>" +
				"</tr>" + 
				"<tr><td>转移次数(包括内部):</td><td> " + Phone.AgentInfoEx_TransferNums + "</td>" + 
				"<td>示闲次数:</td><td>" + Phone.AgentInfoEx_IdleTime + "</td>" + 
				"<td>超假时长(秒):</td><td>" + Phone.AgentInfoEx_RestOutTime + "</td>" +
				"</tr>" + 
				"<tr><td>转出次数:</td><td> " + Phone.AgentInfoEx_TransferOutNums + "</td>" + 
				"<td>总通话时长(秒):</td><td>" + Phone.AgentInfoEx_TotalTalkingTimes + "</td>" + 
				"<td>主动收线次数:</td><td>" + Phone.AgentInfoEx_AgentRelease + "</td>" +
				"</tr>" + 
				"<tr><td>总通话次数:</td><td> " + (Phone.AgentInfoEx_AnswerNums + Phone.AgentInfoEx_CallOutNums + Phone.AgentInfoEx_InterCallNums) + "</td>" + 
				"<td>平均通话时长(秒):</td><td>" + Math.round((Phone.AgentInfoEx_AnswerNums + Phone.AgentInfoEx_CallOutNums + Phone.AgentInfoEx_InterCallNums) > 0 ? Phone.AgentInfoEx_TotalTalkingTimes/(Phone.AgentInfoEx_AnswerNums + Phone.AgentInfoEx_CallOutNums + Phone.AgentInfoEx_InterCallNums) : 0) + "</td>" + 
				"<td>总呼叫数:</td><td>" + (Phone.AgentInfoEx_CallOutNums + Phone.AgentInfoEx_InterCallNums) + "</td>" +
				"</tr>" + 
				"<tbody></table></div>";
}