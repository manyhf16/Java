<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
</head>
<body>
	<h3>name 为空, 跳转回本页面</h3>
	<a href="${app}/sample">查看示例</a>
	<m:debug />
</body>
</html>
