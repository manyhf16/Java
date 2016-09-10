package org.yihang.common.jdbc;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提高版的jdbc工具类
 * 
 * @author yihang
 * 
 */
public abstract class DBMapper {

	// 提供DBMapper懒实例化
	private static class DBMapperHolder {

		public static DBMapper instance;
		static {
			init("/db.properties");
		}

		protected static void init(String config) {
			try (InputStream is = DBMapper.class.getResourceAsStream(config)) {
				p.load(is);
				String dialectClass = p.getProperty("jdbc.dialect");
				instance = (DBMapper) Class.forName(dialectClass).newInstance();

				// 存储基本的类型，将来这些类型可以统一转换
				basicClasses.add(Long.class);
				basicClasses.add(Short.class);
				basicClasses.add(Integer.class);
				basicClasses.add(Character.class);
				basicClasses.add(Double.class);
				basicClasses.add(Float.class);
				basicClasses.add(Byte.class);
				basicClasses.add(Boolean.class);
				basicClasses.add(String.class);
				basicClasses.add(BigInteger.class);
				basicClasses.add(BigDecimal.class);
				basicClasses.add(Date.class);
			} catch (Throwable e) {
				throw new ExceptionInInitializerError(e);
			}
		}
	}

	// 日志类，方便调试sql语句及运行状态
	protected static Logger logger = LoggerFactory.getLogger(DBMapper.class);

	// 存储db.properties信息
	protected static Properties p = new Properties();

	// 存取当前线程的事务状态
	private ThreadLocal<Deque<TxInfo>> txs = new ThreadLocal<>();

	private ThreadLocal<Boolean> ac = new ThreadLocal<>();

	// 存取当前线程的连接对象
	private ThreadLocal<Connection> conns = new ThreadLocal<>();

	// 保存能够由DBMapper处理的基本类型
	protected static List<Class<?>> basicClasses = new ArrayList<>();

	// 用来缓存实体类的反射属性相关信息
	private static Map<Class<?>, Map<String, PropertyDescriptor>> all = new ConcurrentHashMap<>();

	/**
	 * 通用查询方法，用来执行分页查询，需要子类提供具体实现
	 * 
	 * @param <T>
	 * @param beanClass
	 *            要封装的对象类型
	 * @param sql
	 *            sql语句
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            页面大小
	 * @param params
	 *            参数值
	 * @return
	 */
	public abstract <T> List<T> queryList(Class<T> beanClass, int pageNum, int pageSize, String sql, Object... params);

	/**
	 * 创建一个新的数据库连接，需要子类提供具体实现
	 * 
	 * @return
	 */
	public abstract Connection newConnection();

	/**
	 * 加载数据库驱动类，需要子类提供具体实现
	 * 
	 * @throws ClassNotFoundException
	 */
	public abstract void loadDriver() throws ClassNotFoundException;

	/**
	 * 获取DBMapper单实例
	 * 
	 * @return
	 */
	public static DBMapper getInstance() {
		return DBMapperHolder.instance;
	}

