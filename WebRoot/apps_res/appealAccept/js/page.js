/**
 * 删除语音留言
 */
function dev(){
	var radio = $("input[type='radio']:checked");
	if(radio.length == 0){
		$.alert("请选择您要删除的数据!");
	}else{
		 $.confirm({
             'msg': "您确定要删除此条语音留言吗？",
             ok_fn: function() {
            	 var id = radio.parent().parent().parent().children("td").eq(1).text();
            	 alert(id);
            	 var obj = new Object();
            	 obj.id = id;
            	 var cm = new callCenterManager();
            	var rs = cm.updateVoiceState(obj);
            	if(rs == 1){
            		$.infor("删除成功！");
            		window.location.reload();
            	}else{
            		$.error("删除失败，请联系系统管理员！");
            	}
             }
         });
	}
}
/**
 * 下载语音留言
 */
function dld(){
	var radio = $("input[type='radio']:checked");
	if(radio.length == 0){
		$.alert("请先选择需要下载的语音留言数据!");
	}else{
		debugger;
		var tr = radio.parent().parent().parent().children("td");
		var fileName = tr.eq(4).text();
		var url = tr.eq(5).text();
//		alert(url);
		window.location.href="/seeyon/appeal/callCenter.do?method=download&url="+url+"&fileName="+fileName;
	}
}
