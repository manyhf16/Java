package org.yihang.common.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class DBMapperOracle extends DBMapper {
	public void loadDriver() throws ClassNotFoundException {
		String driver = p.getProperty("jdbc.oracle.driver");
		Class.forName(driver);
	}

	public Connection newConnection() {
		try {
			String url = p.getProperty("jdbc.oracle.url");
			String username = p.getProperty("jdbc.oracle.username");
			String password = p.getProperty("jdbc.oracle.password");
			Connection conn = DriverManager.getConnection(url, username, password);
			logger.debug("open connection:{}", conn);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException("error when getConnection", e);
		}
	}

	public <T> List<T> queryList(Class<T> beanClass, int pageNum, int pageSize, String sql, Object... params) {
		String subsql = "select * from (select rownum r, a.* from (" + sql + ") a where rownum <=?) where r > ?";

		boolean isEmpty = params == null || params.length == 0;
		Object[] p = new Object[(isEmpty) ? 2 : params.length + 2];
		if (isEmpty) {
			p[0] = pageNum * pageSize;
			p[1] = (pageNum - 1) * pageSize;
		} else {
			System.arraycopy(params, 0, p, 0, params.length);
			p[params.length] = pageNum * pageSize;
			p[params.length + 1] = (pageNum - 1) * pageSize;
		}
		return queryList(beanClass, subsql, (Object[]) p);
	}
}
