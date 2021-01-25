package com.magenta.controller.global;

import com.magenta.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping
@AllArgsConstructor
public class GlobalController {

    private static final Logger LOGGER = Logger.getLogger("GlobalController");

    private final SecurityService securityService;

    @GetMapping("/")
    public String mainPage(){
        Authentication auth = securityService.getAuthentication();
        if (auth == null || securityService.isAnonymous()){
            return "/index";
        }
        return securityService.isAdmin()? "redirect:/admin" : "redirect:/user";
//        return "index";
    }

//    @GetMapping("/login")
//    public String login(){
//        Authentication auth = securityService.getAuthentication();
//        if (auth == null || securityService.isAnonymous()){
//            return "login";
//        }
//        return "redirect:/";
//    }

    @GetMapping("/logout")
    public String logout(){
        Authentication auth = securityService.getAuthentication();
        if (auth != null){
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/?logout";
    }

    @GetMapping("/403")
    public String response403(){
        return "403";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(){
        return "forgotPassword";
    }
}
