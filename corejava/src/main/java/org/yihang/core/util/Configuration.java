package org.yihang.core.util;

public interface Configuration {
	
	public abstract Config getConfig();
	
	public static Configuration JAVA_CONFIG = new Configuration() {
		
		@Override
		public Config getConfig() {
			Config config = new Config();
			config.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
			config.setUrl("jdbc:hsqldb:hsql://localhost:1234/mydb1");
			config.setUrl("SA");
			config.setPassword("");
			return config;
		}
	};

}
