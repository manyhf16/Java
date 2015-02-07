package zpark.ext.jdbc;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import zpark.ext.annotations.tx.Propagation;
import zpark.ext.annotations.tx.Transactional;

/**
 * 提高版的工具类
 * 
 * @author yihang
 * 
 */
public abstract class MicroContrainer {

    private static ThreadLocal<Deque<TxInfo>> txs = new ThreadLocal<Deque<TxInfo>>();

    private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();

    private static final Properties p = new Properties();

    protected static List<Class<?>> basicClasses = new ArrayList<Class<?>>();

    public static boolean isDebug = false; // 是否调试，调试为true，可以多输出一些调试信息

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Object target, Class<T> interfaces) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { interfaces },
                new TransactionAdvice(target));
    }

    public static class TransactionAdvice implements InvocationHandler {
        private Object target;

        public TransactionAdvice(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Transactional transactional = method.getAnnotation(Transactional.class);
            Propagation propagation = transactional.propagation();
            Deque<TxInfo> stack = txs.get();

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
            Object result = null;
            try {
                System.out.println("before ... method:" + method.getName() + " stack:" + stack);
                result = method.invoke(target, args);
                if (stack != null) {
                    TxInfo current = stack.pop();
                    if (current.isNew()) {
                        System.out.println("connection:" + current.getConnection() + " commit");
                        current.getConnection().commit();
                        current.getConnection().close();
                    }
                }
            } catch (InvocationTargetException e) {
                TxInfo current = stack.pop();
                if (current.isNew() && current.isRollbackOnly()) {
                    System.out.println("connection:" + current.getConnection() + " rollback");
                    current.getConnection().rollback();
                    current.getConnection().close();
                } else {
                    TxInfo prev = stack.peek();
                    if (prev != null) {
                        prev.setRollbackOnly(true);
                    }
                }
                throw e.getCause();
            } finally {
                if (stack.isEmpty()) {
                    txs.set(null);
                }
                System.out.println("after ... method:" + method.getName() + " stack:" + stack);
            }
            return result;
        }

    }

    static {
        InputStream is = null;
        try {
            // 从配置文件读取配置，存入Properties 变量p
            is = MicroContrainer.class.getResourceAsStream("/db_oracle.properties");
            p.load(is);
        } catch (Throwable e) {
            // 应用程序初始化错误
            throw new ExceptionInInitializerError(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

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
    }

    /**
     * 获得数据库连接对象并绑定至当前线程，如果当前线程已有绑定的数据库连接则直接返回该连接对象
     * 
     * @return
     */
    public static Connection newConnection() {
        try {
            String url = p.getProperty("jdbc.url");
            String driver = p.getProperty("jdbc.driver");
            String username = p.getProperty("jdbc.username");
            String password = p.getProperty("jdbc.password");
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            throw new RuntimeException("error when getConnection", e);
        }
    }

    /**
     * 释放资源，包括ResultSet与Statement
     * 
     * @param rs
     * @param stmt
     */
    public static void release(Object... resources) {
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

    public void close() {
        Connection conn = conns.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conns.set(null);
        }
    }

    private static Connection getConnection() {
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
     * 通用update,delete,insert方法
     * 
     * @param sql
     * @param params
     * @return 影响行数
     */
    public static int update(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            prepare(psmt, params);
            if (isDebug) {
                System.out.println("SQL: [" + sql + "]");
                System.out.println("SQL params: " + Arrays.toString(params));
            }
            return psmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(null, psmt);
        }
    }

    /**
     * 通用insert方法(需要获得主键值时使用)
     * 
     * @param sql
     * @param pkColumnName
     *            主键列的名字
     * @param params
     * @return
     */
    public static int insert(String sql, String pkColumnName, Object... params) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql, new String[] { pkColumnName });
            prepare(psmt, params);
            if (isDebug) {
                System.out.println("SQL: [" + sql + "]");
                System.out.println("SQL params: " + Arrays.toString(params));
            }
            psmt.executeUpdate();
            rs = psmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            release(null, psmt);
        }
    }

    /**
     * 给preparedStatement中所有?占位符赋值
     * 
     * @param psmt
     * @param params
     * @throws SQLException
     */
    private static void prepare(PreparedStatement psmt, Object... params) throws SQLException {
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

    private static Map<Class<?>, Map<String, PropertyDescriptor>> all = new ConcurrentHashMap<Class<?>, Map<String, PropertyDescriptor>>();

    /**
     * 得到某一实体列中属性反射信息的map集合：key是属性名(全小写), value是该属性的反射信息 <br>
     * 为了避免每次都获取这些信息(比较耗时)，用了一个all集合来缓存上次的结果
     * 
     * @param beanClass
     * @return
     * @throws IntrospectionException
     */
    private static Map<String, PropertyDescriptor> getPropertyDescriptors(Class<?> beanClass)
            throws IntrospectionException {
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
    public static <T> T queryOne(Class<T> beanClass, String sql, Object... params) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            prepare(psmt, params);
            if (isDebug) {
                System.out.println("SQL: [" + sql + "]");
                System.out.println("SQL params: " + Arrays.toString(params));
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
                        String colName = rsmd.getColumnName(i);
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
    private static <T> T processOneCloumn(Class<T> beanClass, ResultSet rs, int index) throws SQLException {
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
    public static <T> List<T> queryList(Class<T> beanClass, String sql, Object... params) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            prepare(psmt, params);
            if (isDebug) {
                System.out.println("SQL: [" + sql + "]");
                System.out.println("SQL params: " + Arrays.toString(params));
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
                        String colName = rsmd.getColumnName(i);
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
     * 通用查询方法，用来执行分页查询(Oracle)
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
    public static <T> List<T> queryList(Class<T> beanClass, int pageNum, int pageSize, String sql, Object... params) {
        String subsql = "select * from (select rownum r, a.* from (" + sql + ") a where rownum <=?) where r > ?";
        Object[] p = null;
        if (params == null || params.length == 0) {
            p = new Object[2];
            p[0] = pageNum * pageSize;
            p[1] = (pageNum - 1) * pageSize;
        } else {
            p = new Object[params.length + 2];
            for (int i = 0; i < params.length; i++) {
                p[i] = params[i];
            }
            p[params.length] = pageNum * pageSize;
            p[params.length + 1] = (pageNum - 1) * pageSize;
        }
        return queryList(beanClass, subsql, (Object[]) p);
    }

    /**
     * 获得某一序列的下个值
     * 
     * @param sequenceName
     *            序列名
     * @return
     */
    public static int getSequenceVal(String sequenceName) {
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement("select " + sequenceName + ".nextval from dual");
            if (isDebug) {
                System.out.println("SQL: [select " + sequenceName + ".nextval from dual]");
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

    /**
     * 辅助类，用于跟踪事务状态
     * 
     * @author yihang
     * 
     */
    public static enum TxStatus {
        NEW, PARTICIPATING, COMPLETED;
    }

    public static class TxInfo {
        private TxStatus status;
        private boolean rollbackOnly;

        public void setRollbackOnly(boolean rollbackOnly) {
            this.rollbackOnly = rollbackOnly;
        }

        private Connection connection;

        public TxInfo(TxStatus status, TxInfo prev) {
            this.status = status;
            if (isNew()) {
                this.connection = newConnection();
                try {
                    this.connection.setAutoCommit(false);
                } catch (SQLException e) {
                    throw new RuntimeException("error when begin transaction", e);
                }
            } else {
                this.connection = prev.getConnection();
            }
        }

        public TxStatus getStatus() {
            return status;
        }

        public Connection getConnection() {
            return connection;
        }

        public boolean isNew() {
            return this.status == TxStatus.NEW;
        }

        public boolean isParticipating() {
            return this.status == TxStatus.PARTICIPATING;
        }

        public boolean isRollbackOnly() {
            return this.rollbackOnly;
        }

        public boolean isCompleted() {
            return this.status == TxStatus.COMPLETED;
        }

        @Override
        public String toString() {
            return "TxInfo [status=" + status + ", rollbackOnly=" + rollbackOnly + ", connection=" + connection + "]";
        }

    }

}
