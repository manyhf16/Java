package zpark.service;

import java.util.List;
import java.util.Map;

import zpark.entity.City;
import zpark.entity.Contact;
import zpark.entity.User;

public interface UserService {

    /**
     * 转换拼音 输入张三，返回 zhangsan；输入李四，返回lisi
     * 
     * @param name
     * @return
     */
    public String findPinyin(String name);

    /**
     * 添加通讯录
     * 
     * @param contact
     */
    public void addContact(Contact contact);

    /**
     * 更新通讯录
     * 
     * @param contact
     */
    public void updateContact(Contact contact);

    /**
     * 查询单个通讯录对象 (配合更新使用)
     * 
     * @param id
     * @return
     */
    public Contact findContact(int id);

    /**
     * 删除通讯录
     * 
     * @param id
     */
    public void deleteContact(int id);

    /**
     * 组合条件查询个数
     * 
     * @param name
     * @param qq
     * @return
     */
    public int findCount(String name, String qq, Integer cityId, String username);

    /**
     * 组合条件查询，带分页
     * 
     * @param name
     * @param qq
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<Contact> findContacts(String name, String qq, Integer cityId, String username, int pageNo, int pageSize);

    /**
     * 查询所有城市
     * 
     * @return
     */
    public List<City> findCities();

    /**
     * 查询所有城市
     * 
     * @return
     */
    public Map<Long, String> findCityMap();

    /**
     * 查询用户
     * 
     * @param username
     * @return 如果用户不存在，返回null
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
