<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
request.setAttribute("root", root);
Matcher m = Pattern.compile("day\\d{1,2}").matcher(request.getRequestURI());
%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Struts2<%=m.find()?"("+m.group()+")":""%></title>
<link type="text/css" rel="stylesheet" href="${root}/bootstrap/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="${root}/bootstrap/css/prettify.css">
<!-- <link type="text/css" rel="stylesheet" href="${root}/bootstrap/css/desert.css"> -->
<link type="image/x-icon" rel="icon" href="${root}/favicon.ico">
<style> body{padding-top: 60px; padding-left:10px; padding-right:10px;} .jumbotron{padding:10px;}</style>
<script type="text/javascript" src="${root}/bootstrap/js/jquery-1.10.2.js"></script>
