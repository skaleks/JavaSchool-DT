package com.magenta.crud.user;

import com.magenta.myexception.MyException;

import java.util.List;


public interface UserDao {

    void save(User user);

    User findById(int id) throws MyException;

    List<User> findAllUsers();

    User findByLogin(String login) throws MyException;

    void delete(int id);

    void update(User user);
}
