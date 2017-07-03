<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="${path }/callCenter/style/css.css" />
<link rel="stylesheet" type="text/css" href="${path }/callCenter/style/style.css" />
<style type="text/css">
body {
	border: #99bbe8 solid 1px;
}
.style1 {
	font-size:12px;
	color:#000000;
	height:27px;
	width:80%;
	padding:0 0 0 18px;
}
li{
	float:left;
}
#tid{
	float:left;
	position:absolute;
	z-index:1000;
}
</style>
<script type="text/javascript">
//打印日志
function print_log(logs){
	logs = "【"+new Date().toLocaleString()+"】"+logs+"<br />";
	alert(logs);	
}
//弹出座席操作成功与否的对话框
function alertMsg(Result)
{
	if(Result=='0')
	{
		alert("操作成功");
	}
	else
	{
		var msg = Phone.GetPromptByErrorCode(Result);
		alert("错误： -"+Result+"-:  "+msg);
	}
}
//弹出签入页面
function signIn()
{
	var heightLength = 600;
	var widthLength = 850;
	var topLength= screen.availHeight/2-heightLength/2;
	var leftLength= screen.availWidth/2-widthLength/2;
	var url = "/seeyon/appeal/callCenter.do?method=join";
    window.open(url,"_blank","height=" + heightLength +", width=" + widthLength + ", top=" + topLength + ", left=" + leftLength+ ", toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no");
}
//签出
function OnsignOut()
{
	var rtn = Phone.SignOutEx();
	if(rtn == 0)
	{
		Phone.EndTask();
		changeImg("qr","qr.png",false);
		changeImg("qc","qc-1.png",true);
		changeImg("sx","sx-1.png",true);
		changeImg("sm","sm-1.png",true);
		changeImg("xx","xx-1.png",true);
		changeImg("xj","xj-1.png",true);
		changeImg("jt","jt-1.png",true);
		changeImg("gd","gd-1.png",true);
		changeImg("bc","bc-1.png",true);
		changeImg("hf","hf-1.png",true);
		changeImg("bh","bh-1.png",true);
		changeImg("zj","zj-1.png",true);
		alert("签出成功");
	}
}
//接听电话
function button_Answer() 
{
	//应答某一媒体类型的呼叫
	var rtn = Phone.AnswerEx(5);
	alertMsg(rtn);
}
//释放呼叫
function button_ReleaseAnswer()
{
	//释放呼叫，可释放各种媒体类型的呼叫。
	var rtn=Phone.ReleaseCallEx(5);
	alertMsg(rtn);
}
//示忙
function button_Busy()
{
	//正式接口调用使用
	var rtn = Phone.SayBusy();
	//var rtn = 0;
	alertMsg(rtn);
	if(rtn == 0){
		changeImg("sm","sm-1.png",true);
	}
}
//示闲
function button_Xian()
{
	var rtn = Phone.SayFree(5);
	alertMsg(rtn);
	if(rtn == 0){
		changeImg("sx","sx-1.png",true);
	}
}
//请假
function button_Leave()
{
	//坐席请求休息，请求休息的最大时间为3600秒
	var rtn=Phone.Rest(3600);
	alertMsg(rtn);
}
//销假
function button_CancelLeave()
{
	var rtn=Phone.CancelRest();
	alertMsg(rtn);
}
//拨号
function btnCallOut_Onclick(){
	/**
	*	参数说明：<br>
	*	1、媒体类型：5为语音呼叫
	*	2、媒体类型：呼出时的主叫号码
	*	3、呼出的被叫电话号码、传真号码或收件人Email地址
	*	4、小交换机引示号，语音呼叫为空
	*	5、呼出方式，普通呼出为0
	*	6、呼出技能标识，Integer（IN），技能队列ID
	*	7、附加参数存放的缓冲区WideString（IN）。语音呼出时：应该是空字符串
	*/
	var num = $("#txtDestDevice").val();
	var rtn=Phone.CallOutEx3(5,"12300",num,"",0,"","");
	alertMsg(rtn);
}
//转接
function btnTrans_Ondblclick(){
	/**
	*  参数说明：<br>
	*  1、呼叫媒体类型,语音呼叫为5
	*  2、转移模式Integer --> 0：释放转。2：成功转。3：指定转。4：合并转。
	*  3、呼叫媒体类型,语音呼叫为5
	*/
	var num = $("#txtDestWorkNo").val();
	var rtn=Phone.TransToAgent(5,0,num);
	alertMsg(rtn);
}
//保持
function button_holdEx(){
	var rtn = CccX.HoldEx();
	alertMsg(rtn);
}
//恢复
function btnTrans_Ondblclick(){
	/**
	*  参数说明：<br>
	*  1、呼叫媒体类型,语音呼叫为5
	*  2、转移模式Integer --> 0：释放转。2：成功转。3：指定转。4：合并转。
	*  3、呼叫媒体类型,语音呼叫为5
	*/
	var num = $("#txtDestWorkNo").val();
	var rtn=Phone.TransToAgent(5,0,num);
	alertMsg(rtn);
}
//换图片
function changeImg(ids,pic,boolean){
	var obj1 = document.getElementById(ids);
	var statssrc = obj1.src;
	var statspath = statssrc.substring(0,statssrc.indexOf("images")+7);
	obj1.src = statspath+pic;
	obj1.disabled = boolean;
}
</script>
<!--  签入成功时触发此事件-->
<script type="text/javascript" for="Phone" event="OnSignInExSuccess(p1)">
<!--
	print_log("签入成功");
	print_log('OnSignInExSuccess');
