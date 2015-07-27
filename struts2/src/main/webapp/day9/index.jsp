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
    <div class="panel-heading">约定（惯例）优于配置--ajax</div>
    <div class="panel-body">
      <!-- Nav tabs -->
      <ul class="nav nav-tabs">
        <li class="active"><a href="#t1_1" data-toggle="tab">测试</a></li>
        <li><a href="#t1_2" data-toggle="tab">struts.xml</a></li>
        <li><a href="#t1_3" data-toggle="tab">BaseAction.java</a></li>
        <li><a href="#t1_4" data-toggle="tab">LoginCheckAction.java</a></li>
        <li><a href="#t1_5" data-toggle="tab">LogoutAction.java</a></li>
        <li><a href="#t1_6" data-toggle="tab">FindAction.java</a></li>
        <li><a href="#t1_7" data-toggle="tab">UpdateAction.java</a></li>
        <li><a href="#t1_8" data-toggle="tab">login.jsp</a></li>
      </ul>
      <!-- Tab panes -->
      <div class="tab-content">
        <div class="tab-pane active" id="t1_1">
          <br /> <a href="${root}/day9/login.jsp" class="btn btn-default test" title="约定（惯例）优于配置--ajax">测试</a>
        </div>
        <div class="tab-pane" id="t1_2">
          <s:action namespace="/common" name="displayXmlSrc" executeResult="true">
            <s:param name="packageName">day9</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_3">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day9/BaseAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_4">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day9/LoginCheckAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_5">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day9/LogoutAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_6">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day9/FindAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_7">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day9/UpdateAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_8">
          <s:action namespace="/common" name="displayJspSrc" executeResult="true">
            <s:param name="src">day9/login.jsp</s:param>
          </s:action>
        </div>

        <br />
        <div class="alert alert-info">
          <p>
            <span class="glyphicon glyphicon-info-sign"></span> &nbsp;提示
          </p>
          <ol>
            <li>需要加入struts2-convention-plugin.jar 插件</li>
            <li>本例均采用ajax请求作为示例</li>
            <li>要注意:
              <ul>
                <li>action的所在包名必须是actions或struts</li>
                <li>java中的包名，映射为struts中的namespace，示例：
                  <table class="table">
                    <thead>
                      <tr>
                        <th>java包名</th>
                        <th>struts namespace</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>zpark.actions.day9</td>
                        <td>/day9</td>
                      </tr>
                      <tr>
                        <td>zpark.actions.day10</td>
                        <td>/day10</td>
                      </tr>
                    </tbody>
                  </table>
                </li>
                <li>action类名必须以<kbd>Action</kbd>结尾
                </li>
                <li>除去<kbd>Action</kbd>后缀的部分即为action的访问路径，示例：
                  <table class="table">
                    <thead>
                      <tr>
                        <th>Action类名</th>
                        <th>访问路径</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>FindAction</td>
                        <td>find.action</td>
                      </tr>
                      <tr>
                        <td>UpdateAction</td>
                        <td>update.action</td>
                      </tr>
                      <tr>
                        <td>LoginCheckAction</td>
                        <td>login-check.action</td>
                      </tr>
                    </tbody>
                  </table>
                </li>
                <li>使用了@ParentPackage注解引用xml中定义的拦截器，这样避免在注解中添加过多的拦截器定义</li>
                <li>由于Action请求实际不需要视图跳转（见JsonResultInterceptor），因此在Action内的动作方法可以不需要返回值</li>
                <li>更多<kbd>约定</kbd>请参考struts文档中<kbd>convention-plugin.html</kbd>
                </li>
              </ul>
            </li>
          </ol>
        </div>
      </div>
    </div>
  </div>

  <jsp:include page="/common/footer.jsp"></jsp:include>
  <script type="text/javascript">
            // 让所有超链接点击后在新窗口打开测试页面
            $("a.test").click(function(e) {
                e.preventDefault();
                window.open(this.href, this.title);
            });
        </script>

</body>
</html>
