package com.magenta.controller.admin;


import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.global.DataService;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.*;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/user")
@AllArgsConstructor
public class AdminUserHandler {

    private static final Logger LOGGER = LogManager.getLogger(AdminUserHandler.class);
    private final UserService userService;
    private final TariffService tariffService;
    private final ModelMapper modelMapper;
    private final DataService dataService;

    @GetMapping("/{id}")
    public String info(@PathVariable("id") int id, Model model) throws DatabaseException {
        model.addAttribute("profile", dataService.getUserProfileById(id));
        return "admin/userInfo";
    }

    @GetMapping("/list")
    public String all(Model model){
        AllUsersDto allUsersDto = new AllUsersDto();
        allUsersDto.setListOfUsers(userService.findAllUsers());
        model.addAttribute("users", allUsersDto);
        return "admin/allUsers";
    }

    @GetMapping("/new")
    public String newForm(@ModelAttribute("user") NewUserDto newUserDto){
        return "admin/addUser";
    }

    @PostMapping("/new")
    public String add(@Valid @ModelAttribute("user") NewUserDto newUserDto, BindingResult result, Model model) throws DatabaseException {
        if (result.hasErrors()){
            return "admin/addUser";
        }
        userService.save(newUserDto);

        String login = newUserDto.getLogin();
        model.addAttribute("profile", dataService.getUserProfileByLogin(login));
        return "admin/userInfo";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws DatabaseException, MyException {
        userService.deleteById(id);
        return "redirect:/admin/user/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") int id, @ModelAttribute("editUserDto") EditUserDto editUserDto, Model model)
            throws DatabaseException {
        model.addAttribute("userDto", userService.findById(id));
        return "admin/updateUser";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid @ModelAttribute("editUserDto") EditUserDto editUserDto, BindingResult result,
                             @PathVariable int id, Model model) throws DatabaseException {

        if (result.hasErrors()) {
            return "admin/updateUser";
        }
        LOGGER.warn("Are you here?  1 ");
        userService.update(editUserDto);
//        model.addAttribute("profile",dataService.getUserProfileById(id));
//        return "admin/userInfo";
        LOGGER.warn("Are you here?  6 ");
        return "redirect:/admin/user/" + id;
    }

    @GetMapping("/{id}/addContract")
    public String addContract(@PathVariable("id") int id, Model model) throws DatabaseException {

        model.addAttribute("user",userService.findById(id));
        model.addAttribute("contract", new NewContractDto());
        model.addAttribute("tariffs", tariffService.findAllTariff());
        return "admin/addContract";
    }

    @PostMapping("/status")
    public ModelAndView changeStatus(@ModelAttribute("status") ChangeStatusDto changeStatusDto) throws DatabaseException, AuthorizationException {

        userService.setStatus(changeStatusDto);
        UserProfileDto profile = dataService.getUserProfileById(changeStatusDto.getEntityId());
        return new ModelAndView("admin/userInfo","profile", profile);
    }

    @GetMapping("/{id}/balance")
    public ModelAndView addFundsForm(@PathVariable("id") int id) throws DatabaseException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/addFundsPage");
        mav.addObject("funds", new AddFundsDto());
        mav.addObject("user", userService.findById(id));
        return mav;
    }

    @PostMapping("/balance")
    public ModelAndView addFunds(@Valid @ModelAttribute("funds") AddFundsDto fundsDto, BindingResult result) throws DatabaseException, MyException {

        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()){
            mav.setViewName("admin/addFundsPage");
            mav.addObject("funds", new AddFundsDto());
            mav.addObject("user", userService.findById(fundsDto.getUserId()));
            return mav;
        }

        userService.addFunds(fundsDto);
        mav.setViewName("admin/userInfo");
        mav.addObject("success",true);
        mav.addObject("profile", dataService.getUserProfileById(fundsDto.getUserId()));
        return mav;
    }
}
