/****************************************************************************
* ������: queryAllIvrs
* ��������: ��ѯ����IVR
* ����: objectPhone - Phone����
* ����ֵ:Array(���е�IVR������ID,��@�ָ�)
* ����½�: 2.8.15
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAllIvrs(objectPhone) 
{
	var Phone = objectPhone;
	var ivrsArray = new Array();
	rtn = Phone.QueryIvrID();//��ѯIVR��ID
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.IvrNumber == 0)
	{
		return ;
	}
	for(i=1;i<Phone.IvrNumber;i++)//IvrNumber����ֵ��ʾIVR����
	{
		rtn = Phone.GetIvrIDByIdx(i);//���ȡ��IVR��ID
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
* ������: queryIvrDetail
* ��������: ��ѯָ��IVR������Ϣ
* ����: objectPhone - Phone����
*       ivrID - 
* ����ֵ:String(ĳivr������Ϣ��HTML��ʽ)
* ����½�: 2.8.16
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryIvrDetail(objectPhone,ivrID)
{
	var Phone = objectPhone;
	rtn = Phone.QueryDeviceStatusEx(3,ivrID);//��ѯ�豸״̬��չ(����:�豸����,�豸��)
	
	if(rtn!=0)
	{
		return ;
	}
	if(Phone.DeviceStatus_CallNum == 0)//����ѯ����ʱ,�����Ա�ʾ�ŶӺ�����
	{
		return ;
	}
	var str = "<div style='text-align:center;'><table cellpadding='0' cellspacing='1' class='tab1'><tbody><tr class='tr2'><td>���к���</td><td>���к���</td><td>ͨ��ʱ��(��)</td><td>�ͻ�����</td><td>���й켣��</td></tr>";
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