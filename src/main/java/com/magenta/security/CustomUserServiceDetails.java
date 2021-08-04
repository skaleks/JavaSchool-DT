package com.magenta.security;

import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.myexception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service("userDetailsService")
public class CustomUserServiceDetails implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger("UserServiceDetails");

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDto userDto;
        try {
            userDto = userService.findByLogin(s);
        } catch (DatabaseException dbe) {
            LOGGER.info(dbe.getMessage());
            throw new UsernameNotFoundException("Username not found");
        }
        return new CustomUserPrincipal(userDto);
    }
}
