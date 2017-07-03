/****************************************************************************
* 函数名: queryAllTrunk
* 功能描述: 查询所有中继信息
* 参数: objectPhone - Phone对象
* 返回值:String(所有中继信息的HTML格式)
* 相关章节: 2.8.18
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryAllTrunk(objectPhone) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryTrunkTable();//查询中继表的配置
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.TrunkNum == 0)
	{
		return ;
	}
	var str = "<br><table cellpadding='0' cellspacing='1' class='tab1'><thead><tr><td colspan='6'>中继信息列表列表<td></tr></thead><tbody><tr class='tr2'><td>模块号</td><td>中继群号</td><td>中继群向</td><td>中继编号</td><td>状态</td></tr>";
	for(i=0;i<Phone.TrunkNum;i++)//TrunkNum 属性值表示中继数
	{
		rtn = Phone.GetTrunkInfoByIdx(i);//逐个取回各个中继的配置信息
		strtemp = 
			"<tr><td>" + Phone.TrunkInfo_ModuleNo + "</td>" + 
			"<td>" + Phone.TrunkInfo_GroupNo + "</td>" + 
			"<td>" + Phone.TrunkInfo_Direction == 0 ? "入中继" : (Phone.TrunkInfo_Direction == 1 ? "出中继" : "")+ "</td>" + 
			"<td>" + PTrunkInfo_TrunkNo + "</td>" +
			"<td>" + Phone.TrunkInfo_Status == 0 ? "不可用" : (Phone.TrunkInfo_Status == 1 ? "可用" : "") + "</td>" +
			"</tr>";
		str+=strtemp;
	}
	str+= "</tbody></table>";
	return str;
}