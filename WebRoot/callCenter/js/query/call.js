/****************************************************************************
* ������: queryAllServices
* ��������: ��ѯ����ҵ��
* ����: objectPhone - Phone����
* ����ֵ:Array(����ҵ��������ID,��@�ָ�)
* ����½�: 2.8.11
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAllServices(objectPhone) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryServiceTypeEx();//��ѯϵͳ�����õ�����ҵ������
	if(rtn!= 0)
	{
		return ;
	}
	var servicesArray = new Array();
	if(Phone.ServiceTypeNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.ServiceTypeNum;i++)//ServiceTypeNum ����ֵ��ʾϵͳ�����õ�ҵ�����͸���
	{
		rtn = Phone.GetServiceTypeByIdx(i);//���ȡ�ظ���ҵ���������Ϣ
		servicesArray[i] = Phone.ServiceType_Name + "@" + Phone.ServiceType_No;
	}
	return servicesArray;
}

/****************************************************************************
* ������: queryServiceDetail
* ��������: ��ѯָ��ҵ������Ϣ
* ����: objectPhone - Phone����
*       serviceID - ҵ������
* ����ֵ:String(ĳҵ������Ϣ��HTML��ʽ)
* ����½�: 2.8.11
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryServiceDetail(objectPhone,serviceID) {
	var Phone = objectPhone;
	rtn = Phone.QueryCallStatisticsInfoEx(serviceID);
	if(rtn!=0)
	{
		return ;
	}
	var str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab1'><tbody><tr class='tr2'><td>ͳ�Ƶ�(Сʱ)</td>";
	for(i=0;i<Phone.CallStatInfo_PointNum;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + formatNumber(i) + "-" + formatNumber(i+1) + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr><td>������</td>";
	for(i=0;i<Phone.CallStatInfo_PointNum;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + Phone.CallStatInfo_CallNums + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr  class='tr2'><td>������</td>";
	for(i=0;i<Phone.CallStatInfo_PointNum;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + Phone.CallStatInfo_CallAbandonNums + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr><td>��ͨ��</td>";
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
* ������: queryVdnDetail
* ��������: ��ѯָ��ý�����͵Ļ�����Ϣ
* ����: objectPhone - Phone����
*       mediaType - ý������
* ����ֵ:String(ĳý�����ͻ�����Ϣ��HTML��ʽ)
* ����½�: 2.8.12
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryVdnDetail(objectPhone,mediaType)
{
	var Phone = objectPhone;
	rtn = Phone.QuerySysCallStatInfoEx(Phone.VdnID);
	if(rtn!=0)
	{
		return ;
	}
	var str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab1'><tbody><tr class='tr2'><td>ͳ�Ƶ�(Сʱ)</td>";
	for(i=0;i<24;i++)
	{
		//Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + formatNumber(i) + "-" + formatNumber(i+1) + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr><td>������</td>";
	for(i=0;i<24;i++)
	{
		Phone.QueryCallNumByMedia(mediaType,i);
		strtemp = "<td>" + Phone.QueryCallNumByMedia(mediaType,i) + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr  class='tr2'><td>������</td>";
	for(i=0;i<24;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + Phone.QueryCallAbandonNumByMedia(mediaType,i) + "</td>";
		str+=strtemp;
	}
	str += "</tr><tr><td>��ͨ��</td>";
	for(i=0;i<24;i++)
	{
		Phone.GetCallStatInfoByIdx(i);
		strtemp = "<td>" + Phone.QueryCallConnectNumByMedia(mediaType,i) + "</td>";
		str+=strtemp;
	}
	str += "</tr></tbody></table></div>";
	return str;
}
