<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="m"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/nav.jsp"></jsp:include>
	
	<kbd>#name</kbd>  <s:property value="#name"/>
	<hr>
	<kbd>#request.name</kbd>  <s:property value="#request.name"/>
	<hr>
	<kbd>#session.name</kbd>  <s:property value="#session.name"/>
	<hr>
	<kbd>#application.name</kbd>  <s:property value="#application.name"/>
	<hr>
		
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
