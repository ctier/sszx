/****************************************************************************
* 函数名: queryAllSkills
* 功能描述: 查询所有技能
* 参数: objectPhone - Phone对象
* 返回值:Array(所有技能队列描述和ID,以@分隔)
* 相关章节: 2.8.17
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAllSkills(objectPhone) 
{
	var Phone = objectPhone;
	var skillsArray = new Array();
	rtn = Phone.QueryAcdID();//查询所有呼叫队列（ACD）的ID
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.AcdNumber == 0)
	{
		return ;
	}
	for(i=0;i<Phone.AcdNumber;i++)//AcdNumber 属性值表示技能个数
	{
		var strtemp;
		rtn = Phone.GetAcdIDByIdx(i);//逐个取回技能的摘要信息
		rtn = Phone.QueryAcdSkillEx(Phone.AcdID);
		skillsArray[i] = Phone.AcdSkillDesc + "@" + Phone.AcdID;
	}
	return skillsArray;
}

/****************************************************************************
* 函数名: querySkillDesc
* 功能描述: 查询指定技能队列描述
* 参数: objectPhone - Phone对象
*       skillID - 技能队列ID
* 返回值:String(某技能队列描述)
* 相关章节: 2.8.19
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
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
* 函数名: querySkillInfo
* 功能描述: 查询某一技能队列信息
* 参数: objectPhone - Phone对象
*       skillID - 技能队列ID
* 返回值:String(某技能队列信息的HTML格式)
* 相关章节: 2.8.19
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function querySkillInfo(objectPhone,skillID) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryStatInfoByVDNSkillID(Phone.VdnID,skillID);//查询指定队列的统计信息
	if(rtn!= 0)
	{
		return ;
	}
	return str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab2'><thead><tr><td colspan='6' style='height: 25px'>技能队列信息综览</td></tr></thead><tbody>" + 
	"<tr><td>签入业务代表数:</td><td> " + Phone.VDNSkillAgentLoginNum + "</td>" + 
	"<td>通话业务代表数:</td><td>" + Phone.VDNSkillAgentTalkingNum + "</td>" + 
	"<td>空闲业务代表数:</td><td>" + Phone.VDNSkillAgentIdleNum + "</td></tr>" + 
	"<tr><td>可用业务代表数:</td><td>" + Phone.VDNSkillAgentAvailableNum + "</td>" + 
	"<td>示忙业务代表数:</td><td> " + Phone.VDNSkillAgentSetBusyNum + "</td>" + 
	"<td>休息业务代表数:</td><td>" + Phone.VDNSkillAgentRestNum + "</td></tr>" + 
	"<tr><td>最长业务代表空闲时间(秒):</td><td>" + Phone.VDNSkillAgentMaxIdletime + "</td>" +
	"<td>所有呼叫数:</td><td>" + Phone.VDNSkillAllCallNum + "</td>" +
	"<td>排队的呼叫数:</td><td>" + Phone.VDNSkillCallWaitNum + "</td></tr>" + 
	"<tr><td>正在处理的呼叫数:</td><td> " + Phone.VDNSkillProcCallNum + "</td>" +
	"<td>已经处理的呼叫数:</td><td> " + Phone.VDNSkillTotalCallNum + "</td>" + 
	"<td>平均通话时长(秒):</td><td> " + Phone.VDNSkillEvenCallTime + "</td></tr>" + 
	"<tr><td>20 秒内接通数:</td><td> " + Phone.VDNSkillConnNumIn20 + "</td>" +
	"<td>最长呼叫等待时间(秒):</td><td>" + Phone.VDNSkillMaxCallWaitTime + "</td>" + 
	"<td>平均等待时长(秒):</td><td>" + Phone.VDNSkillEvenWaitTime + "</td></tr>" + 
	"</tbody></table><li>普通话务员可以查询到队列中排队呼叫数,20 秒内接通数,所有呼叫数,正处理呼叫数,已处理呼叫数</li>" +
	"<li>质检员和监控员可以查询到全部统计信息</li></div>";
}

/****************************************************************************
* 函数名: querySkillDetail
* 功能描述: 查询指定技能队列下签入的座席
* 参数: objectPhone - Phone对象
*       skillID - 技能队列ID
* 返回值:Array(座席的ID)
* 相关章节: 2.8.35
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function querySkillDetail(objectPhone,skillID) 
{
	var Phone = objectPhone;
	var agentsInSkillArray = new Array();
	rtn = Phone.QueryAgentsByAcdIDEx(skillID);//查询能处理指定呼叫队列中呼叫的座席工号
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
* 函数名: queryWaitCallsInSkill
* 功能描述: 查询指定技能队列下等待的呼叫信息
* 参数: objectPhone - Phone对象
*       skillID - 技能队列ID
* 返回值:String(排队呼叫信息的HTML格式)
* 相关章节: 2.8.1
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryWaitCallsInSkill(objectPhone,skillID)
{
	var Phone = objectPhone;
	rtn = Phone.QueryDeviceStatusEx(1,skillID);//查询设备状态扩展(参数:设备类型,设备号)
	if(rtn!=0)
	{
		return ;
	}
	if(Phone.DeviceStatus_CallNum == 0)//当查询队列时,该属性表示排队呼叫数
	{
		return "";
	}
	var str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab1'><tbody><tr class='tr2'><td>主叫号码</td><td>被叫号码</td><td>排队时长(秒)</td><td>客户级别</td><td>媒体类型</td><td>呼叫轨迹数</td></tr>";
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