<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<script type="text/javascript" src="${path}/ajax.do?managerName=appealAcceptManager"></script>
<html>
 <head> 
  <title>发送短信</title> 
  <script> 
      // 通过window.dialogArguments可以调用父窗口的方法，变量等，例如window.dialogArguments.$('#field0013')
      var args = window.dialogArguments;
      
      $(document).ready(function(){
    	  var sysContent = args.$('#field0092').val();
          //手动填写的
          var sdContent= args.$('#field0085').val();
          var content = sysContent + sdContent;
          if(content == "" || content == null){
	          $("#content").html("短信内容为空，请您确认！");
          }else{
        	  $("#content").html(content);
          }
      });
      
      function OK(){
    	    var flagOpion = args.$('#field0088').children('option:selected').attr('val4cal');
		    var flagValue = args.$('#field0088').children('option:selected').attr('value');
		    if(flagOpion == 0 && flagValue != ''){
	    	  // 使用dataValue回填
	          var obj = new Object();
	          //短息内容
	          //系统的
	          var sysContent = args.$('#field0092').val();
	          //手动填写的
	          var sdContent= args.$('#field0085').val();
	          obj.content = sysContent + sdContent;
	          if(obj.content == "" || obj.content == null ){
	              args.$.infor("短信内容不能为空！");
	              return;
	          }
	          //联系电话
	          obj.phone = args.$('#field0008').val();
	          if(obj.phone == "" || obj.phone == null){
	        	  args.$.infor("联系电话不能为空!");
	              return;
	          }
	          
	          var aam = new appealAcceptManager();
	          var result = aam.transSendSMS(obj);
	          //错误
	          if(result == 'success'){
	        	  args.$.infor("短信发送成功");
	          }else{
	        	  args.$.error("短信发送失败，错误代码:"+result);
	          }
	          return  {dataValue:""};
    	  }else{
    		  args.$.infor("当前为不发短信状态，请您确认！");
    	  }
      }
  </script> 
 </head>
 <body>
  <div style="height: 40px;">
    &nbsp; 
  </div> 
  <table class="margin_t_5 margin_l_5 font_size12" align="center" width="300" height="70%" style="table-layout:fixed;"> 
   <tbody> 
    	<span id="content" class="margin_l_10 margin_r_10"></span>
   </tbody> 
   
  </table>  
 </body>
</html>