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
    <div class="panel-heading">约定（惯例）优于配置--传统</div>
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
        <li><a href="#t1_9" data-toggle="tab">find.jsp</a></li>
      </ul>
      <!-- Tab panes -->
      <div class="tab-content">
        <div class="tab-pane active" id="t1_1">
          <br /> <a href="${root}/day10/login.action" class="btn btn-default test" title="约定（惯例）优于配置--传统">测试</a>
        </div>
        <div class="tab-pane" id="t1_2">
          <s:action namespace="/common" name="displayXmlSrc" executeResult="true">
            <s:param name="packageName">day10</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_3">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day10/BaseAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_4">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day10/LoginCheckAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_5">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day10/LogoutAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_6">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day10/FindAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_7">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/actions/day10/UpdateAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_8">
          <s:action namespace="/common" name="displayJspSrc" executeResult="true">
            <s:param name="src">WEB-INF/content/day10/login.jsp</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_9">
          <s:action namespace="/common" name="displayJspSrc" executeResult="true">
            <s:param name="src">WEB-INF/content/day10/find.jsp</s:param>
          </s:action>
        </div>

        <br />
        <div class="alert alert-info">
          <p>
            <span class="glyphicon glyphicon-info-sign"></span> &nbsp;提示
          </p>
          <ul>
            <li>需要加入struts2-convention-plugin.jar 插件</li>
            <li>本例均采用传统请求作为示例</li>
            <li>传统请求中视图部分的映射（dispatcher），示例：
                  <table class="table">
                    <thead>
                      <tr>
                        <th>java包名</th>
                        <th>Action类名</th>
                        <th>action动作方法返回值</th>
                        <th>约定的jsp名称</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>zpark.actions.day10</td>
                        <td>LoginAction</td>
                        <td><kbd>success</kbd></td>
                        <td>WEB-INF/content/day10/login.jsp</td>
                      </tr>
                      <tr>
                        <td>zpark.actions.day10</td>
                        <td>LoginAction</td>
                        <td><kbd>input</kbd></td>
                        <td>WEB-INF/content/day10/login-input.jsp</td>
                      </tr>
                      <tr>
                        <td>zpark.actions.day10</td>
                        <td>FindAction</td>
                        <td><kbd>success</kbd></td>
                        <td>WEB-INF/content/day10/find.jsp</td>
                      </tr>
                    </tbody>
                  </table>
                </li>
                <li>使用struts2-convention-plugin.jar 后，为了方便调试Action路径映射，可以加入struts2-config-browser-plugin后访问下列地址
                    <kbd><a style="color:white" href="${root}/config-browser/" title="_blank">点击查看</a></kbd>
                </li>
          </ul>
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
