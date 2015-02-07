package zpark.advice;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope
public class RequestContext {
	
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String name){
		HttpServletRequest request = ServletActionContext.getRequest();
		return (T) request.getAttribute(name);
	}

}
