package zpark.ext.log;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;

public abstract class AsyncContexts {

	private static Queue<AsyncContext> CONTEXTS = new ConcurrentLinkedQueue<AsyncContext>();

	public interface AsyncContextCallback {

		void invoke(AsyncContext context) throws Exception;

	}

	private static Long DEFAULT_TIMEOUT = 10  * 60 * 1000L; // 10 minutes

	public static AsyncListener DEFAULT_LISTENER = new AsyncListener() {

		@Override
		public void onTimeout(AsyncEvent event) throws IOException {
			System.out.println("onTimeout...");
			AsyncContext asyncContext = event.getAsyncContext();
			CONTEXTS.remove(asyncContext);
		}

		@Override
		public void onStartAsync(AsyncEvent event) throws IOException {
			System.out.println("onStartAsync...");
		}

		@Override
		public void onError(AsyncEvent event) throws IOException {
			System.out.println("onError...");
			System.out.println(event.getThrowable());
			AsyncContext asyncContext = event.getAsyncContext();
			CONTEXTS.remove(asyncContext);
		}

		@Override
		public void onComplete(AsyncEvent event) throws IOException {
			System.out.println("onComplete...");
			AsyncContext asyncContext = event.getAsyncContext();
			CONTEXTS.remove(asyncContext);
		}
	};

	public static void startAsync(HttpServletRequest request, long timeout, AsyncListener asyncListener) {
		AsyncContext asyncContext = request.startAsync();
		asyncContext.addListener(asyncListener);
		asyncContext.setTimeout(timeout);
		CONTEXTS.add(asyncContext);
	}

	public static void startAsync(HttpServletRequest request) {
		startAsync(request, DEFAULT_TIMEOUT, DEFAULT_LISTENER);
	}
	
	public static int size() {
		return CONTEXTS.size();
	}

	public static void each(AsyncContextCallback callback) {
		for (AsyncContext context : CONTEXTS) {
			try {
				callback.invoke(context);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("remove...");
				CONTEXTS.remove(context);
			}
		}
	}
}
