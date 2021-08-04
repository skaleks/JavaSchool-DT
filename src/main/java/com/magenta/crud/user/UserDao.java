package com.magenta.crud.user;

import com.magenta.myexception.DatabaseException;

import java.util.List;


public interface UserDao {

    void save(User user);

    User findById(int id) throws DatabaseException;

    List<User> findAllUsers();

    User findByLogin(String login) throws DatabaseException;

    void delete(int id);

    void update(User user);

    User findByEmail(String email) throws DatabaseException;
}
