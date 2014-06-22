package zpark.ext.struts;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module.Feature;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;

@SuppressWarnings("serial")
public class JsonResultInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(JsonResultInterceptor.class);

	private ObjectMapper mapper;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
		mapper = new ObjectMapper();
		Hibernate3Module hibernate3Module = new Hibernate3Module();
		hibernate3Module.enable(Feature.FORCE_LAZY_LOADING);
		mapper.registerModule(hibernate3Module);
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext.getContext().getActionInvocation().addPreResultListener(new PreResultListener() {
			@Override
			public void beforeResult(ActionInvocation invocation, String resultCode) {
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
				String ajaxHeader = request.getHeader("X-Requested-With");
				if (ajaxHeader != null && ajaxHeader.equalsIgnoreCase("XMLHttpRequest")) {
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/json");
					try {
						mapper.writeValue(response.getWriter(), invocation.getAction());
					} catch (Exception e) {
						logger.error("json mapper error", e);
						try {
							response.getWriter().print("{\"error\":\"" + e.getMessage() + "\"}");
						} catch (IOException ex) {
						}
					} finally {
						invocation.getProxy().setExecuteResult(false);
					}
				}
			}
		});
		return invocation.invoke();
	}

}
