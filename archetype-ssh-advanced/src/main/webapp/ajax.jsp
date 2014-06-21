<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
	<a href="${pageContext.request.contextPath}/sample.action?name=test">查看示例</a>
	<a href="${pageContext.request.contextPath}/sample.action?name=aaa" class="ajax">查看示例(ajax)</a>
</body>
</html>