//-->
</script>
<!--  签入失败时触发此事件-->
<script type="text/javascript" FOR=Phone EVENT=OnSignInExFailure(P1)>
<!--
	print_log("签入失败");
	print_log('OnSignInExFailure');
//-->
</script>
<!--  签出成功时触发此事件-->
<script LANGUAGE=javascript FOR=Phone EVENT=OnSignOutExSuccess(p)>
<!--
	print_log("签出成功");
	print_log('OnSignOutExSuccess');
//-->
</script>
<!--  签出失败时触发此事件-->
<script language=javascript for=Phone event=OnSignOutExFailure(p)>
<!--
	print_log("签出失败");
	print_log('OnSignOutExFailure');
//-->
</script>
<!--内部求助成功触发此事件 -->
<script LANGUAGE=javascript FOR=Phone EVENT=OnInternalHelpSuccess()>
<!--
    AddResult("内部求助成功");
	AddResult('OnInternalHelpSuccess');
//-->
</script>
<!--三方求助失败时触发此事件  -->
<script LANGUAGE=javascript FOR=Phone EVENT=OnInternalHelpFailure()>
<!--
    AddResult("求助失败");
	AddResult('OnInternalHelpFailure');
//-->
</script>
<!-- 取回呼叫成功时触发此事件  -->
<script LANGUAGE=javascript FOR=Phone EVENT=OnInternalHelpRefused()>
<!--
	AddResult("取回呼叫成功");
	AddResult('OnInternalHelpRefused');
//-->
</script>
<!-- 请求三方通话成功时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnConfJoinSuccess()>
<!--
    AddResult("请求三方通话成功");
	AddResult('OnConfJoinSuccess');
//-->
</script>
<!-- 三方通话成功时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnConfJoinSuccTalk()>
<!--
    AddResult("三方通话成功");
	AddResult('OnConfJoinSuccTalk');
//-->
</script>
<!-- 三方通话失败时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnConfJoinFailure()>
<!--
    AddResult("三方通话失败");
	AddResult('OnConfJoinFailure');
//-->
</script>
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnAnswerRequestEx(mediatype)>
<!--
 AddResult("有来电呼入");
//-->
</SCRIPT>
<!-- 当座席休息结束时间到达时,触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnRestTimeOut(resttime)>
<!--
	alert("休息时间已到,请取消休息");
	AddResult("OnRestTimeOut");
