package zpark.ext.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;

import org.apache.log4j.WriterAppender;

import zpark.ext.log.AsyncContexts.AsyncContextCallback;

public class WebLogAppender extends WriterAppender {

	public static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	public static volatile boolean done = false;
	private static AsyncContextCallback flushCallback = new AsyncContextCallback() {
		@Override
		public void invoke(AsyncContext context) throws Exception {
			PrintWriter writer = context.getResponse().getWriter();
			writer.flush();
		}
	};
	private static AsyncContextCallback closeCallback = new AsyncContextCallback() {
		@Override
		public void invoke(AsyncContext context) throws Exception {
			PrintWriter writer = context.getResponse().getWriter();
			writer.close();
		}
	};

	public static class WebLogWriter extends Writer {

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			String message = new String(cbuf, off, len);
			try {
				queue.put(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void flush() throws IOException {
			AsyncContexts.each(flushCallback);
		}

		@Override
		public void close() throws IOException {
			AsyncContexts.each(closeCallback);
		}

	}

	private Writer writer;

	public static void shutdown() {
		done = true;
		try {
			queue.put("shutdown");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WebLogAppender() {
		System.out.println("WebLogAppender 初始化ok...");
		this.writer = new WebLogWriter();
		this.setWriter(writer);
		done = false;
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(this.getClass());
				while (!done) {
					String str = null;
					try {
						str = queue.take().replaceAll("\n", "").replaceAll("\r", "");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(str != null) {
						final String message = str;
						AsyncContexts.each(new AsyncContextCallback() {
							@Override
							public void invoke(AsyncContext context) throws Exception {
								PrintWriter writer = context.getResponse().getWriter();
								writer.println(message);
								writer.print("<br>");
								writer.flush();
								if (writer.checkError()) {
									throw new IOException("maybe disconnect");
								}
							}
						});
					}
				}
				System.out.println("thread:" + Thread.currentThread().getName() + " is stopped.");
			}
		}).start();
	}

}
