<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<c:set var="static" value="${pageContext.request.contextPath}/static"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
<title>ZPARK-新增班级</title>
<jsp:include page="/WEB-INF/view/includes/head.jsp" />
</head>

<body class="">
	<jsp:include page="/WEB-INF/view/includes/header.jsp" />
	<jsp:include page="/WEB-INF/view/includes/aside.jsp" />
	<!-- MAIN PANEL -->
	<div id="main">
		<!-- RIBBON -->
		<div id="ribbon">
			<span class="ribbon-button-alignment"> 
				<span id="refresh" class="btn btn-ribbon" data-action="resetWidgets" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> 警告，点击此按钮会重置您的页面偏好设置" data-html="true">
					<i class="fa fa-refresh"></i>
				</span> 
			</span>

			<!-- breadcrumb -->
			<ol class="breadcrumb">
				<li>首页</li><li>学员管理</li><li>新增班级</li>
			</ol>
			<!-- end breadcrumb -->
		</div>
		<div id="content">
			<!-- widget grid -->
			<section id="widget-grid" class="">

				<!-- row -->
				<div class="row">

					<article class="col-sm-12 col-md-12 col-lg-12">
				
							<!-- Widget ID (each widget will need unique ID)-->
							<div class="jarviswidget" id="wid-clazz-add" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false">
								<!-- widget options:
								usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">
				
								data-widget-colorbutton="false"
								data-widget-editbutton="false"
								data-widget-togglebutton="false"
								data-widget-deletebutton="false"
								data-widget-fullscreenbutton="false"
								data-widget-custombutton="false"
								data-widget-collapsed="true"
								data-widget-sortable="false"
				
								-->
								<header>
									<span class="widget-icon"> <i class="fa fa-edit"></i> </span>
									<h2>新增班级</h2>
				
								</header>
				
								<!-- widget div-->
								<div>
				
									<!-- widget edit box -->
									<div class="jarviswidget-editbox">
										<!-- This area used as dropdown edit box -->
				
									</div>
									<!-- end widget edit box -->
				
									<!-- widget content -->
									<div class="widget-body">
				
										<form class="smart-form" action="${app}/actions/clazz/doadd" method="post" >
											<header>
												请输入班级信息
											</header>
											<fieldset>
												<section>
													<div class="form-group">
														<label class="label">任课老师：</label>
														<select style="width:100%" class="select2" >
															<optgroup label="教学部">
																<option value="胡老师">胡老师</option>
																<option value="圈姐">圈姐</option>
																<option value="陆老师">陆老师</option>
																<option value="孙哥">孙哥</option>
																<option value="杨老师">杨老师</option>
																<option value="满神">满神</option>
																<option value="红久">红久</option>																
															</optgroup>
															<optgroup label="项目部">
																<option value="老蒋">老蒋</option>
																<option value="涛涛">涛涛</option>
															</optgroup>
														</select>
													</div>
													<div class="note">
														这里考虑不是很周到，应该是“现任”的老师，并且还应该有助教的信息
													</div>
												</section>
												<section>
													<label class="label">编号：</label>
													<label class="input">
														<input type="text" placeholder='例如22班：22000' name="id" >
													</label>													
												</section>
												<section>
													<label class="label">班级名称：</label>
													<label class="input">
														<input type="text" placeholder='例如：22班' name="name" >
													</label>													
												</section>
												<section>
													<label class="label">开班时间：</label>
													<label class="input"> <i class="icon-append fa fa-calendar"></i>
														<input type="text" name="createtime" placeholder="请选择" class="datepicker" data-dateformat="yy-mm-dd">
													</label>
												</section>
											</fieldset>											
											<footer>
												<button type="button" id="eg4" class="btn btn-primary">
													提交
												</button>
												<button type="button" class="btn btn-default" onclick="window.history.back();">
													后退
												</button>
											</footer>
										</form>
									</div>
									<!-- end widget content -->
				
								</div>
								<!-- end widget div -->
				
							</div>
							<!-- end widget -->
				
						</article>
						<!-- END COL -->

				</div>

				<!-- end row -->

				<!-- end row -->

			</section>
			<!-- end widget grid -->
		</div>
	</div>
	<div id="shortcut">[METRO_SHORTCUT]</div>
	<jsp:include page="/WEB-INF/view/includes/scripts.jsp"></jsp:include>
	<!-- PAGE RELATED PLUGIN(S) -->

	<script type="text/javascript">
	$(".select2").each(function() {
        var a = $(this),
        b = a.attr("data-select-width") || "100%";
        a.select2({
            allowClear: !0,
            width: b
        })
    });
	$('.datepicker').datepicker();
	$('#eg4').click(function(e) {
		for(var i = 0; i < 10;i++) {
			$.bigBox({
				title : "Success Message Example",
				content : "Lorem ipsum dolor sit amet, test consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
				color : "#739E73",
				//timeout: 8000,
				icon : "fa fa-check",
				number : i
			}, function() {
				//closedthis();
			});
		}
		e.preventDefault();

	});

	</script>
</body>
</html>
