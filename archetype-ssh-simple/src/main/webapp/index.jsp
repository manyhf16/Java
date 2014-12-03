<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
</head>
<body>
	<h3>name 为空, 跳转回本页面</h3>
	<a href="${pageContext.request.contextPath}/sample.action">查看示例</a>
	<br />
	<s:a action="sample">查看示例(struts 标签)</s:a>
	<hr />
	<h3>name 不为空, 跳转至成功页面</h3>
	<a href="${pageContext.request.contextPath}/sample.action?name=test">查看示例</a>
	<br />
	<s:a action="sample?name=test">查看示例(struts 标签)</s:a>
	<form action=""></form>
	<s:debug></s:debug>
</body>
</html>