//-->
</script>
<!--设置呼叫数据成功时触发此事件  -->
<!--设置呼叫数据失败时触发此事件  -->
<!--获取呼叫数据成功时触发此事件  -->
<!--获取呼叫数据失败时触发此事件  -->
<!--信号检测事件  -->
<script language=javascript FOR=Phone EVENT=OnCallOutDetectResult(result)>
<!--
AddResult("OnCallOutDetectResult");
alert(result);
//-->
</script>
<!-- 向用户报音成功时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnReportSoundSuccess()>
<!--
AddResult("OnReportSoundSuccess");
//-->
</script>
<!-- 与用户恢复通话时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnReportSndEndTalk()>
<!--
AddResult("OnReportSndEndTalk");
//-->
</script>
<!-- 向用户报音失败时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnReportSoundFailure()>
<!--
AddResult("OnReportSoundFailure");
//-->
</script>
<!-- 保持成功将顺序触发OnIsTalkingChanged、OnEndMuteUserSuccess、OnHoldSuccess 事件 -->
<script language=javascript FOR=Phone EVENT=OnIsTalkingChanged()>
<!--
AddResult("OnIsTalkingChanged");
//-->
</script>
<!-- OnEndMuteUserSuccess 事件只有在保持呼叫前座席处于静音状态时才触发 -->
<script language=javascript FOR=Phone EVENT=OnEndMuteUserSuccess()>
<!--
AddResult("OnEndMuteUserSuccess");
//-->
</script>
<script language=javascript FOR=Phone EVENT=OnHoldSuccess()>
<!--
AddResult("OnHoldSuccess");
//-->
</script>

<!-- 取保持成功时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnGetHoldSuccess()>
<!--
AddResult("OnGetHoldSuccess");
//-->
</script>
<!-- 取保持失败时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnGetHoldFailure()>
<!--
AddResult("OnGetHoldFailure");
//-->
</script>

<!--退出休息成功时触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnCancelRestSuccess()>
<!--
AddResult("OnCancelRestSuccess");
//-->
</script>
<!--退出休息失败时触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnCancelRestFailure()>
<!--
AddResult("OnCancelRestFailure");
//-->
</script>

<!--调用休息成功时触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnRestSuccess()>
<!--
AddResult("OnRestSuccess");
//-->
</script>
<!--调用休息失败时触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnRestFailure()>
<!--
AddResult("OnRestFailure");
//-->
</script>
<!--开始休息成功时触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnStartRest()>
<!--
AddResult("OnStartRest");
//-->
</script>
<!--开始静音成功时触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnBeginMuteUserSuccess()>
<!--
AddResult("OnBeginMuteUserSuccess");
//-->
</script>
<!-- 示忙成功时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnSayBusySuccess()>
<!--
AddResult("OnSayBusySuccess");
//-->
</script>
<!-- 示忙失败时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnSayBusyFailure()>
<!--
AddResult("OnSayBusyFailure");
//-->
</script>
<!-- 示闲成功时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnSayFreeSuccess()>
<!--
AddResult("OnSayFreeSuccess");
//-->
</script>
<!-- 示闲失败时触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnSayFreeFailure()>
<!--
AddResult("OnSayFreeFailure");
//-->
</script>
<!--  开始静音失败时触发此事件-->
<script language=javascript FOR=Phone EVENT=OnBeginMuteUserFailure()>
<!--
AddResult("OnBeginMuteUserFailure");
//-->
</script>
<!--  结束静音成功时触发此事件-->
<script language=javascript FOR=Phone EVENT=OnEndMuteUserSuccess()>
<!--
AddResult("OnEndMuteUserSuccess");
//-->
</script>
<!--结束静音失败时触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnEndMuteUserFailure()>
<!--
AddResult("OnEndMuteUserFailure");
//-->
</script>
<script language=javascript FOR=Phone EVENT=OnAnswerRequestEx()>
<!--
 AddResult('有来电');
