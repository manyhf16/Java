
	<thead>
		<tr>
			<#list columns as c>
			<th>${c.text}</th>
			</#list>
		</tr>
	</thead>
	<tbody>
		<#if list?? && list.size() gt 0>
		<#list list as obj>
		<tr>
			<#list columns as c>
			<td>${c.translate(obj[c.index])!}</td>
			</#list>
		</tr>
		</#list>
		<#else>
		<tr>
			<td colspan="${columns.size()}"><center>暂无记录</center></td>
		</tr>
		</#if>
	</tbody>
</table>

<#if count gt 0>
<nav class="struts_pager_${parameters.id}">
  <ul class="pagination">
  	<#if pageNo == 1>
  		<li class="disabled">
	      <a href="#" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
	    <#else>
	    <li>
	      <a href="#" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
  	</#if>    
    <#list begin..end as i>
		<#if i == pageNo>
			<li class="active"><span>${pageNo}</span></li>
		<#else>
			<li><a href="javascript:void(0)" title="${i}">${i}</a></li>
		</#if>
	</#list>
	<#if pageNo == total>
		<li class="disabled">
	      <a href="#" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
	    <#else>
	    <li>
	      <a href="#" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
  	</#if>    
  </ul>
</nav>
</#if>
<form action="" method="get" id="struts_grid_${parameters.id}">
	<input type="hidden" name="pageNo" value="${pageNo}"/>
	<input type="hidden" name="pageSize" value="${pageSize}"/>
</form>
<script type="text/javascript">
	$(".struts_pager_${parameters.id} a").click(function(){
		$("#struts_grid_${parameters.id} [name=pageNo]").val(this.title);		
		$("#struts_grid_${parameters.id}").submit();
	});
</script>