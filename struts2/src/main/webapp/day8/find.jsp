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
  <div class="panel panel-default">
    <div class="panel-heading">更新</div>
    <div class="panel-body">
      <form action="${root}/day8/update.action" method="post">
        <div class="form-group">
          <label for="username">用户名</label> <input name="user.username" type="text" class="form-control"
            value="${user.username}" readonly id="username"> <label for="password">密码</label> <input
            type="password" class="form-control" value="******" readonly id="password"> <label for="type">类型</label>
          <select name="user.type" class="form-control" id="type">
            <option value="0" ${user.type==0?"selected":""}>普通用户</option>
            <option value="1" ${user.type==1?"selected":""}>管理员</option>
          </select> <label for="realname">真实姓名</label> <input name="user.realname" type="text" class="form-control"
            placeholder="请输入真实姓名" value="${user.realname}" id="realname">
        </div>
        <button type="submit" class="btn btn-default">提交</button>
      </form>
      <br />
      <div class="alert alert-success <s:if test='#flash.message == null'>hidden</s:if>">
        <s:property value="#flash.message" />
      </div>
    </div>
  </div>


  <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
