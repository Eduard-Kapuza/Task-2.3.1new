package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public int showAmountUsers() {
        return getListUser().size();
    }

    @Override
    public List<User> getListUser() {
        return userDao.getListUser();
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public void updateUser(User user, int id) {
        userDao.update(user, id);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public List<User> getListUserLimit(int limit) {
        if (limit >= 5 || limit == 0) {
            limit = userDao.getListUser().size();
        }
        return userDao.getListUserLimit(limit);
    }
}