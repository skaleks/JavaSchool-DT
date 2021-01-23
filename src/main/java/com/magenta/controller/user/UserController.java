package com.magenta.controller.user;


import com.magenta.crud.global.DataService;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.UserProfileDto;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final DataService dataService;
    private final UserService userService;
    private final SecurityService securityService;

    @GetMapping
    public String userPage(Model model) throws DatabaseException {
        String login = securityService.getPrincipal();
        model.addAttribute("user", dataService.getMainPageForUser(login));
        return "user/userPanel";
    }

    @GetMapping("/profile")
    public ModelAndView userProfile() throws DatabaseException {
        UserProfileDto userProfile = dataService.getUserProfile(securityService.getPrincipal());
        return new ModelAndView("user/profile","profile", userProfile);
    }

    @PostMapping("/block")
    public ModelAndView blockAccount(@ModelAttribute("status") ChangeStatusDto statusDto) throws DatabaseException, AuthorizationException {
        userService.setStatus(statusDto);
        UserProfileDto userProfile = dataService.getUserProfile(securityService.getPrincipal());
        return new ModelAndView("user/profile","profile", userProfile);
    }
}
