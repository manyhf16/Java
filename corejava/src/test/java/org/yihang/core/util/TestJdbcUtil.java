package org.yihang.core.util;

import java.io.PrintWriter;

import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestJdbcUtil {
	static Server server;

	@BeforeClass
	public static void start() {
		server = new Server();
		server.setAddress("localhost");
		server.setDatabaseName(0, "mydb1");
		server.setDatabasePath(0, "file:E:/hsqldb_databases/db");
		server.setPort(1234);
		server.setTrace(true);
		server.setLogWriter(new PrintWriter(System.out));
		server.start();
	}
	
	@AfterClass
	public static void stop() {
		server.stop();
	}

}
