package com.magenta.crud.user;

import com.magenta.crud.user.dto.NewUserDto;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.myexception.MyException;

import java.util.List;


public interface UserService {

    void save(NewUserDto newUserDto);

    UserDto findById(int id) throws MyException;

    List<UserDto> findAllUsers();

    UserDto findByNumber(String number) throws MyException;

    void deleteById(int id);

    void update(UserDto userDto);

}
