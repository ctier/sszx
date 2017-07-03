<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>TopEng座席程序</title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="${path }/callCenter/style/main.css" charset="UTF-8">
	
<script type="text/javascript">
var Phone = window.opener.Phone;
		
if(Phone == null || Phone == "" ){
	window.location.reload();
	Phone = window.opener.Phone;
}
	
$(function(){
	var openeye = false;
	var phonenum = PhoneNum.value;
	$("#Loginin").attr("disabled","disabled");
	//初始化
	$("#initral").click(function(){
		debugger;
		Phone.CcsID = ServerType.value;
		Phone.MyID = MyID.value ;
		Phone.MainCcsIP = CCSIP.value;
		Phone.Password = pwd.value;
		Phone.WorkNo = AgentID.value;
		Phone.BackCcsIP = backCCSIP.value;
		Phone.AgentType = AgentType.value;
		Phone.AutoRelease= autorelease.checked;
		Phone.AutoAnswer= autoanswer.checked;
		Phone.AutoReleasePhoneCall="False";
		if(openeye){
			setOpenEye();
			phonenum = Phone.WebVoipAccount ;
		}else{
			phonenum = PhoneNum.value;
		}
		//实际业务使用
		var result = Phone.Initial();
		//测试使用
		//var result = 0;
		if(result == 0){
			alert("操作成功");
			$("#Loginin").attr("disabled",false);
			$("#Loginin").attr("src","/seeyon/callCenter/images/login.png");
		}else{
			var msg = Phone.GetPromptByErrorCode(result);
			alert("错误： -"+result+"-:  "+msg);
		}	
	});
	
	//登陆
	$("#Loginin").click(function(){
		debugger;
		if(autostudy.checked){
			//避免一签入就接到呼叫，可使用以下方法进入学习态
			var rtn=Phone.ChangeAgentStatus(8,1,0);
		}
		//签入
		//设置当前签入的媒体服务器
		var torf = "";		
		if(CTISERVER.checked){
	
			if(MAILM.checked && WEBM.checked){
				torf = "TTT";
			}
			else if(MAILM.checked &&(WEBM.checked=='false')){
				torf = "TTF";
			}
			else if(WEBM.checked &&(MAILM.checked=='false')){
				torf = "TFT";
			}
			else{
				torf = "TFF";
			}
		
		}
		else
		{
			if(MAILM.checked && WEBM.checked){
				torf="FTT";
			}
			else if(MAILM.checked &&(WEBM.checked=='false')){
				torf="FFT";
			}
			else{
				torf="FTF";
			}
		}
		var res=Phone.SignInEx(torf,Phone.AgentType,phonenum);
		//测试使用
		//var res = 0;
		if(res == 0){
			window.opener.changeImg("qr","qr-1.png",true);
			window.opener.changeImg("qc","qc.png",false);
			window.opener.changeImg("sm","sm.png",false);
			window.opener.changeImg("sx","sx.png",false);
			window.opener.changeImg("xx","xx.png",false);
			window.opener.changeImg("xj","xj.png",false);
			window.opener.changeImg("jt","jt.png",false);
			window.opener.changeImg("gd","gd.png",false);
			window.opener.changeImg("bc","bc.png",false);
			window.opener.changeImg("hf","hf.png",false);
			window.opener.changeImg("bh","bh.png",false);
			window.opener.changeImg("zj","zj.png",false);
			window.close();
		}
		window.opener.alertMsg(res);
		
	});
	
	//控制是否使用内置OpenEye
	$("#opencheckbox").click(function(){
		if(opencheckbox.checked){
			openeye = true;
		}else{
			openeye = false;
		}
	});
	
	//设置OPENEYE
	function setOpenEye(){
		 Phone.UseWebVoip=true;
		 Phone.WebVoipSIPServerIP=WebVoipAddress.value;
		 Phone.WebVoipSIPServerPort =WebVoipSIPServerPort.value;
		 Phone.WebVoipNeedRegister =true;
		 Phone.WebVoipBakSIPServerIP=WebVoipBakSIPServerIP.value;
		 Phone.WebVoipBakSIPServerPort=WebVoipBakSIPServerPort.value;
		 Phone.WebVoipAccount=WebVoipAccount.value;
		 Phone.WebVoipPassword=WebVoipPassword.value;
	}
});
		
</script>
<style type="text/css">
<!--
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
-->

body{
	margin:0px;
	padding:0px;
	font-size:12px;
}
#openeyeseting1,#openeyeseting2,#openeyeseting3{
	display:none;
}
</style>
</head>
  
