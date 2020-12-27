package com.magenta.service;

import com.magenta.model.User;
import com.magenta.myexception.MyException;

import java.util.List;


public interface UserService {

    void save(User user);

    User findById(int id) throws MyException;

    List<User> findAllUsers();

//    Client findByName(String name);

    void deleteById(int id);

    void update(User user);
}
