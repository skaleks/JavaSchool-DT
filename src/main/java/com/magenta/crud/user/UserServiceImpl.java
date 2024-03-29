package com.magenta.crud.user;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.type.Role;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.dto.AddFundsDto;
import com.magenta.crud.user.dto.EditUserDto;
import com.magenta.crud.user.dto.NewUserDto;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.mapper.MyModelMapper;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import com.magenta.security.SecurityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final ContractService contractService;
    private final MyModelMapper modelMapper;
    private final SecurityService securityService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public UserServiceImpl(UserDao userDao, MyModelMapper modelMapper, ContractService contractService,
                           SecurityService securityService){
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
        user.setBalance(0.0);
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
    public void deleteById(int id) throws DatabaseException, MyException {
        if (!userDao.findById(id).getNumbers().isEmpty()){
            throw new MyException("User still have one or more contracts.");
        }
        userDao.delete(id);
    }


    @Override
    public void update(EditUserDto editDto) {
        UserDto userDto = modelMapper.map(editDto, UserDto.class);
        User user = modelMapper.map(userDto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }

    @Override
    public void setStatus(ChangeStatusDto statusDto) throws AuthorizationException, DatabaseException {

        User user = userDao.findById(statusDto.getEntityId());
        
        if (!securityService.isAdmin() && user.getStatus().equals(Status.BLOCKED)){
            throw new AuthorizationException("Only administrator can do it");
        }

        Status newStatus = modelMapper.map(statusDto.getStatus(),Status.class);
        user.setStatus(newStatus);
    }

    @Override
    public void addFunds(AddFundsDto funds) throws DatabaseException, MyException {
        User user = userDao.findById(funds.getUserId());

        if (!user.getStatus().equals(Status.ACTIVE)){
            throw new MyException("User isn't active!");
        }
        double old = user.getBalance();
        double add = funds.getFunds();
        user.setBalance(old + add);
    }
}
