package org.yihang.common.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class DBMapperMySQL extends DBMapper {
	public void loadDriver() throws ClassNotFoundException {
		String driver = p.getProperty("jdbc.mysql.driver");
		Class.forName(driver);
	}

	public Connection newConnection() {
		try {
			String url = p.getProperty("jdbc.mysql.url");
			String username = p.getProperty("jdbc.mysql.username");
			String password = p.getProperty("jdbc.mysql.password");
			Connection conn = DriverManager.getConnection(url, username, password);
			logger.debug("open connection:{}", conn);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException("error when getConnection", e);
		}
	}

	public <T> List<T> queryList(Class<T> beanClass, int pageNum, int pageSize, String sql, Object... params) {
		if (pageNum == 1) {
			String subsql = sql + " limit ?";
			boolean isEmpty = params == null || params.length == 0;
			int newLength = (isEmpty) ? 1 : params.length + 1;
			Object[] p = new Object[newLength];
			if (isEmpty) {
				p[0] = pageSize;
			} else {
				System.arraycopy(params, 0, p, 0, params.length);
				p[params.length] = pageSize;
			}
			return queryList(beanClass, subsql, (Object[]) p);
		} else {
			String subsql = sql + " limit ?,?";
			boolean isEmpty = params == null || params.length == 0;
			int newLength = (isEmpty) ? 2 : params.length + 2;
			Object[] p = new Object[newLength];
			if (isEmpty) {
				p[0] = (pageNum - 1) * pageSize;
				p[1] = pageSize;
			} else {
				System.arraycopy(params, 0, p, 0, params.length);
				p[params.length] = (pageNum - 1) * pageSize;
				p[params.length + 1] = pageSize;
			}
			return queryList(beanClass, subsql, (Object[]) p);
		}
	}
}
