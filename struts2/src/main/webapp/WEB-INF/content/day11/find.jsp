<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="m"%>
<%@ taglib uri="http://zpark.tag" prefix="z"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
  <jsp:include page="/common/nav.jsp"></jsp:include>
  <div class="panel panel-default">
    <div class="panel-heading">查询结果</div>
    <div class="panel-body">
      <s:debug id="a1"></s:debug>
      <z:grid id="g1">
        <z:column index="id" text="编号"/>
        <z:column index="name" text="姓名"/>
        <z:column index="qq" text="QQ"/>
        <z:column index="phone" text="手机"/>
        <z:column index="email" text="邮件"/>
        <z:column index="cityId" text="城市"/>
     </z:grid>
      <s:debug id="a2"></s:debug>
    </div>
  </div>
  <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
