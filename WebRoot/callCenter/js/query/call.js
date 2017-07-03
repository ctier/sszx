/****************************************************************************
* 函数名: queryAllServices
* 功能描述: 查询所有业务
* 参数: objectPhone - Phone对象
* 返回值:Array(所有业务描述和ID,以@分隔)
* 相关章节: 2.8.11
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAllServices(objectPhone) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryServiceTypeEx();//查询系统中配置的所有业务类型
	if(rtn!= 0)
	{
		return ;
	}
	var servicesArray = new Array();
	if(Phone.ServiceTypeNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.ServiceTypeNum;i++)//ServiceTypeNum 属性值表示系统中配置的业务类型个数
	{
		rtn = Phone.GetServiceTypeByIdx(i);//逐个取回各个业务的配置信息
		servicesArray[i] = Phone.ServiceType_Name + "@" + Phone.ServiceType_No;
	}
	return servicesArray;
}

/****************************************************************************
* 函数名: queryServiceDetail
* 功能描述: 查询指定业务话务信息
* 参数: objectPhone - Phone对象
*       serviceID - 业务类型
* 返回值:String(某业务话务信息的HTML格式)
* 相关章节: 2.8.11
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryServiceDetail(objectPhone,serviceID) {
	var Phone = objectPhone;
	rtn = Phone.QueryCallStatisticsInfoEx(serviceID);
	if(rtn!=0)
	{
		return ;
	}
	var str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab1'><tbody><tr class='tr2'><td>统计点(小时)</td>";
	for(i=0;i<Phone.CallStatInfo_PointNum;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + formatNumber(i) + "-" + formatNumber(i+1) + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr><td>呼叫数</td>";
	for(i=0;i<Phone.CallStatInfo_PointNum;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + Phone.CallStatInfo_CallNums + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr  class='tr2'><td>呼损数</td>";
	for(i=0;i<Phone.CallStatInfo_PointNum;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + Phone.CallStatInfo_CallAbandonNums + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr><td>呼通数</td>";
	for(i=0;i<Phone.CallStatInfo_PointNum;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + (Phone.CallStatInfo_CallNums - Phone.CallStatInfo_CallAbandonNums) + "</td>";
		str+=strtemp;
	}
	str += "</tr></tbody></table></div>";
	return str;
}

/****************************************************************************
* 函数名: queryVdnDetail
* 功能描述: 查询指定媒体类型的话务信息
* 参数: objectPhone - Phone对象
*       mediaType - 媒体类型
* 返回值:String(某媒体类型话务信息的HTML格式)
* 相关章节: 2.8.12
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryVdnDetail(objectPhone,mediaType)
{
	var Phone = objectPhone;
	rtn = Phone.QuerySysCallStatInfoEx(Phone.VdnID);
	if(rtn!=0)
	{
		return ;
	}
	var str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab1'><tbody><tr class='tr2'><td>统计点(小时)</td>";
	for(i=0;i<24;i++)
	{
		//Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + formatNumber(i) + "-" + formatNumber(i+1) + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr><td>呼叫数</td>";
	for(i=0;i<24;i++)
	{
		Phone.QueryCallNumByMedia(mediaType,i);
		strtemp = "<td>" + Phone.QueryCallNumByMedia(mediaType,i) + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr  class='tr2'><td>呼损数</td>";
	for(i=0;i<24;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + Phone.QueryCallAbandonNumByMedia(mediaType,i) + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr><td>呼通数</td>";
	for(i=0;i<24;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + Phone.QueryCallConnectNumByMedia(mediaType,i) + "</td>";
		str+=strtemp;
	}
	str += "</tr></tbody></table></div>";
	return str;
}
