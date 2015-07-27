package zpark.dao;

import java.util.ArrayList;
import java.util.List;

import zpark.entity.Contact;
import zpark.ext.jdbc.JdbcUtil;

public class ContactDaoImpl implements ContactDao {

    public void addContact(Contact contact) {
        String sql = "insert into STRUTS2_CONTACT(id, name, qq, phone, email, cityid, photo,username) values(STRUTS2_CONTACT_SEQ.nextval,?,?,?,?,?,?,?)";
        JdbcUtil.update(sql, contact.getName(), contact.getQq(), contact.getPhone(), contact.getEmail(),
                contact.getCityId(), contact.getPhoto(), contact.getUsername());
    }

    public Contact findContact(int id) {
        String sql = "select id, name, qq, phone, email, cityid, photo, username from STRUTS2_CONTACT where id=?";
        return JdbcUtil.queryOne(Contact.class, sql, id);
    }

    public void updateContact(Contact contact) {
        String sql = "update STRUTS2_CONTACT set name=?, qq=?, phone=?, email=?, cityid=?, photo=? where id=?";
        JdbcUtil.update(sql, contact.getName(), contact.getQq(), contact.getPhone(), contact.getEmail(),
                contact.getCityId(), contact.getPhoto(), contact.getId());
    }

    public void deleteContact(int id) {
        String sql = "delete from STRUTS2_CONTACT where id = ?";
        JdbcUtil.update(sql, id);
    }

    // 查询个数也要考虑条件
    public int findCount(String name, String qq, Integer cityId, String username) {
        String sql = "select count(*) from STRUTS2_CONTACT where 1=1";
        List<Object> list = new ArrayList<Object>();
        if (name != null && !name.equals("")) { // 传递了name 参数
            sql += " and name like ?";
            list.add("%" + name + "%");
        }
        if (qq != null && !qq.equals("")) { // 传递了qq 参数
            sql += " and qq like ?";
            list.add("%" + qq + "%");
        }
        if (cityId != null && cityId != -1) { // 传递了cityId 参数, -1代表未选择
            sql += " and cityId = ?";
            list.add(cityId);
        }
        if (username != null && !username.equals("")) { // 传递了username 参数
            sql += " and username = ?";
            list.add(username);
        }
        return JdbcUtil.queryOne(Integer.class, sql, list.toArray());
    }

    // 根据多个条件查询通讯录, 带分页
    public List<Contact> findContacts(String name, String qq, Integer cityId, String username, int pageNo, int pageSize) {
        String sql = "select * from STRUTS2_CONTACT where 1=1";
        List<Object> list = new ArrayList<Object>();
        if (name != null && !name.equals("")) { // 传递了name 参数
            sql += " and name like ?";
            list.add("%" + name + "%");
        }
        if (qq != null && !qq.equals("")) { // 传递了qq 参数
            sql += " and qq like ?";
            list.add("%" + qq + "%");
        }
        if (cityId != null && cityId != -1) { // 传递了cityId 参数, -1代表未选择
            sql += " and cityId = ?";
            list.add(cityId);
        }
        if (username != null && !username.equals("")) { // 传递了username 参数
            sql += " and username = ?";
            list.add(username);
        }
        return JdbcUtil.queryList(Contact.class, pageNo, pageSize, sql, list.toArray());
    }

}
