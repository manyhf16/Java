package zpark.dao;

import zpark.entity.User;

public interface UserDao {

    /**
     * 查询用户
     * 
     * @param username
     * @return
     */
    public User findUser(String username);

    /**
     * 增加用户
     * 
     * @param user
     */
    public void addUser(User user);

    /**
     * 修改用户
     * 
     * @param user
     */
    public void updateUser(User user);

}