//-->
</script>
<!-- 应答成功触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnAnswerExSuccess(p1)>
<!--
AddResult('OnAnswerExSuccess');
//-->
</script>
<!-- 应答失败触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnAnswerExFailure(p1)>
<!--
AddResult('OnAnswerExFailure');
//-->
</script>
<!-- 外呼成功触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnCallOutSuccess()>
<!--
AddResult("OnCallOutSuccess");
//-->
</script>
<!-- 外呼失败触发此事件 -->
<script language=javascript FOR=Phone EVENT=OnCallOutFailure()>
<!--
AddResult("OnCallOutFailure()");
//-->
</script>
<!-- 外呼成功并且被叫开始通话 -->
<script language=javascript FOR=Phone EVENT=OnCallOutSuccTalk()>
<!--
AddResult("OnCallOutSuccTalk");
//-->
</script>
<!--申请会场成功时,触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnRequestConferenceSuccess()>
<!--
AddResult("OnRequestConferenceSuccess");
//-->
</script>
<!--申请会场失败时,触发此事件  -->
<script language=javascript FOR=Phone EVENT=OnReleaseConferenceSuccess()>
<!--
AddResult("OnReleaseConferenceSuccess");
//-->
</script>
<!--释放会场成功按顺序触发OnReleaseExSuccess和OnReceiveAgentStateInfo事件  -->
<script language=javascript FOR=Phone EVENT=OnReleaseExSuccess()>
<!--
AddResult("OnReleaseExSuccess");
//-->
</script>
<script language=javascript FOR=Phone EVENT=OnReceiveAgentStateInfo()>
<!--
AddResult("OnReceiveAgentStateInfo");
//-->
</script>
<!-- 座席加入会场成功 -->
<script language="javascript" FOR=Phone EVENT=OnAddAgentToConferenceSuccess()>
<!--
AddResult("OnAddAgentToConferenceSuccess");
//-->
</script>
<!--座席加入会场失败  -->
<script language="javascript" FOR=Phone EVENT=OnAddAgentToConferenceFailure()>
<!--
AddResult("OnAddAgentToConferenceFailure");
//-->
</script>
<!--用户加入会场失败  -->
<script language="javascript" FOR=Phone EVENT=OnAddClientToConferenceSuccess()>
<!--
AddResult("OnAddClientToConferenceSuccess");
//-->
</script>
<!--用户加入会场失败  -->
<script language="javascript" FOR=Phone EVENT=OnAddClientToConferenceFailure()>
<!--
AddResult("OnAddClientToConferenceFailure");
//-->
</script>
<!--内呼时，被呼叫座席接收到请求应答的消息会触发此事件  -->
<script language="javascript" FOR=Phone EVENT=OnCallInnerSuccess()>
<!--
AddResult("OnCallInnerSuccess");
//-->
</script>
<!--内呼时，被呼叫座席应答会触发此事件  -->
<script language="javascript" FOR=Phone EVENT=OnCallInnerSuccTalk()>
<!--
AddResult("OnCallInnerSuccTalk");
//-->
</script>
<!-- 内呼失败时将触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnCallInnerFailure()>
<!--
AddResult("OnCallInnerFailure");
//-->
</script>
<!-- 呼叫进入座席私有队列时将触发此事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnPrivateQueueCallIn(iCallID,caller,called,mediaType)>
<!--
 AddResult("呼叫进入座席私有队列");
//-->
</SCRIPT>
<!-- 呼叫离开座席私有队列时将触发此事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnPrivateQueueCallOut(pCallID,caller,called,mediaType)>
<!--
 AddResult("呼叫离开座席私有队列");
