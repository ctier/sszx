var DeviceType = new Array
(
	0,//
	1,//技能队列
	2,//座席
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
    "未签入",
    "空闲状态",
    "预占用状态",
    "占用状态",
    "应答状态",
    "通话状态",
    "工作状态",
    "忙状态",
    "休息状态",
    "学习状态",
    "调整状态"
);

var AgentOpsState = new Array
(
		"座席签出",//ops_null
		"座席签入",//ops_login
		"空闲",//ops_idle,
		"示忙",//ops_busy
		"通话态",//ops_active
		"给话务员分来话后等话务员应答",//ops_wait_answer
		"话务员应答或话务员呼出成功后,等待连接成功的消息",//ops_wait_connect
		"话务员拆线",//ops_release
		"请求释放",//ops_disconnect
		"呼出时等被叫振铃",//ops_wait_alerting
		"三方通话",//ops_tri_talk
		"座席通道坏或座席死机",//ops_fail
		"座席通道或座席已恢复",//ops_ok
		"人工转自动,话务员处于挂起状态,话务员是否被监听或插入",//ops_hungup
		"被监听或插入",//ops_supervise_insert
		"停止被监听或插入,话务员是否被录音",//ops_stop_supervise_insert
		"被录音",//ops_record_begin
		"停止被录音",//ops_record_stop
		"被监视",//Ops_monitor
		"停止被监视",//ops_stop_monitor 
		"监听或插入",//ops_monitor_supervisor_other 
		"停止监听或插入",//ops_stop_monitor_supervisor_other
		"放音",//ops_playvoice
		"停止放音",//ops_stop_playvoice
		"监视",//ops_surveillant
		"停止监视",//ops_stop_surveillant
		"座席休假(休息)",//ops_rest 
		"座席工作态",//ops_working
		"监听,插入呼叫",//ops_listen_insert_call
		"放音",//ops_play
		"停止放音",//ops_stop_play
		"强制签出",//ops_force_out
		"强制示闲",//ops_force_idle
		"强制示忙 "//ops_force_busy
);

var AgentType = new Array
(
    "分机类型",
    "用户线类型,增加兼容类型",
    "1B+D(包括TUA长通电话)",
    "单非长通电话",
    "PC+PHONE",
    "视频2B+D",
    "视频6B+D",
    "忙状态",
    "配置管理员"
);

var MediaType = new Array
(
	"未知类型",//
	"文字交谈",//MEDIA_TYPE_CHAT = 1; 
	"点击通话",//MEDIA_TYPE_WEBPHONE = 2; 
	"护航浏览、表单共享",//MEDIA_TYPE_ESCORT = 3;
	"回呼请求呼叫",//MEDIA_TYPE_CALLBACK = 4; 
	"普通语音电话",//MEDIA_TYPE_PHONE = 5; 
	"电子邮件呼叫",//MEDIA_TYPE_EMAIL = 6;
	"传真呼叫",//MEDIA_TYPE_FAX = 7; 
	"IP 视频呼叫(H.323)",//MEDIA_TYPE_VIDEO = 8; 
	"电子白板",//MEDIA_TYPE_WB = 9; 
	"应用程序共享",//MEDIA_TYPE_APP_SHARE = 10; 
	"文件传输",//MEDIA_TYPE_FILE_TRANSFER = 11; 
	"2B+D ISDN 视频呼叫",//MEDIA_TYPE_VIDEO_2B1D = 12;
	"6B+D ISDN 视频呼叫",//MEDIA_TYPE_VIDEO_6B1D = 13; 
	"OPS呼叫",//MEDIA_TYPE_OPS = 14;
	"预测呼出",//MEDIA_TYPE_PREDICT_OUTBOUND = 15; 
	"预浏览呼出"//MEDIA_TYPE_PREVIEW_OUTBOUND = 16;
);