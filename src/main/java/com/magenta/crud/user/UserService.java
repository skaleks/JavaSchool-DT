package com.magenta.crud.user;

import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.user.dto.NewUserDto;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;

import java.util.List;


public interface UserService {

    void save(NewUserDto newUserDto);

    List<UserDto> findAllUsers();

    UserDto findById(int id) throws DatabaseException;

    UserDto findByNumberOrEmail(String request) throws DatabaseException;

    UserDto findByLogin(String login) throws DatabaseException;

    void deleteById(int id);

    void update(UserDto userDto);

    void setStatus(ChangeStatusDto statusDto) throws DatabaseException, AuthorizationException;
}
