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
		<div class="panel-heading">方法1</div>
		<div class="panel-body">
			<div class="alert alert-success">
				<s:property value="#session.remove('name')"/>
			</div>			
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">方法2</div>
		<div class="panel-body">
			<div class="alert alert-success">
				<s:property value="n2"/>
			</div>			
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
