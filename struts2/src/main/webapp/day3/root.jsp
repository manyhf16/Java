<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/nav.jsp"></jsp:include>
	<s:debug id="d1"/>
	<hr>
	<%
		ValueStack vs = ActionContext.getContext().getValueStack();
	%>
	vs.pop() <kbd>String</kbd>  <%=vs.pop() %>
	<hr>
	vs.pop() <kbd>u2</kbd>  <%=vs.pop() %>
	<hr>
	vs.pop() <kbd>u1</kbd>  <%=vs.pop() %>
	<hr>	
	vs.peek() <kbd>Action</kbd>  <%=vs.peek() %>
	<hr>
	<s:debug id="d2"/>
	<hr>
	<kbd>Action</kbd>  <s:property/>
	<hr>
	<kbd>Action.name</kbd>  <s:property value="name"/>
	<hr>
	<kbd>Action.city.name</kbd>  <s:property value="city.name"/>
	<hr>	
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
