<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".ajax").click(function(e){
		e.preventDefault();
		$.get(this.href, function(json){
			console.log(json);
		});
	});
});
</script>
</head>
<body>
	<h3>name 为空, 跳转回本页面</h3>
	<a href="${pageContext.request.contextPath}/sample.action">查看示例</a>
	<br />
	<s:a action="sample">查看示例(struts 标签)</s:a>
	<br/>
	<a href="${pageContext.request.contextPath}/sample.action" class="ajax">查看示例(ajax)</a>
	<hr />
	<h3>name 不为空, 跳转至成功页面</h3>
	<a href="${pageContext.request.contextPath}/sample.action?name=test">查看示例</a>
	<br />
	<s:a action="sample?name=test">查看示例(struts 标签)</s:a>
	<br/>
	<a href="${pageContext.request.contextPath}/sample.action?name=test" class="ajax">查看示例(ajax)</a>
	<s:debug></s:debug>
</body>
</html>
