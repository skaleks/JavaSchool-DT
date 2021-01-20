package com.magenta.controller.user;


import com.magenta.crud.option.OptionService;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.user.UserService;
import com.magenta.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final TariffService tariffService;
    private final OptionService optionService;
    private final SecurityService securityService;


    @GetMapping
    public String userPanel(){
        if (securityService.getAuthentication().getAuthorities().stream().anyMatch(role->role.getAuthority().equals("ROLE_USER"))){
            return "user/userPanel";
        }
        return "login";
    }
}
