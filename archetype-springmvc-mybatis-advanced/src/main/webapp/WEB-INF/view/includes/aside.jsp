<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<c:set var="s" value="${pageContext.request.contextPath}/static"></c:set>
<aside id="left-panel">
	<!-- User info -->
	<div class="login-info">
		<span> <!-- User image size is adjusted inside CSS, it should stay as it --> <a href="javascript:void(0);" id="show-shortcut" data-action="toggleShortcut"> <img src="${s}/img/avatars/1.png"
				alt="me" class="online" /> <span> 李婷婷 </span> <i class="fa fa-angle-down"></i>
		</a>
		</span>
	</div>
	<!-- end user info -->
	<nav>
		<ul>
			<li class="${activeMap.dashboard}"><a href="index.html" title="公告栏"><i class="fa fa-lg fa-fw fa-home"></i> <span class="menu-item-parent">公告栏</span></a></li>
			<li class="${activeMap.classmate.all}"><a href="${app}/classmate/all"><i class="fa fa-lg fa-fw fa-tasks"></i> <span class="menu-item-parent">同学</span></a></li>
			<li class="${activeMap.classmate.lost}"><a href="${app}/classmate/lost"><i class="fa fa-lg fa-fw fa-tasks"></i> <span class="menu-item-parent">失联同学</span></a></li>
		</ul>
	</nav>
	<span class="minifyme" data-action="minifyMenu"> <i class="fa fa-arrow-circle-left hit"></i>
	</span>
</aside>