	/**
	 * 创建事务代理
	 * 
	 * @param target
	 *            要代理的目标对象
	 * @param interfaces
	 *            目标对象接口
	 * @return 代理对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T createProxy(Object target, Class<T> interfaces) {
		return (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[] { interfaces },
				new TransactionAdvice(target));
	}

	// 事务通知类
	private class TransactionAdvice implements InvocationHandler {
		private Object target;

		public TransactionAdvice(Object target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("toString")) {
				return target.toString();
			}
			Transactional transactional = method.getAnnotation(Transactional.class);
			if (transactional == null) {
				transactional = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes())
						.getAnnotation(Transactional.class);
			}
			Deque<TxInfo> stack = txs.get();
			if (transactional != null) {
				Propagation propagation = transactional.propagation();
				if (Propagation.REQUIRED == propagation) {
					if (stack == null) { // 还没有事务
						stack = new LinkedList<TxInfo>();
						stack.push(new TxInfo(TxStatus.NEW, null));
						txs.set(stack);
					} else {
						TxInfo current = stack.peek();
						stack.push(new TxInfo(TxStatus.PARTICIPATING, current));
					}
				} else if (Propagation.REQUIRES_NEW == propagation) {
					if (stack == null) { // 还没有事务
						stack = new LinkedList<TxInfo>();
						txs.set(stack);
					}
					stack.push(new TxInfo(TxStatus.NEW, null));
				} else if (Propagation.SUPPORTS == propagation) {
					if (stack != null) {
						TxInfo current = stack.peek();
						stack.push(new TxInfo(TxStatus.PARTICIPATING, current));
					}
				}
			}
			Object result = null;
			try {
				if (transactional != null && stack != null) {
					logger.debug("before method:{} transaction stack:{}", method.getName(), stack);
				}
				method.setAccessible(true);
				result = method.invoke(target, args);
				if (transactional != null && stack != null) {
					TxInfo current = stack.pop();
					if (current.isNew()) {
						logger.debug("close and commit transactional connection:{}", current.getConnection());
						current.getConnection().commit();
						current.getConnection().setAutoCommit(ac.get());
						current.getConnection().close();
					}
				}
				return result;
			} catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
				// e.printStackTrace();
				if (transactional != null && stack != null) {
					TxInfo current = stack.pop();
					if (current.isNew()) {
						logger.debug("close and rollback transactional connection:{}", current.getConnection());
						current.getConnection().rollback();
						current.getConnection().setAutoCommit(ac.get());
						current.getConnection().close();
					}
				}
				throw e.getCause();
			} finally {
				if (transactional != null && stack != null) {
					if (stack.isEmpty()) {
						txs.set(null);
					}
					logger.debug("after method:{} transaction stack:{}", method.getName(), stack);
				} else {
					close();
				}
			}
		}

	}

	/**
	 * 辅助类，用于跟踪事务状态
	 * 
	 * @author yihang
	 * 
	 */
	private static enum TxStatus {
		NEW, PARTICIPATING;
	}

	private class TxInfo {
		private TxStatus status;
		private Connection connection;

		public TxInfo(TxStatus status, TxInfo prev) {
			this.status = status;
			if (isNew()) {
				this.connection = newConnection();
				try {
					ac.set(this.connection.getAutoCommit());
					this.connection.setAutoCommit(false);
				} catch (SQLException e) {
					throw new RuntimeException("error when begin transaction", e);
				}
			} else {
				this.connection = prev.getConnection();
			}
		}

		public Connection getConnection() {
			return connection;
		}

		public boolean isNew() {
			return this.status == TxStatus.NEW;
		}

		@Override
		public String toString() {
			return "TxInfo [status=" + status + ", connection=" + connection + "]";
		}

	}

	/**
	 * 释放资源，包括ResultSet与Statement
	 * 
	 * @param rs
	 * @param stmt
	 */
	public void release(Object... resources) {
		if (resources != null) {
			for (Object r : resources) {
				if (r instanceof ResultSet) {
					ResultSet rs = (ResultSet) r;
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}
			}
			for (Object r : resources) {
				if (r instanceof Statement) {
					Statement stmt = (Statement) r;
					try {
						stmt.close();
					} catch (SQLException e) {
					}
				}
			}
		}
	}

