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
		<div class="panel-heading">stream视图</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t1_1" data-toggle="tab">测试</a></li>
				<li><a href="#t1_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t1_3" data-toggle="tab">CaptchaAction1.java</a></li>
				<li><a href="#t1_4" data-toggle="tab">CaptchaAction2.java</a></li>
				<li><a href="#t1_5" data-toggle="tab">stream.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t1_1">
					<br />
					<a href="${root}/day7/stream.jsp" class="btn btn-default test" title="stream视图">测试</a>
				</div>
				<div class="tab-pane" id="t1_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day7</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day7/CaptchaAction1.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_4">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day7/CaptchaAction2.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t1_5">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day7/stream.jsp</s:param>
					</s:action>
				</div>
				<br/>
				<div class="alert alert-info">
					<p><span class="glyphicon glyphicon-info-sign"></span> &nbsp;提示</p>
					<ol>
						<li>对于stream 视图
							<ul>
								<li>需要Action提供 <kbd>java.io.InputStream inputStream</kbd> 属性</li>
								<li>响应的ContentType, ContentLength 等信息可以通过result中的param标签来指定，也可以给Action添加相应的get方法来指定</li>
							</ul>
						</li>
						<li>直接使用response对象
							<ul>
								<li>这时action方法的返回值应当是null, 表示不采用stream的视图</li>
							</ul>
						</li>				
						<li>直接使用response对象更为灵活</li>		
						<li>如果是做文件下载，下载文件中的中文名必须编码，不同浏览器的编码方法是不一样的
							<ul>
								<li>Chrome, IE 采用 <kbd>new String("文件名".getBytes("utf-8"), "iso-8859-1")</kbd></li>
								<li>Firefox 采用 <kbd>URLEncoder.encode("文件名", "utf-8")</kbd></li>
							</ul>
						</li>
					</ol>
				</div>
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">GraphicMagick处理图像</div>
		<div class="panel-body">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs">
				<li class="active"><a href="#t2_1" data-toggle="tab">测试</a></li>
				<li><a href="#t2_2" data-toggle="tab">struts.xml</a></li>
				<li><a href="#t2_3" data-toggle="tab">CropAction.java</a></li>
				<li><a href="#t2_4" data-toggle="tab">WatermarkAction.java</a></li>
				<li><a href="#t2_5" data-toggle="tab">ResizeAction.java</a></li>
				<li><a href="#t2_6" data-toggle="tab">image.jsp</a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="t2_1">
					<br />
					<a href="${root}/day7/image.jsp" class="btn btn-default test" title="图片上传下载测试">测试</a>
				</div>
				<div class="tab-pane" id="t2_2">
					<s:action namespace="/common" name="displayXmlSrc" executeResult="true">
						<s:param name="packageName">day7</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_3">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day7/CropAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_4">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day7/WatermarkAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_5">
					<s:action namespace="/common" name="displayJavaSrc" executeResult="true">
						<s:param name="src">zpark/struts2/action/day7/ResizeAction.java</s:param>
					</s:action>
				</div>
				<div class="tab-pane" id="t2_6">
					<s:action namespace="/common" name="displayJspSrc" executeResult="true">
						<s:param name="src">day7/image.jsp</s:param>
					</s:action>
				</div>
				<br/>
				<div class="alert alert-info">
					<p><span class="glyphicon glyphicon-info-sign"></span> &nbsp;提示</p>
					<ol>
						<li>图片类型的判断，这里使用了服务器端GraphicMagick来处理</li>
                        <li>GraphicMagick是著名的图像处理程序，这里使用了im4java的java库来进行调用</li>
                        <li>裁剪、水印、缩放是它提供的比较基本的功能</li>
                        <li>裁剪的处理使用了Jcrop这个jquery插件，表单提交了选区坐标和原始图片至服务器，实际裁剪操作是在服务器端完成的</li>
                        <li>图片在服务器端处理完毕后，以stream方式写回客户端</li>
                        <li>在处理选择上传文件和显示stream结果时，均使用了<kbd>URL.createObjectURL()</kbd>这一关键方法</li>
                        <li>在发送请求和接收响应上，使用了xhr2的技术</li>
                    </ol>
				</div>
			</div>
		</div>
	</div>

  <div class="panel panel-default">
    <div class="panel-heading">Canvas处理图像</div>
    <div class="panel-body">
      <!-- Nav tabs -->
      <ul class="nav nav-tabs">
        <li class="active"><a href="#t3_1" data-toggle="tab">测试</a></li>
        <li><a href="#t3_2" data-toggle="tab">struts.xml</a></li>
        <li><a href="#t3_3" data-toggle="tab">Base64ImageAction.java</a></li>
        <li><a href="#t3_4" data-toggle="tab">base64.jsp</a></li>
      </ul>
      <!-- Tab panes -->
      <div class="tab-content">
        <div class="tab-pane active" id="t3_1">
          <br /> <a href="${root}/day7/base64.jsp" class="btn btn-default test" title="Canvas处理图像">测试</a>
        </div>
        <div class="tab-pane" id="t3_2">
          <s:action namespace="/common" name="displayXmlSrc" executeResult="true">
            <s:param name="packageName">day7</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t3_3">
          <s:action namespace="/common" name="displayJavaSrc" executeResult="true">
            <s:param name="src">zpark/struts2/action/day7/Base64ImageAction.java</s:param>
          </s:action>
        </div>
        <div class="tab-pane" id="t3_4">
          <s:action namespace="/common" name="displayJspSrc" executeResult="true">
            <s:param name="src">day7/base64.jsp</s:param>
          </s:action>
        </div>
        <br />
        <div class="alert alert-info">
          <p>
            <span class="glyphicon glyphicon-info-sign"></span> &nbsp;提示
          </p>
          <ol>
            <li>与上例不同的是，图片的裁剪、水印、缩放是在客户端使用Canvas实现的</li>
            <li>从Canvas获得的图片信息已经是处理好的结果，它是Base64格式编码的，服务器端只需考虑反编码保存即可</li>
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
