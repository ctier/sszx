/****************************************************************************
* ������: queryCustomLevelName
* ��������: ��ѯָ���ͻ���������
* ����: objectPhone - Phone����
*       customLevel - �ͻ�����
* ����ֵ:String(ĳ�ͻ����������)
* ����½�: 
* ��ʷ:
* 1. ����: 2009/12/11
*    ����: Xu JunJiang wx16466
*    �汾: ����
****************************************************************************/
function queryCustomLevelName(objectPhone,customLevel) 
{
	var Phone = objectPhone;
	var customLevelsArray = new Array();
	rtn = Phone.QueryCustomClassNameEx();//��չ��ѯ������Ϣ
	if(rtn!= 0)
	{
		return ;
	}
	if(Phone.CustomClassNum == 0)
	{
		return ;
	}
	for(i=0;i<Phone.CustomClassNum;i++)//CustomClassNum����ֵ��ʾϵͳ���õĿͻ�����ĸ���
	{
		rtn = Phone.GetCustomClassByIdx(i);//���ȡ��ÿ���ͻ��������Ϣ
		if(rtn!=0)
		{
			return ;
		}
		customLevelsArray[i] = Phone.CustomClass_Name;
	}
	return customLevelsArray[customLevel - 1];
}