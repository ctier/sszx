<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 
<link rel="stylesheet" type="text/css" href="${path }/callCenter/style/css.css"/>
<link rel="stylesheet" type="text/css" href="${path }/callCenter/style/style.css"/>
 -->
<script type="text/javascript" src="${path }/callCenter/js/area.js"></script>
<script type="text/javascript" src="${path }/callCenter/js/classify.js"></script>
<script type="text/javascript" src="${path }/callCenter/js/business.js"></script>
<script type="text/javascript" src="${path }/callCenter/js/company.js"></script>
<script type="text/javascript" src="${path }/apps_res/appealAccept/js/main.js"></script>
<title>电话受理中心</title>
<style type="text/css">
	#container{
		border:1px solid #eeeeee;
		margin: auto;
		width: 80%;
		overflow: hidden;
	}
	#container .box1{
		/*border:1px solid green;*/
	}

	#container .box1 .box1_left{
		float: left;
		width: 65%;
	}
	#container .form-group{
		margin-top: 10px;
	}
	#container #userAdd{
		width: 81%;
	}
	
	#container .labelTop{
		display: inline-block;
	    width: 130px;
	    text-align: right;
	}
	.labelBottom{
		margin-left: 10px;
	}

	.s_province{
		width: 100px;
	}
	.footerBtn{
		width: 20%;
		margin: auto;
		margin-top: 20px;
	}

	.footerBtn input{
		width: 50px;
		height: 25px;
	}
	.textarea{
		width: 80%;
		height: 100px;
		vertical-align: middle;
	}
	h3{
		margin-left: 60px;
		background-color: #BFEFFF;
	}
	#beizhu{
		vertical-align: middle;
    	display: inline-block;
	}
	input,textarea{
		background-color: #7CCD7C;
	}
	#callDate{
		margin-top: -30px;
	}
	#titleTop p{
		width:20%;
		margin:auto;
		font-size:150%;
		font-weight:bold;
	}