//-->
</SCRIPT>
<!-- 发布公告成功时触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnNotifyBulletinSuccess()>
<!--
AddResult("OnNotifyBulletinSuccess");
//-->
</script>
<!-- 发布公告成功时触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnNotifyBulletinFailure()>
<!--
AddResult("OnNotifyBulletinFailure");
//-->
</script>
<!--转移失败触发此事件  -->
<script language="javascript" FOR=Phone EVENT=OnTransInnerFailure()>
<!--
AddResult("OnTransInnerFailure");
//-->
</script>
<!--转移成功触发此事件  -->
<script language="javascript" FOR=Phone EVENT=OnTransInnerSuccess()>
<!--
AddResult("OnTransInnerSuccess");
//-->
</script>
<!-- 保持呼叫释放事件 -->
<script language="javascript" FOR=Phone EVENT=OnHoldCallRelease()>
<!--
AddResult("OnHoldCallRelease");
//-->
</script>
<!--转IVR成功时触发此事件  -->
<script language="javascript" FOR=Phone EVENT=OnRedirectToAutoSuccess()>
<!--
AddResult("OnRedirectToAutoSuccess");
//-->
</script>
<!--转IVR失败时触发此事件  -->
<script language="javascript" FOR=Phone EVENT=OnRedirectToAutoFailure()>
<!--
AddResult("OnRedirectToAutoFailure");
//-->
</script>
<!--挂起转IVR返回座席时触发此事件  -->
<script language="javascript" FOR=Phone EVENT=OnReturnFromIvr()>
<!--
AddResult("OnReturnFromIvr");
alert(Phone.QueryCallDataEx(5));
//-->
</script>
<!-- 成功转成功时触发此事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnTransferResultNotify(cause)>
<!--
	if(cause=='0')
	{
		alert("操作成功");
	}
	else
	{
		alert("操作失败");
	}
	AddResult("OnTransferResultNotify");
