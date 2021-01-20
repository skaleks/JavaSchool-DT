package com.magenta.crud.user;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.type.Role;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.dto.NewUserDto;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.myexception.MyException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ContractService contractService;
    private final ModelMapper modelMapper;


    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper, ContractService contractService){
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.contractService = contractService;
    }

    @Override
    public void save(NewUserDto newUserDto) {
        User user = modelMapper.map(newUserDto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        userDao.save(user);
    }

    @Override
    public UserDto findById(int id) throws MyException {
        return modelMapper.map(userDao.findById(id),UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserDto> resultList = new ArrayList<>();
        userDao.findAllUsers().forEach(user->resultList.add(modelMapper.map(user,UserDto.class)));
        return resultList;
    }

    @Override
    public UserDto findByNumber(String number) throws MyException {
        ContractDto contractDto = contractService.findByNumber(number);
        return modelMapper.map(contractDto.getUser(),UserDto.class);
    }

    @Override
    public User findByLogin(String login) throws MyException {
        return userDao.findByLogin(login);
    }


    @Override
    public void deleteById(int id) {
        userDao.delete(id);
    }


    @Override
    public void update(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }
}
