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
	<table class="table">
		<thead>
			<tr>
				<td>参数名</td>
				<td>参数值</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>name</td>
				<td><kbd><s:property value="name" /></kbd></td>
			</tr>
			<tr>
				<td>age</td>
				<td><kbd><s:property value="age" /></kbd></td>
			</tr>
			<tr>
				<td>gender</td>
				<td><kbd><s:property value="gender" /></kbd></td>
			</tr>
			<tr>
				<td>birthday</td>
				<td><kbd><s:date name="birthday" format="yyyy-MM-dd"/></kbd></td>
			</tr>
			<tr>
				<td>hobby</td>
				<td><kbd><s:property value="hobby" /></kbd></td>
			</tr>
			<tr>
				<td>user</td>
				<td><kbd><s:property value="user" /></kbd></td>
			</tr>
		</tbody>
	</table>
	
	<!-- 检查是否有验证错误 -->
	<s:if test="fieldErrors.size() > 0">
		<div class="alert alert-danger"><s:fielderror /></div>
	</s:if>
	
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
