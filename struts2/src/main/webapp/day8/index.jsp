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
    <div class="panel-heading">拦截器应用</div>
    <div class="panel-body">
      <!-- Nav tabs -->
      <ul class="nav nav-tabs">
        <li class="active"><a href="#t1_1" data-toggle="tab">测试</a></li>
        <li><a href="#t1_2" data-toggle="tab">struts.xml</a></li>
        <li><a href="#t1_3" data-toggle="tab">BaseAction.java</a></li>
        <li><a href="#t1_4" data-toggle="tab">LoginCheckAction.java</a></li>
        <li><a href="#t1_5" data-toggle="tab">FindAction.java</a></li>
        <li><a href="#t1_6" data-toggle="tab">UpdateAction.java</a></li>
        <li><a href="#t1_7" data-toggle="tab">JsonResultInterceptor.java</a></li>
        <li><a href="#t1_8" data-toggle="tab">FlashScopeInterceptor.java</a></li>
        <li><a href="#t1_9" data-toggle="tab">LoginCheckInterceptor.java</a></li>
        <li><a href="#t1_10" data-toggle="tab">login.jsp</a></li>
        <li><a href="#t1_11" data-toggle="tab">find.jsp</a></li>
      </ul>
      <!-- Tab panes -->
      <div class="tab-content">
        <div class="tab-pane active" id="t1_1">
          <br /> <a href="${root}/day8/login.action" class="btn btn-default test" title="拦截器应用">测试</a>
        </div>
        <div class="tab-pane" id="t1_2">
          <s:action namespace="/common" name="displayXmlSrc" executeResult="true">
            <s:param name="packageName">day8</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_3">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/struts2/action/day8/BaseAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_4">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/struts2/action/day8/LoginCheckAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_5">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/struts2/action/day8/FindAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_6">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/struts2/action/day8/UpdateAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_7">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/ext/struts/JsonResultInterceptor.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_8">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/ext/struts/FlashScopeInterceptor.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_9">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/struts2/action/day8/LoginCheckInterceptor.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_10">
          <s:action namespace="/common" name="displayJspSrc" executeResult="true">
            <s:param name="src">day8/login.jsp</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t1_11">
          <s:action namespace="/common" name="displayJspSrc" executeResult="true">
            <s:param name="src">day8/find.jsp</s:param>
          </s:action>
        </div>

        <br />
        <div class="alert alert-info">
          <p>
            <span class="glyphicon glyphicon-info-sign"></span> &nbsp;提示 这里用拦截器解决了以下几个实际问题
          </p>
          <ol>
            <li>登录验证拦截器，用来控制哪些action必须登录之后才能进入</li>
            <li>paramsPrepareParamsStack的应用：
              <ul>
                <li>场景：有些属性不希望更新（从数据库获得值），有些属性希望更新（从表单获得值）</li>
                <li>在更新用户的需求中，只希望更新<kbd>类型</kbd>和<kbd>真实姓名</kbd>,不希望<kbd>密码</kbd>被更新，但后台的update方法是一个考虑更新所有字段的通用方法
                </li>
                <li>问题来了， <kbd>类型</kbd>和<kbd>真实姓名</kbd>的值需要从前台传入，但<kbd>密码</kbd>需要在action再次被查询
                </li>
                <li>如何比较优雅地解决这个问题呢？结合使用paramsPrepareParamsStack 拦截器栈和 Preparable接口，它的执行顺序是：
                  <ul>
                    <li>首先经过params拦截器，接收到user.username请求参数</li>
                    <li>其次调用Preparable接口的prepare方法，根据user.username查询数据库中的User对象</li>
                    <li>再次经过params拦截器，这时使用请求参数中的user.type, user.realname覆盖在上一步查询的User对象的相应属性</li>
                    <li>到达execute方法时的User对象已经是期望中的</li>
                  </ul>
                </li>
              </ul>
            </li>
            <li>如何在不影响Action代码的情况下，让ajax请求和普通表单请求各自返回相应的结果？例如：
              <ul>
                <li>向Action发送ajax请求，它返回json数据</li>
                <li>向Action发送普通表单请求，它返回jsp视图</li>
                <li>解决方案是使用了JsonResultInterceptor拦截器</li>
              </ul>
            </li>
            <li>如何构建<kbd>flash</kbd>作用域，即在重定向时使用session传参，但进行封装以简化用户操作：
              <ul>
                <li>解决方案是使用了FlashScopeInterceptor拦截器</li>
                <li>只需要用户在Action1中用<kbd>@Flash</kbd>注解标记哪些属性需要在重定向时传递给Action2
                </li>
                <li>FlashScopeInterceptor拦截器会在Action1结束时将这些属性值存入#session</li>
                <li>FlashScopeInterceptor拦截器会在进入Action2时将#session中这些值转存入#flash，以便用户使用，而#session中的内容会被清除</li>
                <li>#flash中的变量生命周期仅限一次请求（其实它就是值栈context中一个key，值的类型是Map）</li>
                <li>缺点是：如果重定向至jsp,
                  则不会完成清除#session操作，这是由于struts的拦截器只能拦截action，因此struts也提倡不直接使用jsp，所有请求都先经过action间接进入jsp</li>
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
