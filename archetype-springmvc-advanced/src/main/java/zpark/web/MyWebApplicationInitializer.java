package zpark.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("enter");
		Dynamic dynamic1 = servletContext.addServlet("m1", new DispatcherServlet());
		dynamic1.setLoadOnStartup(1);
		dynamic1.addMapping("/");
//		Dynamic dynamic2 = servletContext.addServlet("m2", new DispatcherServlet());
//		dynamic2.setLoadOnStartup(1);
//		dynamic2.addMapping("/m2");
		
		servletContext.addListener(ContextLoaderListener.class);
		servletContext.setInitParameter("contextConfigLocation", "classpath:spring/application-config.xml");
	}

}
