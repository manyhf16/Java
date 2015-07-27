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
		<div class="panel-heading">模型驱动</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t1_1" data-toggle="tab">普通方式给Person赋值</a></li>
				<li><a href="#t1_2" data-toggle="tab">模型驱动方式给Person赋值</a></li>
				<li><a href="#t1_3" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t1_4" data-toggle="tab">NormalAction.java</a></li>
				<li><a href="#t1_5" data-toggle="tab">ModelDrivenAction.java</a></li>
				<li><a href="#t1_6" data-toggle="tab">normal.jsp</a></li>
				<li><a href="#t1_7" data-toggle="tab">model.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t1_1">
					<br />
					<form role="form">
						<div class="form-group">
							<label for="param">请求参数</label> <input type="text" class="form-control" id="param1" placeholder="请输入请求参数"
								value="person.name=张三&person.age=18&person.city.name=北京">
						</div>
						<button type="button" class="btn btn-default" id="test1">测试</button>
					</form>
				</div>
				<div class="tab-pane" id="t1_2">
					<br />
					<form role="form">
						<div class="form-group">
							<label for="param">请求参数</label> <input type="text" class="form-control" id="param2" placeholder="请输入请求参数"
								value="name=张三&age=18&city.name=北京">
						</div>
						<button type="button" class="btn btn-default" id="test2">测试</button>
					</form>
				</div>
				<div class="tab-pane" id="t1_3">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day4</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_4">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day4/NormalAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_5">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day4/ModelDrivenAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_6">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day4/normal.jsp</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_7">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day4/model.jsp</s:param>
					</s:action>
				</div>
			</div>			
			<br />
			<div class="alert alert-info">
				<span class="glyphicon glyphicon-info-sign"></span> &nbsp; 提示
				<ol>
					<li>struts2 会将Action对象压入值栈
						<ul>
							<li>这样无论是接收请求参数，还是利用标签显示结果，都是在操作值栈中的Action对象</li>
						</ul>
					</li>					
					<li>所谓模型驱动，就是让Action实现ModelDriven接口，这样不仅会将Action对象压入值栈，还会调用getModel()方法返回的对象压入栈顶
						<ul>
							<li>这样无论是接收请求参数，还是利用标签显示结果，都是在操作getModel()方法返回的对象</li>
						</ul>					
					</li>
					<li>接收请求参数时的参数名，&lt;s:property&gt;的value属性值，其实都称之为OGNL表达式</li>
					<li>在查看示例时，注意对比请求参数和显示结果时两种方法的不同之处
						<ul>
							<li>参数名称上</li>
							<li>标签显示上</li>
							<li>栈顶对象上</li>
						</ul>
					</li>				
				</ol>				
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">遍历集合</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t2_1" data-toggle="tab">遍历List的原理</a></li>
				<li><a href="#t2_2" data-toggle="tab">遍历Map的原理</a></li>
				<li><a href="#t2_3" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t2_4" data-toggle="tab">ListAction.java</a></li>
				<li><a href="#t2_5" data-toggle="tab">MapAction.java</a></li>
				<li><a href="#t2_6" data-toggle="tab">list.jsp</a></li>
				<li><a href="#t2_7" data-toggle="tab">map.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t2_1">
					<br />
					<a href="${root}/day4/list.action" class="btn btn-default test" title="遍历List的原理">测试</a>
				</div>
				<div class="tab-pane" id="t2_2">
					<br />
					<a href="${root}/day4/map.action" class="btn btn-default test" title="遍历Map的原理">测试</a>
				</div>
				<div class="tab-pane" id="t2_3">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day4</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_4">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day4/ListAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_5">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day4/MapAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_6">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day4/list.jsp</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_7">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day4/map.jsp</s:param>
					</s:action>
				</div>
			</div>			
			<br />
			<div class="alert alert-info">
				<span class="glyphicon glyphicon-info-sign"></span> &nbsp; 提示
				<ol>
					<li><kbd>&lt;s:iterator&gt;</kbd> 标签来遍历集合时，也是利用了值栈
						<ul>
							<li>每次进入循环时将集合中的第n个对象入栈</li>
							<li>每次结束循环时再栈顶对象弹出</li>
							<li>所有循环结束后，栈顶对象仍是Action</li>
						</ul>
					</li>
					<li><kbd>&lt;s:iterator&gt;</kbd> 标签中的 <kbd>var</kbd> 的工作方式是实际是每次循环调用了<kbd>ActionContext.put("var的值", "每次遍历的对象")</kbd>
						<ul>
							<li>所以要取得每次遍历的对象要使用：<kbd>&lt;s:property value="#var的值"/&gt;</kbd></li>
						</ul>
					</li>
					<li><kbd>&lt;s:iterator&gt;</kbd> 标签中的 <kbd>status</kbd> 的工作方式是实际是每次循环调用了<kbd>ActionContext.put("status的值", IteratorStatus对象)</kbd>
						<ul>
							<li>其中IteratorStatus是包含了循环次数和循环下标等信息的一个类</li>
							<li><kbd>&lt;s:property value="#status的值.index"/&gt;</kbd> 来获取循环下标</li>
							<li><kbd>&lt;s:property value="#status的值.count"/&gt;</kbd> 来获取循环次数</li>
						</ul>
					</li>
				</ol>
			</div>
		</div>
	</div>
		
	<jsp:include page="/common/footer.jsp"></jsp:include>
	<script type="text/javascript">
		$("#test1").click(
				function() {
					window.open("${root}/day4/normal.action?"
							+ $("#param1").val(), "测试普通方式传参");
				});
		
		$("#test2").click(
				function() {
					window.open("${root}/day4/model.action?"
							+ $("#param2").val(), "测试模型驱动传参");
				});
		
		// 让所有超链接点击后在新窗口打开测试页面
		$("a.test").click(
				function(e){
					e.preventDefault();
					window.open(this.href, this.title);
				});
	</script>
</body>
</html>
