package com.magenta.controller.user;


import com.magenta.crud.option.OptionService;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;
    TariffService tariffService;
    OptionService optionService;

    @Autowired
    public UserController(UserService userService, TariffService tariffService, OptionService optionService) {
        this.userService = userService;
        this.tariffService = tariffService;
        this.optionService = optionService;
    }

    @GetMapping
    public String userPanel(){
        return "user/userPanel";
    }
}
