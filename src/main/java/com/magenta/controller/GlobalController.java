package com.magenta.controller;

import com.magenta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class GlobalController {

    UserService userService;

    @Autowired
    public GlobalController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(){
        return "index";
    }
}
