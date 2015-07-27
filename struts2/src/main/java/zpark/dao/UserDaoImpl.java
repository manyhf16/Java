package zpark.dao;

import zpark.entity.User;
import zpark.ext.jdbc.JdbcUtil;

public class UserDaoImpl implements UserDao {

    public void addUser(User user) {
        String sql = "insert into STRUTS2_USERS (username, password, type, realname) values (?,?,0,?)";
        JdbcUtil.update(sql, user.getUsername(), user.getPassword(), user.getRealname());
    }

    public User findUser(String username) {
        String sql = "select username, password, type, realname from STRUTS2_USERS where username=?";
        return JdbcUtil.queryOne(User.class, sql, username);
    }

    public void updateUser(User user) {
        String sql = "update STRUTS2_USERS set password=?, realname=?, type=? where username=?";
        JdbcUtil.update(sql, user.getPassword(), user.getRealname(), user.getType(), user.getUsername());
    }

}
