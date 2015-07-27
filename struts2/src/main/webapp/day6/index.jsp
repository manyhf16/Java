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
		<div class="panel-heading">重定向传参</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t1_1" data-toggle="tab">测试</a></li>
				<li><a href="#t1_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t1_3" data-toggle="tab">RedirectAction1.java</a></li>
				<li><a href="#t1_4" data-toggle="tab">RedirectAction2.java</a></li>
				<li><a href="#t1_5" data-toggle="tab">r_action2.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t1_1">
					<br />
					<a href="${root}/day6/r_action1.action" class="btn btn-default test" title="重定向传参">测试</a>
				</div>
				<div class="tab-pane" id="t1_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day6</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day6/RedirectAction1.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_4">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day6/RedirectAction2.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_5">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day6/r_action2.jsp</s:param>
					</s:action>
				</div>
				<br/>
				<div class="alert alert-info">
					<p><span class="glyphicon glyphicon-info-sign"></span> &nbsp;提示</p>
					<ol>
						<li>对于使用方法1（利用session进行重定向传值）如果该值显示完毕后不再使用，应当及时清除，避免占用session内存</li>
						<li>方法2的好处是更为通用，不占用session内存，可以跨应用传值；缺点是只能传递字符串类型值，并且需要考虑编码问题</li>
					</ol>
				</div>
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">Chain传参</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t2_1" data-toggle="tab">测试</a></li>
				<li><a href="#t2_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t2_3" data-toggle="tab">ChainAction1.java</a></li>
				<li><a href="#t2_4" data-toggle="tab">ChainAction2.java</a></li>
				<li><a href="#t2_5" data-toggle="tab">c_action2.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t2_1">
					<br />
					<a href="${root}/day6/c_action1.action" class="btn btn-default test" title="Chain传参(不带请求参数)">测试(不带请求参数)</a>
					<a href="${root}/day6/c_action1.action?name=请求值" class="btn btn-default test" title="Chain传参(带请求参数)">测试(带请求参数)</a>
				</div>
				<div class="tab-pane" id="t2_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day6</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day6/ChainAction1.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_4">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day6/ChainAction2.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_5">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day6/c_action2.jsp</s:param>
					</s:action>
				</div>
				<br/>
				<div class="alert alert-info">
					<p><span class="glyphicon glyphicon-info-sign"></span> &nbsp;chain是较少使用的一种从action到action的视图类型，要注意：</p>
					<ul>
						<li>chain本质上使用了请求转发，整个流程是一次请求</li>
						<li>chain会先后将ChainAction1、ChainAction2入栈</li>
						<li>chain会将栈中除ChainAction2之外的对象属性赋值（复制）给ChainAction2</li>
						<li>由于chain是用拦截器实现，chain拦截器之后还有params拦截器，因此执行复制操作后的属性可能会被请求参数值覆盖</li>
						<li>chain过程中的Action对象，它们的ValueStack是同一个，但ActionContext各自是各自的</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">参数验证</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t3_1" data-toggle="tab">测试</a></li>
				<li><a href="#t3_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t3_3" data-toggle="tab">ValidationAction.java</a></li>
				<li><a href="#t3_4" data-toggle="tab">success.jsp</a></li>
				<li><a href="#t3_5" data-toggle="tab">input.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t3_1">
					<br />
					<a href="${root}/day6/validation.action" class="btn btn-default test" title="参数验证">测试</a>
				</div>
				<div class="tab-pane" id="t3_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day6</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t3_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day6/ValidationAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t3_4">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day6/success.jsp</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t3_5">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day6/input.jsp</s:param>
					</s:action>
				</div>
				<br/>
				<div class="alert alert-info">
					<p><span class="glyphicon glyphicon-info-sign"></span> &nbsp;提示</p>
					<ul>
						<li>struts2 中有三种参数验证方法：编程式、配置文件式、注解式，这里只介绍注解式</li>
						<li>action必须继承父类  <kbd>ActionSupport</kbd> </li>
						<li>验证通过，才会执行execute方法的内容，验证不通过，会去寻找一个名为 <kbd>input</kbd> 的视图</li>
						<li>如果验证的是复杂类型，例如上例中的 <kbd>person.name</kbd> ，只能用类级别的注解进行验证，而不能使用属性级别的注解验证</li>
						<li>默认所有请求参数都会进行验证（非短路），可以利用 <kbd>&lt;s:fielderror/&gt;</kbd> 来显示所有的验证错误信息</li>
						<li>属性级别的验证才能配置是否短路，类级别的验证总是非短路的</li>
						<li>类型转换错误对验证的影响：
							<ul>
								<li>请求参数的类型转换发生在参数验证之前</li>
								<li>类型转换的错误提示信息对应的key为: <kbd>xwork.default.invalid.fieldvalue</kbd> 可以使用自定义的资源文件进行覆盖</li>
								<li>例如上例中 age是整型，当输入非数字时验证失败，这时错误的age值仍会在值栈被找到，这是ConversionErrorInterceptor拦截器起到的作用，但这么做会引发一种攻击（这超出了本文讨论的范围）</li>
								<li>struts为了实现验证的通用性，牺牲了一定的灵活性和安全性</li>
							</ul>
						</li>
					</ul>
				</div>
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
