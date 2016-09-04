package zpark.ext.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodingFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(EncodingFilter.class);

	private String charset = "UTF-8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String charset = filterConfig.getInitParameter("charset");
		if (charset != null) {
			this.charset = charset;
		}
	}

	private String decode(String value) {
		try {
			return new String(value.getBytes("iso-8859-1"), charset);
		} catch (UnsupportedEncodingException e) {
			logger.warn("decoding error{}", e.getMessage());
			return null;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		final ConcurrentHashMap<String, String[]> decodedMap = new ConcurrentHashMap<String, String[]>();
		Map<String, String[]> map = req.getParameterMap();
		for (String key : map.keySet()) {
			String[] values = map.get(key);
			if (values != null) {
				String[] decodeValues = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					String d = decode(values[i]);
					decodeValues[i] = d;
				}
				decodedMap.put(key, decodeValues);
			} else {
				decodedMap.put(key, null);
			}
		}
		if (req.getMethod().equals("GET")) {
			HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(req) {
				@Override
				public String getParameter(String name) {
					String[] values = decodedMap.get(name);
					return values != null && values.length > 0 ? values[0] : null;
				}

				@Override
				public Map<String, String[]> getParameterMap() {
					return decodedMap;
				}

				@Override
				public String[] getParameterValues(String name) {
					return decodedMap.get(name);
				}
			};
			chain.doFilter(wrapper, response);
		} else {
			req.setCharacterEncoding(charset);
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
	}

}
