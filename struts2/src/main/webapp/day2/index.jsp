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
		<div class="panel-heading">测试请求参数</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t1_1" data-toggle="tab">测试</a></li>
				<li><a href="#t1_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t1_3" data-toggle="tab">ParameterAction.java</a></li>
				<li><a href="#t1_4" data-toggle="tab">parameter.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t1_1">
					<br />
					<form role="form">
						<div class="form-group">
							<label for="param">请求参数</label> <input type="text" class="form-control" id="param" placeholder="请输入请求参数"
								value="name=张三&age=18&gender=male&birthday=2015-1-1&hobby=唱歌&hobby=游泳&user.username=zhangsan&user.realname=张三">
						</div>
						<button type="button" class="btn btn-default" id="test1">测试</button>
					</form>
				</div>
				<div class="tab-pane" id="t1_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day2</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day2/ParameterAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_4">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day2/parameter.jsp</s:param>
					</s:action>
				</div>
			</div>			
			<br />
			<div class="alert alert-info">
				<span class="glyphicon glyphicon-info-sign"></span> &nbsp;可以改变地址栏的参数值，来进行测试
			</div>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">测试重定向到Action</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t2_1" data-toggle="tab">测试</a></li>
				<li><a href="#t2_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t2_3" data-toggle="tab">Action1.java</a></li>
				<li><a href="#t2_4" data-toggle="tab">Action2.java</a></li>
				<li><a href="#t2_5" data-toggle="tab">action2.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t2_1">
					<br />
					<a href="${root}/day2/action1.action" class="btn btn-default test" title="测试重定向到Action">测试</a>
				</div>
				<div class="tab-pane" id="t2_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day2</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day2/Action1.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_4">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day2/Action2.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_5">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day2/action2.jsp</s:param>
					</s:action>
				</div>
			</div>
			<br/>
			<div class="alert alert-info">
				<span class="glyphicon glyphicon-info-sign"></span> &nbsp; 提示
				<ol>
					<li>在struts.xml文件中
						<ul>
							<li>视图的<kbd>type="dispatcher"</kbd>是默认值，表示请求从action转发至目标，目标只能是jsp</li>
							<li>视图的<kbd>type="redirect"</kbd>表示请求从action重定向至目标，目标可以是jsp或action</li>
						</ul>
					</li>
					<li>点击<kbd>测试</kbd>前，注意按钮的链接地址为 <kbd>/day2/action1.action</kbd></li>
					<li>点击<kbd>测试</kbd>后，看请求地址栏是否变化为<kbd>/day2/action2.action</kbd>，如果是说明请求被重定向</li>
					<li>看在action中向session存储的name变量是否正确显示</li>					
				</ol>				
			</div>
		</div>
	</div>

	<jsp:include page="/common/footer.jsp"></jsp:include>
	<script type="text/javascript">
		$("#test1").click(
				function() {
					window.open("${root}/day2/parameter.action?"+ $("#param").val(), "测试请求参数");
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
