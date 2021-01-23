package com.magenta.crud.user;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.type.Role;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.dto.NewUserDto;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.security.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger("UserService");

    private final UserDao userDao;
    private final ContractService contractService;
    private final ModelMapper modelMapper;
    private final SecurityService securityService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper, ContractService contractService, SecurityService securityService){
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.contractService = contractService;
        this.securityService = securityService;
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
    public UserDto findById(int id) throws DatabaseException {
        return modelMapper.map(userDao.findById(id),UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserDto> resultList = new ArrayList<>();
        userDao.findAllUsers().forEach(user->resultList.add(modelMapper.map(user,UserDto.class)));
        return resultList;
    }

    @Override
    public UserDto findByNumberOrEmail(String request) throws DatabaseException {

        if(request.contains("@")){
            User user = userDao.findByEmail(request);
            return modelMapper.map(user, UserDto.class);
        }
        ContractDto contractDto = contractService.findByNumber(request);
        return modelMapper.map(contractDto.getUser(),UserDto.class);
    }

    @Override
    public UserDto findByLogin(String login) throws DatabaseException {
        return modelMapper.map(userDao.findByLogin(login), UserDto.class);
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

    @Override
    public void setStatus(ChangeStatusDto statusDto) throws AuthorizationException, DatabaseException {

        User user = userDao.findById(statusDto.getEntityId());
        
        /// TODO: 22.01.2021 Find algorithm to user will be able to block contract
        if (!securityService.isAdmin() && user.getStatus().equals(Status.BLOCKED)){
            throw new AuthorizationException("Only administrator can do it");
        }

        Status newStatus = modelMapper.map(statusDto.getStatus(),Status.class);
        user.setStatus(newStatus);
    }
}