//-->
</SCRIPT>
<!--转移失败触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnTransInnerFailure()>
<!--
AddResult("OnTransInnerFailure");
//-->
</script>
<!--转移成功触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnTransInnerSuccess()>
<!--
AddResult("OnTransInnerSuccess");
//-->
</script>
<!-- 转队列时，首先触发OnBeforeTrans 事件，然后开始执行转队列操作。-->
<script language="javascript" FOR=Phone EVENT=OnBeforeTrans()>
<!--
AddResult("OnBeforeTrans");
//-->
</script>
<!--转队列失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnRedirectToOtherFailure()>
<!--
AddResult("OnRedirectToOtherFailure");
//-->
</script>
<!--转队列成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnRedirectToOtherSuccess()>
<!--
AddResult("OnRedirectToOtherSuccess");
//-->
</script>
<!--呼叫转移成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnTransOutSuccess()>
<!--
AddResult("OnTransOutSuccess");
//-->
</script>
<!--呼叫转移失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnTransOutFailure>
<!--
AddResult("OnTransOutFailure");
//-->
</script>
<!--座席设置自己的呼叫转移，包括取消呼叫转移，设置前转或者忙转成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnSetTransferSuccess()>
<!--
AddResult("OnSetTransferSuccess");
//-->
</script>
<!--座席设置自己的呼叫转移，包括取消呼叫转移，设置前转或者忙转成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnSetTransferFailure()>
<!--
AddResult("OnSetTransferFailure");
//-->
</script>
<!--监听话务员通话成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnSuperviseTrunkSuccess()>
<!--
AddResult("OnSuperviseTrunkSuccess");
//-->
</script>
<!--监听话务员通话失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnSuperviseTrunkFailure()>
<!--
AddResult("OnSuperviseTrunkFailure");
//-->
</script>
<!--插入成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnInsertSuccess()>
<!--
AddResult("OnInsertSuccess");
//-->
</script>
<!--插入失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnInsertFailure()>
<!--
AddResult("OnInsertFailure");
//-->
</script>
<!--质检员将话务员强制示闲成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnAgentForceIdleSuccess()>
<!--
AddResult("OnAgentForceIdleSuccess");
//-->
</script>
<!--质检员将话务员强制示闲失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnAgentForceIdleFailure()>
<!--
AddResult("OnAgentForceIdleFailure");
//-->
</script>
<!--质检员将座席强制示忙成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnAgentForceBusySuccess()>
<!--
AddResult("OnAgentForceBusySuccess");
//-->
</script>
<!--质检员将座席强制示忙失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnAgentForceBusyFailure()>
<!--
AddResult("OnAgentForceBusyFailure");
//-->
</script>
<!--质检员将座席强制签出成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnAgentForceOutSuccess()>
<!--
AddResult("OnAgentForceOutSuccess");
//-->
</script>
<!--质检员将座席强制签出失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnAgentForceOutFailure()>
<!--
AddResult("OnAgentForceOutFailure");
//-->
</script>
<!--质检员将座席强制签出指定媒体服务器成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnAgentForceOutSuccess()>
<!--
AddResult("OnAgentForceOutSuccess");
//-->
</script>
<!--质检员将座席强制签出指定媒体服务器失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnAgentForceOutFailure()>
<!--
AddResult("OnAgentForceOutFailure");
//-->
</script>
<!--质检员对指定座席的话路进行拦截，拦截成功时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnInterceptSuccess()>
<!--
AddResult("OnInterceptSuccess");
//-->
</script>
<!--质检员对指定座席的话路进行拦截，拦截失败时，触发此事件 -->
<script language="javascript" FOR=Phone EVENT=OnInterceptFailure()>
<!--
AddResult("OnInterceptFailure");
//-->
</script>
<!-- 录音成功事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnRecordSuccess()>
<!--
AddResult("OnRecordSuccess");
//-->
</SCRIPT>
<!-- 录音失败事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnRecordFailure()>
<!--
AddResult("OnRecordFailure");
//-->
</SCRIPT>
<!-- 停止录音成功事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnStopRecordSuccess()>
<!--
AddResult("OnStopRecordSuccess");
//-->
</SCRIPT>
<!-- 停止录音失败事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnStopRecordFailure()>
<!--
AddResult("OnStopRecordFailure");
//-->
</SCRIPT>
<!-- 放音成功事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnPlaySuccess()>
<!--
AddResult("OnPlaySuccess");
//-->
</SCRIPT>
<!-- 放音失败事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnPlayFailure()>
<!--
AddResult("OnPlayFailure");
//-->
</SCRIPT>
<!-- 停止播放成功事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnStopPlaySuccess()>
<!--
AddResult("OnStopPlaySuccess");
//-->
</SCRIPT>
<!-- 停止播放失败事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnStopPlayFailure()>
<!--
AddResult("OnStopPlayFailure");
//-->
</SCRIPT>
<!-- 暂停播放成功事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnPausePlaySuccess()>
<!--
AddResult("OnPausePlaySuccess");
//-->
</SCRIPT>
<!-- 暂停播放失败事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnPausePlayFailure()>
<!--
AddResult("OnPausePlayFailure");
//-->
</SCRIPT>
<!-- 继续放音成功事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnContinuePlaySuccess()>
<!--
AddResult("OnContinuePlaySuccess");
//-->
</SCRIPT>
<!-- 继续放音失败事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnContinuePlayFailure()>
<!--
AddResult("OnContinuePlayFailure");
//-->
</SCRIPT>
<!-- 快进成功事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnForePlaySuccess()>
<!--
AddResult("OnForePlaySuccess");
//-->
</SCRIPT>
<!-- 快进失败事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnForePlayFailure()>
<!--
AddResult("OnForePlayFailure");
//-->
</SCRIPT>
<!-- 快退成功事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnBackPlaySuccess()>
<!--
AddResult("OnBackPlaySuccess");
//-->
</SCRIPT>
<!-- 快退失败事件 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnBackPlayFailure()>
<!--
AddResult("OnBackPlayFailure");
//-->
</SCRIPT>
<!-- 定时触发 -->
<SCRIPT LANGUAGE=javascript FOR=Phone EVENT=OnTimer()>
<!--
AddResult("定时器成功触发");
//-->
</SCRIPT>
</head>

