package com.magenta.controller.admin;

import com.magenta.crud.global.DataService;
import com.magenta.crud.global.dto.AdminMainDto;
import com.magenta.crud.user.dto.UserProfileDto;
import com.magenta.myexception.DatabaseException;
import com.magenta.security.SecurityService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminHomeController {

    private static final Logger LOGGER = LogManager.getLogger(AdminHomeController.class);
    private final DataService dataService;
    private final SecurityService securityService;


    @GetMapping()
    public ModelAndView adminPanel(){
        AdminMainDto adminMainDto = dataService.getMainPageForAdmin(securityService.getPrincipal());
        return new ModelAndView("admin/adminPanel","panel", adminMainDto);
    }

    @GetMapping("/profile")
    public ModelAndView adminLikeUserProfile() throws DatabaseException {
        UserProfileDto userProfile = dataService.getUserProfileByLogin(securityService.getPrincipal());
        return new ModelAndView("admin/profile","profile", userProfile);
    }

    @GetMapping("/search")
    public String searchByNumberOrEmail(@RequestParam("search") String request, Model model) throws DatabaseException {
        model.addAttribute("profile", dataService.getUserProfileByNumberOrEmail(request));
        return "admin/userInfo";
    }

}
