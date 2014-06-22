<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
</head>
<body>
	<ul>
		<s:iterator value="result">
		<li><s:property value="name" /></li>
		</s:iterator>
	</ul>
	<s:debug />
</body>
</html>
