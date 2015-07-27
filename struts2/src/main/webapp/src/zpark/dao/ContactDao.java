package zpark.dao;

import java.util.List;

import zpark.entity.Contact;

public interface ContactDao {

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

}
