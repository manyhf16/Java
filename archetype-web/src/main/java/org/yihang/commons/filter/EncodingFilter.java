package org.yihang.commons.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private final String charset = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals("GET")) {
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(req) {
                ConcurrentHashMap<String, String> singleMap = new ConcurrentHashMap<String, String>();
                ConcurrentHashMap<String, List<String>> multiMap = new ConcurrentHashMap<String, List<String>>();

                @Override
                public String getParameter(String name) {
                    try {
                        String value = super.getParameter(name);
                        if (value == null || value.trim().length() == 0) {
                            return value;
                        }
                        String decode = singleMap.get(name);
                        if (decode == null) {
                            decode = new String(value.getBytes("iso-8859-1"), charset);
                            singleMap.put(name, decode);
                        }
                        return decode;
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }

                @Override
                public String[] getParameterValues(String name) {
                    String[] values = super.getParameterValues(name);
                    if (values == null || values.length == 0) {
                        return values;
                    }
                    try {
                        List<String> list = multiMap.get(name);
                        if (list == null) {
                            list = new ArrayList<String>();
                            for (String value : values) {
                                String decode = new String(value.getBytes("iso-8859-1"), charset);
                                list.add(decode);
                            }
                        }
                        return list.toArray(new String[] {});
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
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
