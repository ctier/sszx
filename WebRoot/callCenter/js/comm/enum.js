var DeviceType = new Array
(
	0,//
	1,//���ܶ���
	2,//��ϯ
	3,//IVR
	4,//ConferenceType
	5,//CallPartyType
	6,//AgentQueueType
	7,//RouteType
	8,//PhoneType
	9,//AgentPrivateQueueType
	10//NetCCType
);

var AgentState = new Array
(
    "δǩ��",
    "����״̬",
    "Ԥռ��״̬",
    "ռ��״̬",
    "Ӧ��״̬",
    "ͨ��״̬",
    "����״̬",
    "æ״̬",
    "��Ϣ״̬",
    "ѧϰ״̬",
    "����״̬"
);

var AgentOpsState = new Array
(
		"��ϯǩ��",//ops_null
		"��ϯǩ��",//ops_login
		"����",//ops_idle,
		"ʾæ",//ops_busy
		"ͨ��̬",//ops_active
		"������Ա��������Ȼ���ԱӦ��",//ops_wait_answer
		"����ԱӦ�����Ա�����ɹ���,�ȴ����ӳɹ�����Ϣ",//ops_wait_connect
		"����Ա����",//ops_release
		"�����ͷ�",//ops_disconnect
		"����ʱ�ȱ�������",//ops_wait_alerting
		"����ͨ��",//ops_tri_talk
		"��ϯͨ��������ϯ����",//ops_fail
		"��ϯͨ������ϯ�ѻָ�",//ops_ok
		"�˹�ת�Զ�,����Ա���ڹ���״̬,����Ա�Ƿ񱻼��������",//ops_hungup
		"�����������",//ops_supervise_insert
		"ֹͣ�����������,����Ա�Ƿ�¼��",//ops_stop_supervise_insert
		"��¼��",//ops_record_begin
		"ֹͣ��¼��",//ops_record_stop
		"������",//Ops_monitor
		"ֹͣ������",//ops_stop_monitor 
		"���������",//ops_monitor_supervisor_other 
		"ֹͣ���������",//ops_stop_monitor_supervisor_other
		"����",//ops_playvoice
		"ֹͣ����",//ops_stop_playvoice
		"����",//ops_surveillant
		"ֹͣ����",//ops_stop_surveillant
		"��ϯ�ݼ�(��Ϣ)",//ops_rest 
		"��ϯ����̬",//ops_working
		"����,�������",//ops_listen_insert_call
		"����",//ops_play
		"ֹͣ����",//ops_stop_play
		"ǿ��ǩ��",//ops_force_out
		"ǿ��ʾ��",//ops_force_idle
		"ǿ��ʾæ "//ops_force_busy
);

var AgentType = new Array
(
    "�ֻ�����",
    "�û�������,���Ӽ�������",
    "1B+D(����TUA��ͨ�绰)",
    "���ǳ�ͨ�绰",
    "PC+PHONE",
    "��Ƶ2B+D",
    "��Ƶ6B+D",
    "æ״̬",
    "���ù���Ա"
);

var MediaType = new Array
(
	"δ֪����",//
	"���ֽ�̸",//MEDIA_TYPE_CHAT = 1; 
	"���ͨ��",//MEDIA_TYPE_WEBPHONE = 2; 
	"���������������",//MEDIA_TYPE_ESCORT = 3;
	"�غ��������",//MEDIA_TYPE_CALLBACK = 4; 
	"��ͨ�����绰",//MEDIA_TYPE_PHONE = 5; 
	"�����ʼ�����",//MEDIA_TYPE_EMAIL = 6;
	"�������",//MEDIA_TYPE_FAX = 7; 
	"IP ��Ƶ����(H.323)",//MEDIA_TYPE_VIDEO = 8; 
	"���Ӱװ�",//MEDIA_TYPE_WB = 9; 
	"Ӧ�ó�����",//MEDIA_TYPE_APP_SHARE = 10; 
	"�ļ�����",//MEDIA_TYPE_FILE_TRANSFER = 11; 
	"2B+D ISDN ��Ƶ����",//MEDIA_TYPE_VIDEO_2B1D = 12;
	"6B+D ISDN ��Ƶ����",//MEDIA_TYPE_VIDEO_6B1D = 13; 
	"OPS����",//MEDIA_TYPE_OPS = 14;
	"Ԥ�����",//MEDIA_TYPE_PREDICT_OUTBOUND = 15; 
	"Ԥ�������"//MEDIA_TYPE_PREVIEW_OUTBOUND = 16;
);