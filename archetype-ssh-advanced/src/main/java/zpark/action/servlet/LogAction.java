package zpark.action.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zpark.ext.log.AsyncContexts;

@SuppressWarnings("serial")
@WebServlet(asyncSupported=true, urlPatterns="/log", name="log")
public class LogAction extends HttpServlet {
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.isAsyncSupported());
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html");
		resp.setHeader("cache-control", "private");
		PrintWriter w = resp.getWriter();
		w.print("welcome..................");
		w.print("<br>");
		w.flush();
		AsyncContexts.startAsync(req);
	}
}
