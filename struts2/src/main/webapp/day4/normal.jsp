<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/nav.jsp"></jsp:include>
	<s:debug /><hr/>
	模型:<s:property/> <hr/>
	姓名:<kbd><s:property value="person.name"/></kbd> <hr/>
	年龄:<kbd><s:property value="person.age"/></kbd> <hr/>
	城市:<kbd><s:property value="person.city.name"/></kbd> <hr/>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