<body>
<div class="top">
	<%-- <ul>
		<li><img id="qr" src="${path }/callCenter/images/qr.png" title="签入" onclick="signIn()"/></li>
		<li><img id="qc" src="${path }/callCenter/images/qc-1.png" title="签出" onclick="OnsignOut()" disabled="disabled"/></li>
		<li><img id="sm" src="${path }/callCenter/images/sm-1.png" title="示忙" onclick="button_Busy()" disabled="disabled"/></li>
		<li><img id="sx" src="${path }/callCenter/images/sx-1.png" title="示闲" onclick="button_Xian()" disabled="disabled"/></li>
		<li><img id="xx" src="${path }/callCenter/images/xx-1.png" title="休息" onclick="button_Leave()" disabled="disabled"/></li>
		<li><img id="xj" src="${path }/callCenter/images/xj-1.png" title="销假" onclick="button_CancelLeave()" disabled="disabled"/></li>
		<li><img id="jt" src="${path }/callCenter/images/jt-1.png" title="接听" onclick="button_Answer()" disabled="disabled"/></li>
		<li><img id="gd" src="${path }/callCenter/images/gd-1.png" title="挂断" onclick="button_ReleaseAnswer()" disabled="disabled"/></li>
		<li><img id="bc" src="${path }/callCenter/images/bc-1.png" title="保持" onclick="button_holdEx()" disabled="disabled"/></li>
		<li><img id="hf" src="${path }/callCenter/images/hf-1.png" title="恢复" onclick="" disabled="disabled"/></li>
	</ul>
	<div id="tid" style="width:60%">
		<table width="80%" align="center" border="0">
			<tr>
				<td>拨号:</td>
				<td>&nbsp;<input type="text" id="txtDestDevice"></td>
				<td>
					<img onclick="btnCallOut_Onclick()" id="bh" title="拨号" src="${path }/callCenter/images/bh-1.png" disabled="disabled">
				</td>
				<td>&nbsp;&nbsp;转接:</td>
				<td>&nbsp;<input type="text" id="txtDestWorkNo"></td>
				<td>
					<img onclick="btnTrans_Ondblclick()" id="zj" title="转接" src="${path }/callCenter/images/zj-1.png" disabled="disabled">
				</td>
			</tr>
			<tr>
				<td class="style1" colSpan="3" noWrap>来电显示：<span id="call_no"></span> </td>
				<td class="style1" colSpan="3" noWrap>来电时间：<span id="call_time"></span></td>
			</tr>
		</table>
	</div> --%>
	<table>
		<tr>
			<td><img id="qr" src="${path }/callCenter/images/qr.png" title="签入" onclick="signIn()"/></td>
			<td><img id="qc" src="${path }/callCenter/images/qc-1.png" title="签出" onclick="OnsignOut()" disabled="disabled"/></td>
			<td><img id="sm" src="${path }/callCenter/images/sm-1.png" title="示忙" onclick="button_Busy()" disabled="disabled"/></td>
			<td><img id="sx" src="${path }/callCenter/images/sx-1.png" title="示闲" onclick="button_Xian()" disabled="disabled"/></td>
			<td><img id="xx" src="${path }/callCenter/images/xx-1.png" title="休息" onclick="button_Leave()" disabled="disabled"/></td>
			<td><img id="xj" src="${path }/callCenter/images/xj-1.png" title="销假" onclick="button_CancelLeave()" disabled="disabled"/></td>
			<td><img id="jt" src="${path }/callCenter/images/jt-1.png" title="接听" onclick="button_Answer()" disabled="disabled"/></td>
			<td><img id="gd" src="${path }/callCenter/images/gd-1.png" title="挂断" onclick="button_ReleaseAnswer()" disabled="disabled"/></td>
			<td><img id="bc" src="${path }/callCenter/images/bc-1.png" title="保持" onclick="button_holdEx()" disabled="disabled"/></td>
			<td><img id="hf" src="${path }/callCenter/images/hf-1.png" title="恢复" onclick="" disabled="disabled"/></td>
			<td>拨号:</td>
			<td><input type="text" id="txtDestDevice"></td>
			<td><img onclick="btnCallOut_Onclick()" id="bh" title="拨号" src="${path }/callCenter/images/bh-1.png" disabled="disabled"></td>
			<td>转接:</td>
			<td><input type="text" id="txtDestWorkNo"></td>
			<td><img onclick="btnTrans_Ondblclick()" id="zj" title="转接" src="${path }/callCenter/images/zj-1.png" disabled="disabled"></td>
		</tr>
	</table>
