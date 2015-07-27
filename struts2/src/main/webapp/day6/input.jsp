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
	<form role="form" action="${root}/day6/validation.action">
		<div class="form-group">			
			<label for="param">age</label> 
				<input name="age" type="text" class="form-control" placeholder="请输入年龄" value="${age}">
			<label for="param">birthday</label> 
				<input name="birthday" type="text" class="form-control" placeholder="请输入生日" value="<s:date name='birthday' format="yyyy-MM-dd"/>">
			<label for="param">email</label> 
				<input name="email" type="text" class="form-control" placeholder="请输入邮件地址" value="${email}">
			<label for="param">pass</label> 
				<input name="pass" type="password" class="form-control" placeholder="请输入密码">
			<label for="param">passConfirm</label> 
				<input name="passConfirm" type="password" class="form-control" placeholder="请确认密码">
			<label for="param">phone</label> 
				<input name="phone" type="text" class="form-control" placeholder="请输入电话号码, 格式为(086) 1234-56789" value="${phone}">
			<label for="param">person.name</label> 
				<input name="person.name" type="text" class="form-control" placeholder="请输入姓名" value="${person.name}">
		</div>
		<button type="submit" class="btn btn-default">提交</button>
	</form>
	<br/>
	<div class="alert alert-warning">
		<s:fielderror/>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
