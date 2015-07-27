<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/nav.jsp"></jsp:include>
	<div class="jumbotron">
		<h1>Hello, world!</h1>
		<p> 转换的结果为：<kbd>${ requestScope.pinyin }</kbd></p>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