	/**
	 * 关闭连接对象，用在非代理情况下，连接关闭
	 * <p>
	 * 代理模式下，事务结束会自动关闭连接
	 * </p>
	 */
	public void close() {
		Connection conn = conns.get();
		if (conn != null) {
			try {
				conn.setAutoCommit(ac.get());
				conn.close();
				logger.debug("close non-transactional connection:{}", conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conns.set(null);
		}
	}

	// 获取连接对象
	private Connection getConnection() {
		Deque<TxInfo> stack = txs.get();
		if (stack == null) {
			Connection conn = conns.get();
			if (conn == null) {
				conn = newConnection();
				conns.set(conn);
			}
			return conn;
		} else {
			return stack.peek().getConnection();
		}
	}

	/**
	 * 给preparedStatement中所有?占位符赋值
	 * 
	 * @param psmt
	 * @param params
	 * @throws SQLException
	 */
	private void prepare(PreparedStatement psmt, Object... params) throws SQLException {
		if (params != null) {
			int i = 1;
			for (Object o : params) {
				if (o instanceof java.util.Date) {
					java.util.Date d = (java.util.Date) o;
					psmt.setTimestamp(i, new Timestamp(d.getTime()));
				} else {
					psmt.setObject(i, o);
				}
				i++;
			}
		}
	}

	/**
	 * 得到某一实体列中属性反射信息的map集合：key是属性名(全小写), value是该属性的反射信息 <br>
	 * 为了避免每次都获取这些信息(比较耗时)，用了一个all集合来缓存上次的结果
	 * 
	 * @param beanClass
	 * @return
	 * @throws IntrospectionException
	 */
	private Map<String, PropertyDescriptor> getPropertyDescriptors(Class<?> beanClass) throws IntrospectionException {
		Map<String, PropertyDescriptor> map = all.get(beanClass);
		if (map == null) {
			map = new HashMap<String, PropertyDescriptor>();
			PropertyDescriptor[] pds = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				map.put(pd.getName().toLowerCase(), pd);
			}
			all.put(beanClass, map);
		}
		return map;
	}

	/**
	 * 通用update,delete,insert方法
	 * 
	 * @param sql
	 * @param params
	 * @return 影响行数
	 */
	public int update(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			prepare(psmt, params);
			if (logger.isDebugEnabled()) {
				logger.debug("SQL: [{}]", sql);
				logger.debug("SQL params: {}", Arrays.toString(params));
			}
			return psmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(psmt);
		}
	}

