package com.magenta.controller.admin;


import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.EditUserDto;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/edit")
public class AdminEditController {

    private static final Logger LOGGER = Logger.getLogger("Edit");

    UserService userService;
    TariffService tariffService;
    OptionService optionService;
    ContractService contractService;
    ModelMapper modelMapper;

    @Autowired
    public AdminEditController(UserService userService, TariffService tariffService, OptionService optionService, ContractService contractService, ModelMapper modelMapper) {
        this.userService = userService;
        this.tariffService = tariffService;
        this.optionService = optionService;
        this.contractService = contractService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/deleteUser{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/deleteTariff{id}")
    public String deleteTariff(@PathVariable("id") int id) throws DatabaseException {
        tariffService.deleteExistTariff(id);
        return "redirect:/admin/allTariffs";
    }

    @GetMapping("/deleteOption{id}")
    public String deleteOption(@PathVariable("id") int id) throws DatabaseException {
        optionService.deleteExistOption(id);
        return "redirect:/admin/allOptions";
    }

    @GetMapping("/deleteContract{id}")
    public String deleteContract(@PathVariable("id") int id) throws DatabaseException {
        contractService.deleteContract(id);
        return "redirect:/admin/allContracts";
    }
    @GetMapping("/updateUser{id}")
    public String editUser(@PathVariable("id") int id, @ModelAttribute("editUserDto") EditUserDto editUserDto, Model model) throws DatabaseException {
        model.addAttribute("userDto", userService.findById(id));
        return "admin/updateUser";
    }

    @PostMapping("/{id}")
    public String updateUser(@Valid @ModelAttribute("editUserDto") EditUserDto editUserDto, BindingResult result,
                             @PathVariable int id, Model model) throws DatabaseException {

        if (result.hasErrors()) {
            return "admin/updateUser";
        }
        UserDto userDto = modelMapper.map(editUserDto,UserDto.class);
//        Изменить на EditUserDto?
        userService.update(userDto);
        model.addAttribute("user",userService.findById(id));
        return "admin/userInfo";
    }

    @GetMapping("/{id}/addContract")
    public String addContractToExistUser(@PathVariable("id") int id, Model model) throws DatabaseException {

        model.addAttribute("user",userService.findById(id));
        model.addAttribute("contract", new NewContractDto());
        model.addAttribute("tariffs", tariffService.findAllTariff());
        return "admin/addContract";
    }

    @PostMapping("/user-status")
    public String changeUserStatus(@ModelAttribute("status") ChangeStatusDto changeStatusDto, Model model) throws DatabaseException, AuthorizationException {

        userService.setStatus(changeStatusDto);
        UserDto dto = userService.findById(changeStatusDto.getEntityId());
        model.addAttribute("user", dto);
        return "admin/userInfo";
    }
}
