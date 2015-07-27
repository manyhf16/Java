<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<jsp:include page="/common/header.jsp"></jsp:include>
</head>
<body>
  <jsp:include page="/common/nav.jsp"></jsp:include>
  <div class="panel panel-default">
    <div class="panel-heading">登录/退出/查询</div>
    <div class="panel-body">
      <form action="${root}/day8/login-check.action" method="post" id="f">
        <div class="form-group">
          <label for="username">用户名</label><input type="text" name="username" class="form-control" id="username">
          <label for="password">密码</label><input type="password" name="password" class="form-control" id="password">
        </div>
        <button type="submit" class="btn btn-default">登录</button>
        <button type="button" class="btn btn-default" id="loginBtn">登录(ajax)</button>
        <button type="button" class="btn btn-default" id="logoutBtn1">退出</button>
        <button type="button" class="btn btn-default" id="logoutBtn2">退出(ajax)</button>
        <a href="${root}/day8/find.action?user.username=zhangsan" class="btn btn-default">修改zhangsan用户</a>
      </form>
      <br />
      <div id="warning" class="alert alert-warning <s:if test='#flash.error == null'>hidden</s:if>"><s:property value="#flash.error"/></div>
      <div id="success" class="alert alert-success <s:if test='#flash.message == null'>hidden</s:if>"><s:property value="#flash.message"/></div>
      <s:debug></s:debug>
    </div>
  </div>
  <jsp:include page="/common/footer.jsp"></jsp:include>
  <script type="text/javascript">
            $("#loginBtn").click(function() {
                var f = $("#f").get(0);
                var formData = new FormData(f);
                $.ajax({
                    url : f.action,
                    method : "post",
                    data : formData,
                    contentType : false,
                    processData : false
                }).done(function(json) {
                    if (json.error) {
                        $("#warning").removeClass("hidden").html(json.error);
                        $("#success").addClass("hidden");
                    } else {
                        $("#warning").addClass("hidden");
                        $("#success").removeClass("hidden").html(json.message);
                    }
                });
            });

            $("#logoutBtn1").click(function() {
                window.location.href = "${root}/day8/logout.action";
            });

            $("#logoutBtn2").click(function() {
                $.get("${root}/day8/logout.action").done(function(json) {
                    if (json.error) {
                        $("#warning").removeClass("hidden").html(json.error);
                        $("#success").addClass("hidden");
                    } else {
                        $("#warning").addClass("hidden");
                        $("#success").removeClass("hidden").html(json.message);
                    }
                });
            });
        </script>
</body>
</html>
