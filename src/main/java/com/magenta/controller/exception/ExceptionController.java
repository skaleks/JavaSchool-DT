package com.magenta.controller.exception;

import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import com.magenta.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = Logger.getLogger("Exception");

    private final SecurityService securityService;

    @Autowired
    public ExceptionController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView exception(Exception exception){
        return choosePath(exception.getMessage());
    }


    @ExceptionHandler(value = MyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView myException(MyException myException){
        return choosePath(myException.getMessage());
    }

    @ExceptionHandler(value = DatabaseException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView databaseException(DatabaseException databaseException){
        return choosePath(databaseException.getMessage());
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ModelAndView myException(AuthorizationException authorizationException){
        return choosePath(authorizationException.getMessage());
    }


    private ModelAndView choosePath(String error){

        LOGGER.info(error);
        String viewName;
        ModelAndView modelAndView = new ModelAndView();

        if (securityService.getAuthorities().stream().anyMatch(role->role.getAuthority().equals("ROLE_ADMIN"))){
            viewName = "admin/error";
        }else{
            viewName = "user/error";
        }

        modelAndView.setViewName(viewName);
        modelAndView.addObject("exception", error);
        return modelAndView;
    }
}
