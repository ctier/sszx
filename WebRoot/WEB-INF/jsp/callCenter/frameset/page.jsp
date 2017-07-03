<%@ page contentType="text/html; charset=utf-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<script type="text/javascript" src="${path}/ajax.do?managerName=callCenterManager"></script>
<html>
<head>
<title>语音留言列表</title>
<script type="text/javascript" src="${path }/apps_res/appealAccept/js/page.js"></script>
<script text="text/javascript">
	var grid;
	$(function() {
		new MxtLayout({
			'id' : 'layout',
			'northArea' : {
				'id' : 'north',
				'height' : 30,
				'sprit' : false,
				'border' : false
			},
			'centerArea' : {
				'id' : 'center',
				'border' : false,
				'minHeight' : 20
			}
		});
		grid = $("#listSent").ajaxgrid({
			//回调函数
			//render:rend,
			 click : clk,//单击事件
			colModel : [{
				display : 'radio',
				width : '2%',
				name : 'radioid',
				sortable : false,
				align : 'center',
				type : 'radio'
			},{
				display : "留言编号",
				name : 'lybh',
				width : '20%',
				sortable : false,
				align : 'center'
			},{
				display : "接收时间",
				name : 'jssj',
				width : '20%',
				sortable : false,
				align : 'center'
			},{
				display : "留言号码",
				name : 'lyhm',
				width : '20%',
				sortable : false,
				align : 'center'
			},{
				display : "收听留言",
				name : 'stly',
				width : '20%',
				sortable : false,
				align : 'center'
			},{
				display : "留言地址",
				name : 'lydz',
				width : '18%',
				sortable : false,
				align : 'center'
			}/* ,{
				display : "操作",
				name : 'CZ',
				width : '16%',
				sortable : false,
				align : 'center'
			} */],
			isToggleHideShow : false,
			usepager : true,//是否启用分页
			//rpMaxSize:5,//每页展示最大数，最大为200
			useRp : true,
			customize : false,
			resizable : false,
			showToggleBtn : false,
			parentId : $('.layout_center').eq(0).attr('id'),
			vChange : false,
			vChangeParam : {
				overflow : "hidden",
				autoResize : true
			},
			slideToggleBtn : true,
			managerName : "callCenterManager",
			managerMethod : "getVoiceData"
		});
		var o = new Object();
		$("#listSent").ajaxgridLoad(o);
		
		/* function rend(txt,rowData, rowIndex, colIndex,colObj){
			/*  单字删除
			rowData.CZ='删除';
			if(colIndex==5){
				txt='删除';
			}
			if(txt==='删除'){
				return '<a href="javascript:void(0);" onclick="deleteVoice()">' + txt + '</a>';
			}else{
				return txt;
			} */
			/*  右侧展示去掉，换成工具条
			var content = '<a href="javascript:void(0)" class="common_button common_button_emphasize" onclick="bfv()">下载</a>'
						+ '<a href="javascript:void(0)" class="common_button common_button_emphasize" onclick="deleteVoice()">删除</a>';
			rowData.CZ = content;
			if(colIndex==5){
				txt = content;
			}
			if(txt==='删除'){
				return txt;
			}else{
				return txt;
			}
		} */
		 function clk(data, r, c) {
      		//debugger;
      		var sj = data.jssj;
      		var phone = data.lyhm;
      		var fileName = data.stly;
      		var fileUrl = data.lydz;
      		//alert("接收时间==="+sj+",留言号码"+phone);
      		var mainFrame = window.parent.mainFrame;
      		mainFrame.$("#ldNum").val(phone);
      		mainFrame.$("#ldsj").val(sj);
      		$("#fileName").val(fileName);
      		$("#fileUrl").val(fileUrl);
   	     }
	});
</script>
</head>
<body>
	<div id='layout'>
		<div class="layout_north bg_color" id="north">
			<div id="toolbars" class="f0f0f0">
	            <table class="w100b">
                    <tr>
                    	<td colspan="2">
                    		<span class="left">
                    			<a class="img-button margin_r_5" href="javascript:void(0)" onclick="dld()">
                    				<em class="ico16 download_16"></em>下载</a>
                    		</span>
                    		<span class="left">
                    			<a class="img-button margin_r_5" href="javascript:void(0)" onclick="dev()">
                    				<em class="ico16 del_16"></em>删除</a>
                    		</span>
                    	</td>
                    </tr>
            	</table>
			</div>
		</div>
		<div class="layout_center over_hidden" id="center">
			<table class="flexme3" id="listSent"></table>
			<div id="grid_detail" class="h100b">
				<iframe id="summary" name='summaryF' width="100%" height="100%"
					frameborder="0" class='calendar_show_iframe'
					style="overflow-y: hidden"></iframe>
			</div>
		</div>
	</div>
	<iframe name="hiddenIframe" id="hiddenIframe" style="display:none"></iframe>
	<form id="downloadForm" method="get">
		<input type="hidden" id="fileName" name="fileName">
		<input type="hidden" id="fileUrl" name="fileUrl">
	</form>
</body>
</html>