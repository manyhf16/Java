<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<c:set var="static" value="${pageContext.request.contextPath}/static"></c:set>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
<title>ZPARK-班级管理</title>
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
				<li>首页</li><li>失联同学</li>
			</ol>
			<!-- end breadcrumb -->
		</div>
		<div id="content">
			<!-- widget grid -->
			<section id="widget-grid" class="">

				<!-- row -->
				<div class="row">

					<!-- NEW WIDGET START -->
					<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

						<!-- Widget ID (each widget will need unique ID)-->
						<div class="jarviswidget" id="wid-clazz-list" data-widget-editbutton="false">
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
								<span class="widget-icon"> <i class="fa fa-table"></i>
								</span>
								<h2>同学列表</h2>

							</header>

							<!-- widget div-->
							<div>

								<!-- widget edit box -->
								<div class="jarviswidget-editbox">
									<!-- This area used as dropdown edit box -->

								</div>
								<!-- end widget edit box -->

								<!-- widget content -->
								<div class="widget-body no-padding">
									<table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%">
										<thead>
											<tr>
												<th>ID</th>
												<th>用户名</th>
												<th>姓名</th>
												<th>手机</th>
												<th>邮件</th>
												<th>详情</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list}" var="c">
												<tr>
													<td>${c.id}</td>
													<td>${c.username}</td>
													<td>${c.name}</td>
													<td>${c.mobile }</td>
													<td>${c.email }</td>
													<td>
														<button class="btn-circle" onclick="window.location.href='${static}/manager/classstudent/${cls.id}'">
															<i class="fa fa-user"></i>&nbsp;联系他（她）
														</button>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

								</div>
								<!-- end widget content -->

							</div>
							<!-- end widget div -->

						</div>
						<!-- end widget -->
					</article>
					<!-- WIDGET END -->

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
	<script src="${static}/js/plugin/datatables/jquery.dataTables.min.js"></script>
	<script src="${static}/js/plugin/datatables/dataTables.colVis.min.js"></script>
	<script src="${static}/js/plugin/datatables/dataTables.tableTools.min.js"></script>
	<script src="${static}/js/plugin/datatables/dataTables.bootstrap.min.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			pageSetUp();
			$('#dt_basic').dataTable({
				"pageLength" : 6,
				"lengthMenu": [[6, 12, 18, -1], [6, 12, 18, "所有"]],
				"language" : {
					"paginate" : {
						"previous" : "上页",
						"next" : "下页"
					},
					"info": "显示<span class='txt-color-darken'>_START_</span>-<span class='txt-color-darken'>_END_</span> (共<span class='text-primary'>_TOTAL_</span>条记录)"
				}
			});
		});
	</script>
</body>
</html>