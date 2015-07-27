package zpark.dao;

import zpark.entity.User;
import zpark.ext.jdbc.MicroContrainer;

public class UserDaoImpl implements UserDao {

    public void addUser(User user) {
        String sql = "insert into users (username, password, type, realname) values (?,?,0,?)";
        MicroContrainer.update(sql, user.getUsername(), user.getPassword(), user.getRealname());
    }

    public User findUser(String username) {
        String sql = "select username, password, type, realname from users where username=?";
        return MicroContrainer.queryOne(User.class, sql, username);
    }

    public void updateUser(User user) {
        String sql = "update users set password=?, realname=?, type=? where username=?";
        MicroContrainer.update(sql, user.getPassword(), user.getRealname(), user.getType(), user.getUsername());
    }

}