</style>
</head>
<body>
	<div id="container">
		<form id="telForm">
			<div class="box1" id="titleTop">
				<p>电话受理记录单</p>
			</div>
			<div class="box1">
				<div class="form-group box1_left">
					<label for="ldsj" class="labelTop" id="callDate">受理员:</label>
					 <input type="text" id="sly" name="sly" value="${member.name}"><span style="color:red">*</span>
					 <input type="hidden" id="slyId" name="slyId" value="${member.id}">
				</div>
				<div class="form-group">
					<label for="ldsj" class="labelTop" id="callDate">来电号码:</label>
					 <input type="text" id="ldNum"><span style="color:red">*</span>
				</div>
				<div class="form-group box1_left">
					<label for="ldsj" class="labelTop" id="callDate">来电时间:</label>
					 <input type="text" id="ldsj" name="ldsj" value=""><span style="color:red">*</span>
				</div>
				<div class="form-group" id="">
				 	<label for="appealSource" class="labelTop">申诉来源:</label>
				 	<select id="appealSource" name="appealSource" disabled="true">
						<option value="">请选择</option>
						<option value="电话" selected="selected">电话</option>
						<option value="传真">传真</option>
						<option value="短信">短信</option>
						<option value="电子邮件">电子邮件</option>
						<option value="网站">网站</option>
						<option value="信函">信函</option>
						<option value="来访">来访</option>
						<option value="微信">微信</option>
					</select>
				</div>
			</div>
			<h3 style="margin-top:15px;">用户信息</h3>
			<div class="box1">
				<div class="form-group box1_left">
					 <label for="userName" class="labelTop">用户姓名:</label>
					 <input type="text" id="userName" name="userName"><span style="color:red">*</span>
				</div>
				<div class="form-group">
					 <label for="workAdd" class="labelTop">工作单位:</label>
					 <input type="text" id="workAdd" name="workAdd">
				</div>
				<div class="form-group box1_left">
					 <label for="phone" class="labelTop">联系电话:</label>
					 <input type="text" id="phone" name="phone"><span style="color:red">*</span>
				</div>
				<div class="form-group">
					 <label for="disputeNum" class="labelTop">争议号码:</label>
					 <input type="text" id="disputeNum"><span style="color:red">*</span>
				</div>
				<div class="form-group">
					 <label for="userAdd" class="labelTop">地址:</label>
					 <input type="text" id="userAdd">
				</div>
				<div class="form-group">
					 <label for="code" class="labelTop">邮政编码:</label>
					 <input type="number" id="code">
				</div>
				<div class="form-group">
					 <label for="appealContent" class="labelTop" id="beizhu">申诉信息:</label>
					 <textarea name="appealContent" id="appealContent" class="textarea"></textarea>
				</div>
			</div>
			<h3>受理信息</h3>
			<div class="box1">
				<div class="form-group box1_left">
					<label for="appealMode" class="labelTop">申诉方式:</label>
					<select id="appealMode" name="appealMode">
						<option value="">请选择</option>
						<option value="申诉">申诉</option>
						<option value="咨询" selected="selected">咨询</option>
						<option value="重单">重单</option>
					</select>
				</div>
				<div class="form-group">
					 <label for="processMode" class="labelTop">处理方式:</label>
					 <select id="processMode" name="processMode">
						<option value="">请选择</option>
						<option value="记录信息" selected="selected">记录信息</option>
						<option value="转入后台处理">转入后台处理</option>
					</select>
				</div>
				<div class="form-group box1_left">
					<label for="" class="labelTop">被投诉企业一级:</label>
					<select id="firstCompany" name="firstCompany" class="s_province"></select>
					
				</div>
				<div class="form-group">
					<label for="" class="labelTop">被投诉企业二级:</label>
					<select id="secondCompany" name="secondCompany" class="s_province"></select>
					<script type="text/javascript">_init_company();</script>
				</div>
				<div class="form-group">
					<label for="" class="labelTop">涉及企业:</label>
					<label><input name="sjqy" type="checkbox" value="中国电信" />中国电信 </label>
					<label><input name="sjqy" type="checkbox" value="其他" />其他 </label>
					<label><input name="sjqy" type="checkbox" value="中国移动" />中国移动 </label>
					<label><input name="sjqy" type="checkbox" value="中国联通" />中国联通</label>
					<label><input name="sjqy" type="checkbox" value="中国铁通" />中国铁通 </label>
					<label><input name="sjqy" type="checkbox" value="三网融合试点企业" />三网融合试点企业 </label>
					<label><input name="sjqy" type="checkbox" value="虚拟运营商" />虚拟运营商 </label>
					<label><input name="sjqy" type="checkbox" value="互联网企业" />互联网企业 </label>
				</div>
				<div class="form-group">
					<label for="" class="labelTop">运营企业地区:</label>
					<select id="s_province" name="s_province" class="s_province"></select>
					<select id="s_city" name="s_city" class="s_province"></select>
					<script type="text/javascript">_init_area();</script>
				</div>
				<div class="form-group">
					<label for="" class="labelTop">分类码:</label>
					<label for="" class="labelBottom">第一级:</label>
					<select id="classify_first" name="classify_first" class="s_province"></select>
					<label for="" class="labelBottom">第二级:</label>
					<select id="classify_second" name="classify_second" class="s_province"></select>
					<label for="" class="labelBottom">第三级:</label>
					<select id="classify_third" name="classify_third" class="s_province"></select>
					<script type="text/javascript">_init_classify();</script>
				</div>
				<div class="form-group">
					<label for="" class="labelTop">业务码:</label>
					<label for="" class="labelBottom">第一级:</label>
					<select id="business_first" name="business_first" class="s_province"></select>
					<label for="" class="labelBottom">第二级:</label>
					<select id="business_second" name="business_second" class="s_province"></select>
					<label for="" class="labelBottom">第三级:</label>
					<select id="business_third" name="business_third" class="s_province"></select>
					<label for="" class="labelBottom">第四级:</label>
					<select id="business_fourth" name="business_fourth" class="s_province"></select>
					<script type="text/javascript">_init_business();</script>
				</div>
				<div class="form-group">
					 <label for="remark" class="labelTop">备注:</label>
					 <textarea name="remark" id="remark" class="textarea"></textarea>
				</div>
				<div class="footerBtn">
					<input type="reset" id="reset" value="重置">
					<input type="button" id="save" value="保存">
				</div> 
			</form>
		</div>
	</div>
</body>
</html>

