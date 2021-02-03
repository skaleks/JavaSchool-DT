package com.magenta.services;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.user.User;
import com.magenta.crud.user.UserDao;
import com.magenta.crud.user.UserServiceImpl;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.mapper.MyModelMapper;
import com.magenta.security.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private List<User> users = new ArrayList<>();
    private List<UserDto> usersDto = new ArrayList<>();

    @Mock
    UserDao userDao;
    @Mock
    ContractService contractService;
    @Mock
    MyModelMapper modelMapper;
    @Mock
    SecurityService securityService;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser() {

    }

    @Test
    void findById() {

    }

    @Test
    void findAllUsers() {

    }

    @Test
    void findByNumberOrEmail() {
    }

    @Test
    void findByLogin() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void setStatus() {
    }

    @Test
    void addFunds() {
    }
}