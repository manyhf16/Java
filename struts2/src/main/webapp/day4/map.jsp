<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/nav.jsp"></jsp:include>
	
	<s:iterator value="map" status="i">		
		进入第<s:property value="#i.index"/>次循环<s:debug id="%{'debug_begin_' + #i.index}"/>
		<s:property value="key"/>, <s:property value="value"/>
		<hr/>
	</s:iterator>
	
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
