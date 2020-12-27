package com.magenta.service;

import com.magenta.dao.UserDao;
import com.magenta.model.User;
import com.magenta.myexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findById(int id) throws MyException {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }


    @Override
    public void deleteById(int id) {
        userDao.delete(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

}
