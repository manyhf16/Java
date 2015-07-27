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
		<div class="panel-heading">测试Root</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t1_1" data-toggle="tab">测试</a></li>
				<li><a href="#t1_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t1_3" data-toggle="tab">RootAction.java</a></li>
				<li><a href="#t1_4" data-toggle="tab">root.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t1_1">
					<br />
					<a href="${root}/day3/root.action" class="btn btn-default test" title="测试Context">测试</a>
				</div>
				<div class="tab-pane" id="t1_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day3</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day3/RootAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_4">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day3/root.jsp</s:param>
					</s:action>
				</div>
			</div>			
			<br />
			<div class="alert alert-info">
				<span class="glyphicon glyphicon-info-sign"></span> &nbsp; 提示
				<ol>
					<li>通过<kbd>&lt;s:debug/&gt;</kbd>标签可以看到值栈中的内容</li>
					<li>root 部分是一个栈结构，符合后进先出的规则</li>
					<li>点击第一个<kbd>debug</kbd>标签看到root的初始内容</li>
					<li>点击第二个<kbd>debug</kbd>标签看到经过几次pop操作后root的内容</li>
					<li>关键之处在于，struts会将当前请求的Action对象放入值栈root，这样就能够从root获取Action对象的属性值</li>
					<li>
						<span>实际开发中，不会要求程序员直接操作值栈，而是配合struts标签来获取和显示值栈内容</span>
						<ul>
							<li><kbd>&lt;s:property/&gt;</kbd>可以获得(peek)并显示栈顶对象</li>
							<li><kbd>&lt;s:property value="属性"/&gt;</kbd>可以从栈顶自上向下查找第一个名字匹配的对象属性并显示</li>
							<li>如果对象属性又是一个复杂类型，可以使用<kbd>&lt;s:property value="属性.属性"/&gt;</kbd>来显示值</li>
						</ul>
					</li>				
				</ol>				
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">测试Context</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t2_1" data-toggle="tab">测试</a></li>
				<li><a href="#t2_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t2_3" data-toggle="tab">ContextAction.java</a></li>
				<li><a href="#t2_4" data-toggle="tab">context.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t2_1">
					<br />
					<a href="${root}/day3/context.action" class="btn btn-default test" title="测试Context">测试</a>
				</div>
				<div class="tab-pane" id="t2_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day3</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day3/ContextAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_4">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day3/context.jsp</s:param>
					</s:action>
				</div>
			</div>			
			<br />
			<div class="alert alert-info">
				<span class="glyphicon glyphicon-info-sign"></span> &nbsp; 提示
				<ol>
					<li>通过<kbd>&lt;s:debug/&gt;</kbd>标签可以看到值栈中的内容</li>
					<li>context 部分是一个Map结构，其中有一些预定义的键值，例如：
						<ul>
							<li>key: request,  value: requestMap</li>
							<li>key: session,  value: sessionMap</li>
							<li>key: application,  value: applicationMap</li>
						</ul>
					</li>
					<li>用标签取值时使用：<kbd>&lt;s:property value="#key"/&gt;</kbd>
						例如：
						<ul>
							<li><kbd>&lt;s:property value="#session"/&gt;</kbd>  是取到整个sessionMap</li>
							<li><kbd>&lt;s:property value="#session.name"/&gt;</kbd>  是取到sessionMap中key="name"的作用域变量值</li>
						</ul>
					</li>				
				</ol>				
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
