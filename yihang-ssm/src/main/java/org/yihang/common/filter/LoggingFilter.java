package org.yihang.common.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if (logger.isDebugEnabled()) {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(resp) {
				private PrintWriter out = new PrintWriter(baos, true);

				@Override
				public PrintWriter getWriter() throws IOException {
					return out;
				}

				@Override
				public ServletOutputStream getOutputStream() throws IOException {
					return new ServletOutputStream() {
						@Override
						public void write(int b) throws IOException {
							baos.write(b);

						}
					};
				}
			};
			chain.doFilter(request, wrapper);
			wrapper.getWriter().close();
			baos.writeTo(resp.getOutputStream());
			baos.close();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss Z");
			logger.debug("{} - - [{}] \"{} {} {}\" {} {} \"-\" \"{}\"", request.getRemoteAddr(), sdf.format(new Date()),
					req.getMethod(), req.getRequestURI(), req.getProtocol(), resp.getStatus(), baos.size(),
					req.getHeader("USER-AGENT"));
		}

	}

	@Override
	public void destroy() {
	}

}
