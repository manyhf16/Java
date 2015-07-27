package zpark.service;

import java.util.List;
import java.util.Map;

import zpark.dao.CityDao;
import zpark.dao.CityDaoImpl;
import zpark.dao.ContactDao;
import zpark.dao.ContactDaoImpl;
import zpark.dao.UserDao;
import zpark.dao.UserDaoImpl;
import zpark.entity.City;
import zpark.entity.Contact;
import zpark.entity.User;
import zpark.ext.annotations.tx.Transactional;
import zpark.util.PinyinUtil;

public class UserServiceImpl implements UserService {

    public String findPinyin(String name) {
        return PinyinUtil.toPinyin(name);
    }

    private ContactDao contactDao = new ContactDaoImpl();

    private CityDao cityDao = new CityDaoImpl();

    private UserDao userDao = new UserDaoImpl();

    @Transactional
    public void addContact(Contact contact) {
        contactDao.addContact(contact);
    }

    @Transactional
    public void updateContact(Contact contact) {
        contactDao.updateContact(contact);
    }

    public Contact findContact(int id) {
        return contactDao.findContact(id);
    }

    @Transactional
    public void deleteContact(int id) {
        contactDao.deleteContact(id);
    }

    public int findCount(String name, String qq, Integer cityId, String username) {
        return contactDao.findCount(name, qq, cityId, username);
    }

    public List<Contact> findContacts(String name, String qq, Integer cityId, String username, int pageNo, int pageSize) {
        return contactDao.findContacts(name, qq, cityId, username, pageNo, pageSize);
    }

    public List<City> findCities() {
        return cityDao.findCityList();
    }

    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User findUser(String username) {
        return userDao.findUser(username);
    }

    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public Map<Long, String> findCityMap() {
        return cityDao.findCityMap();
    }

}
