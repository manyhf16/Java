package org.yihang.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
	
	private Config config;
	
	private ThreadLocal<Connection> tLocal = new ThreadLocal<Connection>();
	
	public JdbcUtil(Config config) {
		this.config = config;
	}
	
	private void initDriverClass() {
		try {
			Class.forName(config.getDriverClass());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("jdbc driver load error", e);
		}
	}
	
	public Connection getConnection() {
		try {
			Connection conn = tLocal.get();
			if(conn == null) {
				initDriverClass();
				conn = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
				if(!config.isAutoCommit()) {
					conn.setAutoCommit(false);
				}
				tLocal.set(conn);
			}
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException("jdbc connection error", e);
		}
	}
	
	public void releaseConnetion() {
		Connection conn = tLocal.get();
		if(conn != null) {
			tLocal.set(null);
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
