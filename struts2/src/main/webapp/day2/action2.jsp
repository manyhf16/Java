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
	<div class="alert alert-success">
		#session.name <kbd><s:property value="#session.name" /></kbd>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
