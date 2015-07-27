<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/nav.jsp"></jsp:include>
	<table class="table">
		<thead>
			<tr>
				<td>作用域</td>
				<td>值</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Action 中的 name 属性</td>
				<td><kbd><s:property value="name" /></kbd></td>
			</tr>
			<tr>
				<td>Session 中的 name</td>
				<td><kbd><s:property value="#session.name" /></kbd></td>
			</tr>
			<tr>
				<td>Application 中的 name</td>
				<td><kbd><s:property value="#application.name" /></kbd></td>
			</tr>
		</tbody>
	</table>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
