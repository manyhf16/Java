package org.yihang.core.util;

import java.sql.Connection;

public class Config {
	public static final int BATCH_SIZE = 30;
	public static final int FETCH_SIZE = 25;
	private String driverClass;
	private String url;
	private String username;
	private String password;
	private int batchSize;
	private int fetchSize;
	private int isolation = Connection.TRANSACTION_READ_COMMITTED;
	private boolean autoCommit = false;
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	public int getFetchSize() {
		return fetchSize;
	}
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}
	public int getIsolation() {
		return isolation;
	}
	public void setIsolation(int isolation) {
		this.isolation = isolation;
	}
	public boolean isAutoCommit() {
		return autoCommit;
	}
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

}