</div>
<OBJECT style="LEFT: 0px; VISIBILITY: hidden; TOP: 0px"
	classid=clsid:014D83A5-7E35-11D3-8AF9-00C0DF245E51 name=Phone VIEWASTEXT>
	<PARAM NAME="CcsID" VALUE="20">
	<PARAM NAME="MyID" VALUE="40">
	<PARAM NAME="MainCcsIP" VALUE="">
	<PARAM NAME="BackCcsIP" VALUE="">
	<PARAM NAME="WorkNo" VALUE="0">
	<PARAM NAME="DesktopNo" VALUE="1000">
	<PARAM NAME="Password" VALUE="">
	<PARAM NAME="TimeOut" VALUE="5000">
	<PARAM NAME="RecordFileDir" VALUE="c:\temp">
	<PARAM NAME="CardType" VALUE="3">
	<PARAM NAME="AutoAnswer" VALUE="0">
	<PARAM NAME="AutoRelease" VALUE="0">
	<PARAM NAME="AutoReconnect" VALUE="0">
	<PARAM NAME="HaveBell" VALUE="-1">
	<PARAM NAME="BellTime" VALUE="3">
	<PARAM NAME="PlayStep" VALUE="2">
	<PARAM NAME="FreeStatus" VALUE="0">
	<PARAM NAME="TimerEnabled" VALUE="0">
	<PARAM NAME="TimerInterval" VALUE="1000">
	<PARAM NAME="MediaPlay" VALUE="0">
	<PARAM NAME="MediaFileName" VALUE="">
	<PARAM NAME="Version" VALUE="3">
	<PARAM NAME="AgentType" VALUE="2">
	<PARAM NAME="ConvoyDirection" VALUE="1">
	<PARAM NAME="ConvoyMode" VALUE="1">
	<PARAM NAME="WFSMode" VALUE="2">
	<PARAM NAME="PMSMode" VALUE="1">
	<PARAM NAME="AutoRecord" VALUE="0">
	<PARAM NAME="PlayMode" VALUE="1">
	<PARAM NAME="StreamServerAddr" VALUE="">
	<PARAM NAME="StreamFastPlayStep" VALUE="3">
	<PARAM NAME="IsQccing" VALUE="0">
	<PARAM NAME="IsAutoEnterWork" VALUE="0">
	<PARAM NAME="Account" VALUE="">
	<PARAM NAME="AccountPassword" VALUE="">
	<PARAM NAME="AutoReleasePhoneCall" VALUE="0">
	<PARAM NAME="PlaySystem" VALUE="0">
	<PARAM NAME="UseWebVoip" VALUE="0">
	<PARAM NAME="WebVoipSIPServerIP" VALUE="">
	<PARAM NAME="WebVoipSIPServerPort" VALUE="5060">
	<PARAM NAME="WebVoipNeedRegister" VALUE="-1">
	<PARAM NAME="WebVoipAccount" VALUE="">
	<PARAM NAME="WebVoipPassword" VALUE="">
	<PARAM NAME="NiceCLSIP" VALUE="">
	<PARAM NAME="NiceCLSPort" VALUE="2050">
	<PARAM NAME="bCanHold" VALUE="0">
	<PARAM NAME="bCanIntercept" VALUE="0">
	<PARAM NAME="WebVoipBakSIPServerIP" VALUE="">
	<PARAM NAME="WebVoipBakSIPServerPort" VALUE="5060">
	<PARAM NAME="wecc_win_objA" VALUE="">
	<PARAM NAME="wecc_win_objB" VALUE="">
	<PARAM NAME="email_win_obj" VALUE="">
</OBJECT>
</body>
</html>