<body>
  	<table cellspacing="0" cellpadding="0" border="0" width="890">
  		<tbody>
  			<tr>
  				<td>
					<img wigth="800" height="200" src="${path }/callCenter/images/login2.jpg">
				</td>
  			</tr>
  			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" width="890">
						<tbody>
							<tr>
							  	<td width="267" rowspan="13" style="padding-left:30px;">
									<img width="209" height="254" src="${path }/callCenter/images/login3.jpg" />
								</td>
							 	<td height="30" colspan="4" bgcolor="#B3DFFF">
									<strong>基本信息</strong>								</td>
							</tr>
							<tr>
								<td width="80" height="30">工号：</td>
								<td width="153" height="30">
									<input type="text" value="" id="AgentID" name="AgentID"/>
							  </td>
								<td width="132" height="30">主服务器地址：</td>
								<td width="258" height="30">
									<input type="text" value="192.168.1.101" id="CCSIP" name="CCSIP"/>
							  </td>
							</tr>
							<tr>
								<td height="30">密码：</td>
							 	<td height="30">
									<input type="text" value="" id="pwd" name="pwd"/>
							  </td>
							  	<td height="30">备服务器地址：</td>
							  	<td height="30"><input name="backCCSIP" type="text" value="192.168.1.103"/></td>
							</tr>
							<tr>
							  	<td height="30">座席类型：</td>
							  	<td height="30">
									<select id="AgentType">
										<option value="4">PC+Phone</option>
										<option value="2">1B+1D</option>
										<option value="5">2B+1D</option>
										<option value="6">6B+1D</option>
									</select>
							  </td>
							  	<td height="30">服务器类型：</td>
							  	<td height="30">
									<select id="ServerType">
										<option value="20">CCS</option>
										<option value="22" selected="selected">MCP</option>
								  </select>
						  	  </td>
							</tr>
							<tr>
							  	<td height="30">座席电话：</td>
							  	<td height="30">
									<input type="text" value="" id="PhoneNum" name="PhoneNum"/>
							  </td>
							  	<td height="30">通讯进程ID：</td>
							  	<td height="30">
									<input type="text" value="42" id="MyID" name="MyID"/>
							  </td>
							</tr>
							<tr>
							  	<td height="30" colspan="4">选择媒体服务器：
									<input type="checkbox" name="CTISERVER" checked="checked"/>
									MS_CTISERVER&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="WEBM" checked="checked"/>
									MS_WEBM&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="MAILM" checked="checked"/>
									MS_MAILM 
							  </td>
							</tr>
							<tr>
							  	<td height="30" colspan="4">
									<input type="checkbox" name="autostudy" />
									签入后不分配来电&nbsp;&nbsp;
									<input type="checkbox" name="autoanswer" checked="checked"/>
									自动应答&nbsp;&nbsp;
									<input type="checkbox" name="autowork" />
									自动进入工作态&nbsp;&nbsp;
									<input type="checkbox" name="autorelease"  checked="checked"/>
									自动释放
							  </td>
							</tr>
							<tr>
							  	<td height="30" colspan="4" align="right" bgcolor="#B3DFFF">
									<input type="checkbox" id="opencheckbox" name="opencheckbox" />
									是否集成内置OpenEye&nbsp;&nbsp;&nbsp;&nbsp;      
							  </td>
							</tr>
							<tr >
								<td height="30">UAP地址：</td>
								<td height="30"><input name="WebVoipAddress" type="text" value="192.168.1.100"/></td>
								<td height="30">备用服务器IP：</td>
								<td height="30"><input name="WebVoipBakSIPServerIP" type="text" /></td>
							</tr>
							<tr >
								<td height="30">端口号：</td>
								<td height="30"><input name="WebVoipSIPServerPort" type="text" value="5060"/></td>
								<td height="30">备用服务器端口：</td>
								<td height="30"><input name="WebVoipBakSIPServerPort" type="text" value="5060"/></td>
							</tr>
							<tr >
								<td height="30">分机号：</td>
								<td height="30"><input name="WebVoipAccount" type="text" value="7004"/></td>
								<td height="30">密码</td>
								<td height="30"><input name="WebVoipPassword" type="text" /></td>
							</tr>
							<tr>
							   <td height="30" colspan="4" align="center">
									<input name="button" type="image" src="${path }/callCenter/images/init.png" id="initral" />
									&nbsp;
									<input name="button" type="image" src="${path }/callCenter/images/logindisabled.png" id="Loginin" />
							  		&nbsp;&nbsp;
							  	</td>
							</tr>
						</tbody>
			  	  </table>
  			    </td>
  			</tr>
		</tbody>
	</table>
</body>
</html>