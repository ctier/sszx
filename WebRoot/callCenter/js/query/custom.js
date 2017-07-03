/****************************************************************************
* 函数名: queryCustomLevelName
* 功能描述: 查询指定客户级别描述
* 参数: objectPhone - Phone对象
*       customLevel - 客户级别
* 返回值:String(某客户级别的描述)
* 相关章节: 
* 历史:
* 1. 日期: 2009/12/11
*    作者: Xu JunJiang wx16466
*    版本: 创建
****************************************************************************/
function queryCustomLevelName(objectPhone,customLevel) 
{
	var Phone = objectPhone;
	var customLevelsArray = new Array();
	rtn = Phone.QueryCustomClassNameEx();//扩展查询级别信息
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.CustomClassNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.CustomClassNum;i++)//CustomClassNum属性值表示系统配置的客户级别的个数
	{
		rtn = Phone.GetCustomClassByIdx(i);//逐个取回每个客户级别的信息
		if(rtn!=0)
		{
			return ;
		}
		customLevelsArray[i] = Phone.CustomClass_Name;
	}
	return customLevelsArray[customLevel - 1];
}