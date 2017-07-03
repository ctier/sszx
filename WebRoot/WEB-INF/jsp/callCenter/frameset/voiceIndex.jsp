<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${path }/callCenter/style/style.css"/>
<title>index</title>
</head>
	<frameset rows="40%,60%" cols="*" frameborder="no" border="0" framespacing="0">
	  <frame src="${path }/appeal/callCenter.do?method=page" name="pageFrame" scrolling="no" noresize="noresize" id="topFrame" title="pageFrame" style="border-bottom:#99bbe8 solid 1px;"/>
	  <frameset rows="100%" cols="*" frameborder="0" border="0" framespacing="0">
	    <frame src="${path }/appeal/callCenter.do?method=mainVoice" name="mainFrame" id="mainFrame" title="mainFrame" />
	  </frameset>
	</frameset>
</html>