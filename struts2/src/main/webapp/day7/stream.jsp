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
		<div class="panel-heading">直接显示(用stream视图类型)</div>
		<div class="panel-body">
			<img alt="" src="${root}/day7/captcha1.action">
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">直接显示(直接用response对象)</div>
		<div class="panel-body">
			<img alt="" src="${root}/day7/captcha2.action">
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">点击下载</div>
		<div class="panel-body">
			<a href="${root}/day7/captcha1.action?download=true" class="btn btn-default">点击下载</a>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
