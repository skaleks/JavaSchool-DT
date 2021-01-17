package com.magenta.controller.admin;


import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.myexception.MyException;
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
    public String deleteTariff(@PathVariable("id") int id) throws MyException {
        tariffService.deleteExistTariff(tariffService.findTariffById(id));
        return "redirect:/admin/allTariffs";
    }

    @GetMapping("/deleteOption{id}")
    public String deleteOption(@PathVariable("id") int id) throws MyException {
        optionService.deleteExistOption(optionService.findOptionById(id));
        return "redirect:/admin/allOptions";
    }

    @GetMapping("/deleteContract{id}")
    public String deleteContract(@PathVariable("id") int id) throws Exception {
        contractService.deleteContract(contractService.findById(id));
        return "redirect:/admin/allContracts";
    }
    @GetMapping("/updateUser{id}")
    public String editUser(@PathVariable("id") int id, Model model) throws Exception {
        model.addAttribute("user", userService.findById(id));
        return "admin/updateUser";
    }

    @PostMapping("/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result,
                             @PathVariable int id, Model model) throws MyException {

        if (result.hasErrors()) {
            return "admin/updateUser";
        }
        userService.update(userDto);
        model.addAttribute("user",userService.findById(id));
        return "admin/userInfo";
    }

    @GetMapping("/{id}/addContract")
    public String addContractToExistUser(@PathVariable("id") int id, Model model) throws MyException {
        LOGGER.severe("User id:" + id);
        model.addAttribute("user",userService.findById(id));
        model.addAttribute("contract", new NewContractDto());
        model.addAttribute("tariffs", tariffService.findAllTariff());
        return "admin/addContract";
    }
}
