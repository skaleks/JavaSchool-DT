package com.magenta.security;

import com.magenta.crud.user.User;
import com.magenta.crud.user.UserService;
import com.magenta.myexception.MyException;
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

        User user;
        try {
            user = userService.findByLogin(s);
        } catch (MyException mye) {
            LOGGER.info(mye.getMessage());
            throw new UsernameNotFoundException("Username not found");
        }
        return new CustomUserPrincipal(user);
    }
}