	/**
	 * 通用insert方法(需要获得主键值时使用)
	 * 
	 * @param sql
	 *            sql语句
	 * @param pkColumnName
	 *            主键列的名字
	 * @param params
	 *            参数值
	 * @return 主键值
	 */
	public <T> T insert(String sql, String pkColumnName, Class<T> pkColumnClass, Object... params) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql, new String[] { pkColumnName });
			prepare(psmt, params);
			if (logger.isDebugEnabled()) {
				logger.debug("SQL: [{}]", sql);
				logger.debug("SQL params: {}", Arrays.toString(params));
			}
			psmt.executeUpdate();
			rs = psmt.getGeneratedKeys();
			boolean empty = !rs.next();
			if (empty) {
				throw new RuntimeException("no generated keys");
			}
			return processOneCloumn(pkColumnClass, rs, 1);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(psmt);
		}
	}

	/**
	 * 通用查询方法，可以用来查询单个对象或单值
	 * 
	 * @param <T>
	 * @param beanClass
	 *            要封装的对象类型
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数值
	 * @return
	 */
	public <T> T queryOne(Class<T> beanClass, String sql, Object... params) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			prepare(psmt, params);
			if (logger.isDebugEnabled()) {
				logger.debug("SQL: [{}]", sql);
				logger.debug("SQL params: {}", Arrays.toString(params));
			}
			rs = psmt.executeQuery();
			if (rs.next()) {
				if (beanClass.isPrimitive() || basicClasses.contains(beanClass)) {
					return processOneCloumn(beanClass, rs, 1);
				} else {
					Map<String, PropertyDescriptor> pds = getPropertyDescriptors(beanClass);
					ResultSetMetaData rsmd = rs.getMetaData();
					T beanInstance = beanClass.newInstance();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String colName = rsmd.getColumnLabel(i);
						// logger.debug("colName:" + colName);
						PropertyDescriptor pd = pds.get(colName.toLowerCase());
						Object colValue = processOneCloumn(pd.getPropertyType(), rs, i);
						pd.getWriteMethod().invoke(beanInstance, colValue);
					}
					return beanInstance;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(rs, psmt);
		}
	}

	/**
	 * 处理结果集中每一列
	 * 
	 * @param beanClass
	 * @param rs
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private <T> T processOneCloumn(Class<T> beanClass, ResultSet rs, int index) throws SQLException {
		Object o = rs.getObject(index);
		if (!beanClass.isPrimitive() && rs.wasNull()) {
			return null;
		}
		if (beanClass == int.class || beanClass == Integer.class) {
			return (T) Integer.valueOf(rs.getInt(index));
		}
		if (beanClass == long.class || beanClass == Long.class) {
			return (T) Long.valueOf(rs.getLong(index));
		}
		if (beanClass == short.class || beanClass == Short.class) {
			return (T) Short.valueOf(rs.getShort(index));
		}
		if (beanClass == double.class || beanClass == Double.class) {
			return (T) Double.valueOf(rs.getDouble(index));
		}
		if (beanClass == float.class || beanClass == Float.class) {
			return (T) Float.valueOf(rs.getFloat(index));
		}
		if (beanClass == boolean.class || beanClass == Boolean.class) {
			return (T) Boolean.valueOf(rs.getBoolean(index));
		}
		if (beanClass == byte.class || beanClass == Byte.class) {
			return (T) Byte.valueOf(rs.getByte(index));
		}
		if (beanClass == String.class) {
			return (T) rs.getString(index);
		}
		if (beanClass == Date.class) {
			return (T) rs.getTimestamp(index);
		}
		if (beanClass == BigDecimal.class) {
			return (T) rs.getBigDecimal(index);
		}
		return (T) o;
	}

	/**
	 * 通用查询方法，可以用来查询对象集合或值集合
	 * 
	 * @param <T>
	 * @param beanClass
	 *            要封装的对象类型
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数值
	 * @return
	 */
	public <T> List<T> queryList(Class<T> beanClass, String sql, Object... params) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			prepare(psmt, params);
			if (logger.isDebugEnabled()) {
				logger.debug("SQL: [{}]", sql);
				logger.debug("SQL params: {}", Arrays.toString(params));
			}
			rs = psmt.executeQuery();
			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				if (beanClass.isPrimitive() || basicClasses.contains(beanClass)) {
					list.add(processOneCloumn(beanClass, rs, 1));
				} else {
					Map<String, PropertyDescriptor> pds = getPropertyDescriptors(beanClass);
					ResultSetMetaData rsmd = rs.getMetaData();
					T beanInstance = beanClass.newInstance();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String colName = rsmd.getColumnLabel(i);
						// logger.debug("colName:" + colName);
						PropertyDescriptor pd = pds.get(colName.toLowerCase());
						if (pd != null) {
							Object colValue = processOneCloumn(pd.getPropertyType(), rs, i);
							pd.getWriteMethod().invoke(beanInstance, colValue);
						}
					}
					list.add(beanInstance);
				}
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(rs, psmt);
		}
	}

	/**
	 * 用来将结果集映射为一个Map集合，key为查询语句中的第一列，值为剩余的列
	 * 
	 * @param keyClass
	 *            key的类型
	 * @param valueClass
	 *            value的类型
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数值
	 * @return
	 */
	public <T, K extends Serializable> Map<K, T> queryIdMap(Class<K> keyClass, Class<T> valueClass, String sql,
			Object... params) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			prepare(psmt, params);
			if (logger.isDebugEnabled()) {
				logger.debug("SQL: [{}]", sql);
				logger.debug("SQL params: {}", Arrays.toString(params));
			}
			rs = psmt.executeQuery();
			Map<K, T> map = new LinkedHashMap<K, T>();
			while (rs.next()) {
				if ((keyClass.isPrimitive() || basicClasses.contains(keyClass))) {
					K key = processOneCloumn(keyClass, rs, 1);
					T value;
					if ((valueClass.isPrimitive() || basicClasses.contains(valueClass))) {
						value = processOneCloumn(valueClass, rs, 2);
					} else {
						Map<String, PropertyDescriptor> pds = getPropertyDescriptors(valueClass);
						ResultSetMetaData rsmd = rs.getMetaData();
						value = valueClass.newInstance();
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							String colName = rsmd.getColumnLabel(i);
							// logger.debug("colName:" + colName);
							PropertyDescriptor pd = pds.get(colName.toLowerCase());
							if (pd != null) {
								Object colValue = processOneCloumn(pd.getPropertyType(), rs, i);
								pd.getWriteMethod().invoke(value, colValue);
							}
						}
					}
					map.put(key, value);
				} else {
					throw new RuntimeException("only support basic type fro key");
				}
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(rs, psmt);
		}
	}

	/**
	 * 获得某一序列的下个值
	 * 
	 * @param sequenceName
	 *            序列名
	 * @return
	 */
	public int getSequenceVal(String sequenceName) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement("select " + sequenceName + ".nextval from dual");
			if (logger.isDebugEnabled()) {
				logger.debug("SQL: [select {}.nextval from dual]", sequenceName);
			}
			rs = psmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(rs, psmt);
		}
	}

}