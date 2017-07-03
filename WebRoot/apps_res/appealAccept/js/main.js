$(function(){
	function getDate(){
		var today = new Date().Format("yyyy-MM-dd hh:mm:ss"); 
		return today;
	}
	//时间格式化
	Date.prototype.Format = function (fmt) { 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	};
	//设置时间
	$("#ldsj").val(getDate());
	//保存数据
	$("#save").click(function(){
		var top = window.parent.topFrame;
		var flag = top.$("#qr").prop("disabled");
		if(!flag){
			top.$.alert("当前用户未签入，请先签入！");
			return;
		}
		var userName = $("#userName").val();
		if(userName == "" || userName == null){
			top.$.alert("用户姓名不允许为空！");
			return;
		}
		var phone = $("#phone").val();
		if(phone == "" || phone == null){
			top.$.alert("联系电话不允许为空！");
			return;
		}
		var disputeNum = $("#disputeNum").val();
		if(disputeNum == "" || disputeNum == null){
			top.$.alert("争议号码不允许为空！");
			return;
		}
		var appealContent = $("#appealContent").val();
		if(appealContent == "" || appealContent == null){
			top.$.alert("申诉信息不允许为空！");
			return;
		}
		//受理员
		var slyId = $("#slyId").val();
		//来电时间
		var ldsj = $("#ldsj").val();
		//申诉来源
		var appealSource = $("#appealSource").val();
		//工作单位
		var workAdd = $("#workAdd").val();
		//地址
		var userAdd = $("#userAdd").val();
		//邮政编码
		var code = $("#code").val();
		//申诉方式
		var appealMode = $("#appealMode").val();
		//处理方式
		var processMode = $("#processMode").val();
		//被申诉企业一级
		var firstCompany = $("#firstCompany").val();
		if(firstCompany == "请选择"){
			top.$.alert("请选择被投诉企业一级!");
			return;
		}
		//被申诉企业二级
		var secondCompany = $("#secondCompany").val();
		if(firstCompany == "虚拟运营商" && secondCompany == "请选择"){
			top.$.alert("请选择被投诉企业二级!");
			return;
		}
		//涉及企业
		var sjqy = "";
		$('input[name="sjqy"]:checked').each(function(){
			sjqy += $(this).val()+','; 
		});
		if (sjqy.length > 0) { 
			sjqy = sjqy.substring(0,sjqy.length - 1); 
		} 
		//运营企业地区一级
		var s_province = $("#s_province").val();
		if(s_province == "省份"){
			top.$.alert("运营企业地区一级不可为空");
		}
		//运营企业地区二级
		var s_city = $("#s_city").val();
		if(s_city == "市(区)"){
			top.$.alert("运营企业地区二级不可为空");
		}
		//分类码一级
		var classifyFirst = $("#classify_first").val();
		//分类码二级
		var classifySecond = $("#classify_second").val();
		//分类码三级
		var classifyThird = $("#classify_third").val();
		//业务码一级
		var businessFirst = $("#business_first").val();
		//业务码二级
		var businessSecond = $("#business_second").val();
		//业务码三级
		var businessThird = $("#business_third").val();
		//业务码四级
		var businessFourth = $("#business_fourth").val();
		//备注
		var remark = $("#remark").val();
		var params = {
				'sly':slyId,
				'ldsj':ldsj,
				'appealSource':appealSource,
				'userName':userName,
				'workAdd':workAdd,
				'phone':phone,
				'disputeNum':disputeNum,
				'userAdd':userAdd,
				'code' : code,
				'appealContent' : appealContent,
				'appealMode' : appealMode,
				'processMode' : processMode,
				'firstCompany' : firstCompany,
				'secondCompany' : secondCompany,
				's_province' : s_province,
				's_city' : s_city,
				'sjqy' : sjqy,
				'classifyFirst' : classifyFirst,
				'classifySecond' : classifySecond,
				'classifyThird' : classifyThird,
				'businessFirst' : businessFirst,
				'businessSecond' : businessSecond,
				'businessThird' : businessThird,
				'businessFourth' : businessFourth,
				'remark' : remark
		};
		$.ajax({
			type:"post",
			dataType:"json",
			url:"/seeyon/appeal/callCenter.do?method=save",
			async:false,
			data: params,
			success:function(result){
				if(result){
					window.location.reload();
				}else{
					top.$.alert("保存数据失败！");
				}
			},
			error:function(){
				top.$.alert("服务器异常！");
			} 
		});
	});
	//重置按钮
//	$("#").click(function(){
//		
//	});
});