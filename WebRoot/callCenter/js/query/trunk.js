/****************************************************************************
* ������: queryAllTrunk
* ��������: ��ѯ�����м���Ϣ
* ����: objectPhone - Phone����
* ����ֵ:String(�����м���Ϣ��HTML��ʽ)
* ����½�: 2.8.18
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryAllTrunk(objectPhone) 
{
	var Phone = objectPhone;
	rtn = Phone.QueryTrunkTable();//��ѯ�м̱������
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.TrunkNum == 0)
	{
		return ;
	}
	var str = "<br><table cellpadding='0' cellspacing='1' class='tab1'><thead><tr><td colspan='6'>�м���Ϣ�б��б�<td></tr></thead><tbody><tr class='tr2'><td>ģ���</td><td>�м�Ⱥ��</td><td>�м�Ⱥ��</td><td>�м̱��</td><td>״̬</td></tr>";
	for(i=0;i<Phone.TrunkNum;i++)//TrunkNum ����ֵ��ʾ�м���
	{
		rtn = Phone.GetTrunkInfoByIdx(i);//���ȡ�ظ����м̵�������Ϣ
		strtemp = 
			"<tr><td>" + Phone.TrunkInfo_ModuleNo + "</td>" + 
			"<td>" + Phone.TrunkInfo_GroupNo + "</td>" + 
			"<td>" + Phone.TrunkInfo_Direction == 0 ? "���м�" : (Phone.TrunkInfo_Direction == 1 ? "���м�" : "")+ "</td>" + 
			"<td>" + PTrunkInfo_TrunkNo + "</td>" +
			"<td>" + Phone.TrunkInfo_Status == 0 ? "������" : (Phone.TrunkInfo_Status == 1 ? "����" : "") + "</td>" +
			"</tr>";
		str+=strtemp;
	}
	str+= "</tbody></table>";
	return str;
}