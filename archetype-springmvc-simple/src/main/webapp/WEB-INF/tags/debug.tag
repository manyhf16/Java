<%@tag import="java.util.Enumeration"%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<style>
<!--
	div.container{
		position: absolute; top: 0; left: 0; background-color: #eee;width:100%;
		opacity: 0.9;
	}
	div.debug, div.debug div.collapse{
		display: none; 
	}
	div.debug table{
		font-size: 12px; border-collapse: collapse; width:100%;
	}
	div.debug table tr.caption {
		color: white; background-color: black; font-size: 14px;
	}
	div.debug table thead th {
		border-bottom: 1px solid black;text-align: left; font-size: 14px; 
	}
	div.debug table tbody td {
		border-bottom: 1px solid black; 
	}
	div.debug table .first{
		width: 478px; border-right: 1px solid black;
	}
	
-->
</style>
<div class="container">
<a href="javascript:void(0)" id="m_debug">[debug]</a>

<div class="debug" id="m_debug_div">
<table>
	<thead>
		<tr class="caption">
			<th colspan="2">Request Scope</th>
		</tr>
		<tr>
			<th class="first">name</th>
			<th>value</th>
		</tr>
	</thead>
	<tbody>	
<%
Enumeration<String> e1 = request.getAttributeNames();
while(e1.hasMoreElements()) {
	String name = e1.nextElement();
	String value = request.getAttribute(name)!=null?request.getAttribute(name).toString():"";
	boolean isCollapse = value.length()>200;
%>
		<tr>
			<td class="first"><%=name %></td>
			<td>
				<%
				if(isCollapse) {
					out.println("<a href='javascript:void(0)' class='collapse'>&gt;&gt;&gt;</a><div class='collapse'>"+value+"</div>");
				} else {
					out.println(value);
				}
				%>
			</td>
		</tr>
<%
}

%>
	<thead>
		<tr class="caption">
			<th colspan="2">Session Scope</th>
		</tr>
		<tr>
			<th class="first">name</th>
			<th>value</th>
		</tr>
	</thead>
	<tbody>	
<%
if(session != null){
	Enumeration<String> e2 = session.getAttributeNames();
	while(e2.hasMoreElements()) {
		String name = e2.nextElement();
		String value = session.getAttribute(name)!=null?session.getAttribute(name).toString():"";
		boolean isCollapse = value.length()>200;
%>
		<tr>
			<td class="first"><%=name %></td>
			<td>
				<%
				if(isCollapse) {
					out.println("<a href='javascript:void(0)' class='collapse'>&gt;&gt;&gt;</a><div class='collapse'>"+value+"</div>");
				} else {
					out.println(value);
				}
				%>
			</td>
		</tr>
<%
	}
}
%>
	<thead>
		<tr class="caption">
			<th colspan="2">Application Scope</th>
		</tr>
		<tr>
			<th class="first">name</th>
			<th>value</th>
		</tr>
	</thead>
	<tbody>	
<%
Enumeration<String> e3 = application.getAttributeNames();
while(e3.hasMoreElements()) {
	String name = e3.nextElement();
	String value = application.getAttribute(name)!=null?application.getAttribute(name).toString():"";
	boolean isCollapse = value.length()>200;
%>
		<tr>
			<td class="first"><%=name %></td>
			<td>
				<%
				if(isCollapse) {
					out.println("<a href='javascript:void(0)' class='collapse'>&gt;&gt;&gt;</a><div class='collapse'>"+value+"</div>");
				} else {
					out.println(value);
				}
				%>
			</td>
		</tr>
<%
}

%>
	</tbody>
</table>
</div>
<script type="text/javascript">
function toggle(dom) {
	if(dom.style.display == '' || dom.style.display == 'none') {
		dom.style.display = 'block';
	} else {
		dom.style.display = 'none';
	}
}
document.getElementById("m_debug").onclick=function(){
	var div = document.getElementById("m_debug_div");
	toggle(div);
};
var div = document.getElementById("m_debug_div");
if(div.addEventListener) {
	div.addEventListener('click', function(e){
		if(e.target.className=='collapse') {
			var dom = e.target.parentNode.getElementsByTagName("div")[0];
			toggle(dom);
		}
	}, false);
} else {
    div.attachEvent("onclick",function(){
        var e = window.event;
        if(e.srcElement.className=='debugcollapse') {
            var dom = e.srcElement.parentNode.getElementsByTagName("div")[0];
            toggle(dom);
        }
    }); 
}
</script>
</div>
