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
		<div class="panel-heading">Hello World</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t1_1" data-toggle="tab">测试</a></li>
				<li><a href="#t1_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t1_3" data-toggle="tab">WelcomeAction.java</a></li>
				<li><a href="#t1_4" data-toggle="tab">success.jsp</a></li>
				<li><a href="#t1_5" data-toggle="tab">error.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t1_1">
					<br/>
					<a href="${root}/day1/hello.action?name=张三" class="btn btn-default test" title="三个作用域">测试</a>
				</div>
				<div class="tab-pane" id="t1_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day1</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day1/WelcomeAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_4">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day1/success.jsp</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_5">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day1/error.jsp</s:param>
					</s:action>
				</div>
			</div>			
			<br />
			<div class="alert alert-info">
				<span class="glyphicon glyphicon-info-sign"></span> &nbsp;第一个Action
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">三个作用域</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t2_1" data-toggle="tab">测试</a></li>
				<li><a href="#t2_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t2_3" data-toggle="tab">ScopeAction.java</a></li>
				<li><a href="#t2_4" data-toggle="tab">scope.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t2_1">
					<br/>
					<a href="${root}/day1/scope.action" class="btn btn-default test" title="三个作用域">测试</a>
				</div>
				<div class="tab-pane" id="t2_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day1</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day1/ScopeAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_4">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day1/scope.jsp</s:param>
					</s:action>
				</div>
			</div>			
			<br />
			<div class="alert alert-info">
				<span class="glyphicon glyphicon-info-sign"></span> &nbsp;展示如何存取request, session和application作用域的值
			</div>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
	<script type="text/javascript">
	// 让所有超链接点击后在新窗口打开测试页面
	$("a.test").click(
			function(e){
				e.preventDefault();
				window.open(this.href, this.title);
			});
	</script>
</body>
</html>
