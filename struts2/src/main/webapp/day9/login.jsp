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
    <div class="panel-heading">登录/退出/查询/更新</div>
    <div class="panel-body">
      <form action="${root}/day9/login-check.action" method="post" id="f1">
        <div class="form-group">
          <label for="username">用户名</label><input type="text" name="username" class="form-control" id="username">
          <label for="password">密码</label><input type="password" name="password" class="form-control" id="password">
        </div>
        <button type="button" class="btn btn-default" id="loginBtn">登录(ajax)</button>
        <button type="button" class="btn btn-default" id="logoutBtn">退出(ajax)</button>
        <a href="${root}/day9/find.action?user.username=zhangsan" class="btn btn-default" id="modifyBtn">修改zhangsan用户</a>
      </form>
      <br />
      <div class="form-group hidden" id="modifyDiv">
        <form action="${root}/day9/update.action" method="post" id="f2">
          <label for="username">用户名</label> <input name="user.username" type="text" class="form-control" readonly
            id="m_username"> <label for="password">密码</label> <input type="password" class="form-control"
            value="******" readonly> <label for="type">类型</label> <select name="user.type" class="form-control"
            id="m_type">
            <option value="0">普通用户</option>
            <option value="1">管理员</option>
          </select> <label for="realname">真实姓名</label> <input name="user.realname" type="text" class="form-control"
            placeholder="请输入真实姓名" id="m_realname"> <br />
          <button type="button" class="btn btn-default" id="modify">提交</button>
        </form>
      </div>

      <div id="warning" class="alert alert-warning hidden"></div>
      <div id="success" class="alert alert-success hidden"></div>
    </div>
  </div>
  <jsp:include page="/common/footer.jsp"></jsp:include>
  <script type="text/javascript">
            $("#modifyBtn").click(function(e) {
                e.preventDefault();
                $.get(this.href, function(json) {
                    showResult(json);
                    if (!json.error) {
                        $("#modifyDiv").removeClass("hidden");
                        $("#m_username").val(json.user.username);
                        $("#m_type").val(json.user.type);
                        $("#m_realname").val(json.user.realname);
                    }
                });
            });

            $("#modify").click(function() {
                var f2 = $("#f2").get(0);
                var formData = new FormData(f2);
                $.ajax({
                    url : f2.action,
                    method : "post",
                    data : formData,
                    contentType : false,
                    processData : false
                }).done(function(json) {
                    showResult(json);
                });
            });

            $("#loginBtn").click(function() {
                var f1 = $("#f1").get(0);
                var formData = new FormData(f1);
                $.ajax({
                    url : f1.action,
                    method : "post",
                    data : formData,
                    contentType : false,
                    processData : false
                }).done(function(json) {
                    showResult(json);
                });
            });

            $("#logoutBtn").click(function() {
                $.get("${root}/day9/logout.action").done(function(json) {
                    showResult(json);
                });
            });

            function showResult(json) {
                if (json.error) {
                    $("#warning").removeClass("hidden").html(json.error);
                    $("#success").addClass("hidden");
                } else if (json.message) {
                    $("#warning").addClass("hidden");
                    $("#success").removeClass("hidden").html(json.message);
                }
            }
        </script>
</body>
</html>
