/****************************************************************************
* 函数名: queryAllIvrs
* 功能描述: 查询所有IVR
* 参数: objectPhone - Phone对象
* 返回值:Array(所有的IVR描述和ID,以@分隔)
* 相关章节: 2.8.15
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAllIvrs(objectPhone) 
{
	var Phone = objectPhone;
	var ivrsArray = new Array();
	rtn = Phone.QueryIvrID();//查询IVR的ID
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.IvrNumber == 0)
	{
		return ;
	}
	for(i=1;i<Phone.IvrNumber;i++)//IvrNumber属性值表示IVR个数
	{
		rtn = Phone.GetIvrIDByIdx(i);//逐个取回IVR的ID
		if(rtn!=0)
		{
			return ;
		}
		rtn = Phone.QueryIvrDescriptionByID(Phone.IvrID);
   		ivrsArray[i-1] = Phone.IvrInfo_Description + "@" + Phone.IvrInfo_ID;
	}
	return ivrsArray;
}

/****************************************************************************
* 函数名: queryIvrDetail
* 功能描述: 查询指定IVR呼叫信息
* 参数: objectPhone - Phone对象
*       ivrID - 
* 返回值:String(某ivr呼叫信息的HTML格式)
* 相关章节: 2.8.16
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryIvrDetail(objectPhone,ivrID)
{
	var Phone = objectPhone;
	rtn = Phone.QueryDeviceStatusEx(3,ivrID);//查询设备状态扩展(参数:设备类型,设备号)
	
	if(rtn!=0)
	{
		return ;
	}
	if(Phone.DeviceStatus_CallNum == 0)//当查询队列时,该属性表示排队呼叫数
	{
		return ;
	}
	var str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab1'><tbody><tr class='tr2'><td>主叫号码</td><td>被叫号码</td><td>通话时长(秒)</td><td>客户级别</td><td>呼叫轨迹数</td></tr>";
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
					"<td>" + Phone.CallInfoEx2_LogonTime + "</td>" + 
					"<td>" + queryCustomLevelName(Phone,Phone.CallInfoEx2_Priority) + "</td>" +
					"<td>" + Phone.CallInfoEx2_PartyNum + "</td>" +
					"</tr>"
		:strtemp = "<tr class='tr2'><td>" + Phone.CallInfoEx2_Ani + "</td>" + 
					"<td>" + Phone.CallInfoEx2_DialedNumber + "</td>" + 
					"<td>" + Phone.CallInfoEx2_LogonTime + "</td>" + 
					"<td>" + queryCustomLevelName(Phone,Phone.CallInfoEx2_Priority) + "</td>" +
					"<td>" + Phone.CallInfoEx2_PartyNum + "</td>" +
					"</tr>"
		str+=strtemp;
	}
	str+= "</tbody></table></div>";
	return str;
}