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
	<div class="panel panel-default">
		<div class="panel-heading">结果</div>
		<div class="panel-body">
			<div class="alert alert-success">
				<s:property value="name"/>
			</div>			
			<s:debug/>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
