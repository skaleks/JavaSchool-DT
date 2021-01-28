package com.magenta.controller.admin;


import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractPageDto;
import com.magenta.crud.contract.dto.EditContractDto;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.global.DataService;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.option.dto.AddRelativeToOption;
import com.magenta.crud.option.dto.DelRelativeFromOption;
import com.magenta.crud.option.dto.EditTariffOptionList;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.SwitchTariffDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.AddFundsDto;
import com.magenta.crud.user.dto.EditUserDto;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.crud.user.dto.UserProfileDto;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/edit")
@AllArgsConstructor
public class AdminEditController {

    private static final Logger LOGGER = Logger.getLogger("Edit");

    private final UserService userService;
    private final TariffService tariffService;
    private final OptionService optionService;
    private final ContractService contractService;
    private final ModelMapper modelMapper;
    private final DataService dataService;

    @GetMapping("/deleteUser{id}")
    public String deleteUser(@PathVariable("id") int id) throws DatabaseException, MyException {
        userService.deleteById(id);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/deleteTariff{id}")
    public String deleteTariff(@PathVariable("id") int id) throws DatabaseException, MyException {
        tariffService.deleteExistTariff(id);
        return "redirect:/admin/allTariffs";
    }

    @GetMapping("/deleteOption{id}")
    public String deleteOption(@PathVariable("id") int id) throws DatabaseException, MyException {
        optionService.deleteExistOption(id);
        return "redirect:/admin/allOptions";
    }

    @GetMapping("/deleteContract{id}")
    public String deleteContract(@PathVariable("id") int id) throws DatabaseException {
        contractService.deleteContract(id);
        return "redirect:/admin/allContracts";
    }

    @GetMapping("/updateUser{id}")
    public String editUser(@PathVariable("id") int id, @ModelAttribute("editUserDto") EditUserDto editUserDto, Model model)
            throws DatabaseException {
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
        model.addAttribute("profile",dataService.getUserProfileById(id));
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
    public ModelAndView changeUserStatus(@ModelAttribute("status") ChangeStatusDto changeStatusDto) throws DatabaseException, AuthorizationException {

        userService.setStatus(changeStatusDto);
        UserProfileDto profile = dataService.getUserProfileById(changeStatusDto.getEntityId());
        return new ModelAndView("admin/userInfo","profile", profile);
    }

    @PostMapping("/contract-status")
    public ModelAndView changeContractStatus(@ModelAttribute("status") ChangeStatusDto changeStatusDto) throws DatabaseException, AuthorizationException {

        contractService.setStatus(changeStatusDto);
        ContractPageDto contractPage = dataService.getContractPage(changeStatusDto.getEntityId());
        return new ModelAndView("admin/contractInfo","profile", contractPage);
    }

//    @PostMapping("/switchTariff")
//    public ModelAndView switchTariff(@ModelAttribute("switch")SwitchTariffDto newTariff) throws DatabaseException, MyException {
//
//        contractService.switchTariff(newTariff);
//        UserProfileDto profile = dataService.getUserProfileById(newTariff.getUserId());
//        return new ModelAndView("admin/userInfo","profile", profile);
//    }
    @PostMapping("/switchTariff")
    public ModelAndView switchTariff(@ModelAttribute("switch") SwitchTariffDto newTariff) throws DatabaseException, MyException {

        contractService.switchTariff(newTariff);
        ModelAndView mav = new ModelAndView();
        ContractPageDto contractPage = dataService.getContractPage(newTariff.getContractId());
        List<TariffDto> tariffs = tariffService.findAllTariff();

        mav.addObject("profile",contractPage);
        mav.addObject("tariffs", tariffs);
        mav.setViewName("admin/contractInfo");
        return mav;
    }

    @GetMapping("/user{id}balance")
    public ModelAndView addFundsForm(@PathVariable("id") int id) throws DatabaseException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/addFundsPage");
        mav.addObject("funds", new AddFundsDto());
        mav.addObject("user", userService.findById(id));
        return mav;
    }

    @PostMapping("/balance")
    public ModelAndView addFunds(@ModelAttribute("funds") AddFundsDto fundsDto) throws DatabaseException, MyException {
        userService.addFunds(fundsDto);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/userInfo");
        mav.addObject("success",true);
        mav.addObject("profile", dataService.getUserProfileById(fundsDto.getUserId()));
        return mav;
    }

    @PostMapping("/relativeAdd")
    public ModelAndView addRelativeToOption(@ModelAttribute("relAdd") AddRelativeToOption addRelativeToOption)
            throws DatabaseException, MyException {

        optionService.addRelativeToOption(addRelativeToOption);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/optionInfo");
        mav.addObject("profile", dataService.getOptionPage(addRelativeToOption.getTargetOptionId()));
        return mav;
    }

    @PostMapping("/relativeDel")
    public ModelAndView delRelativeFromOption(@ModelAttribute("relDel") DelRelativeFromOption delRelativeFromOption)
            throws DatabaseException, MyException {

        optionService.delRelativeToOption(delRelativeFromOption);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/optionInfo");
        mav.addObject("profile", dataService.getOptionPage(delRelativeFromOption.getTargetOptionId()));
        return mav;
    }

    @PostMapping("/addOptionToTariff")
    public ModelAndView addOptionToTariff(@ModelAttribute("optionToTariff") EditTariffOptionList optionToTariff)
            throws DatabaseException, MyException {

        tariffService.addOption(optionToTariff);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/tariffInfo");
        mav.addObject("profile", dataService.getTariffPage(optionToTariff.getTariffId()));
        return mav;
    }

    @PostMapping("/delOptionFromTariff")
    public ModelAndView delOptionFromTariff(@ModelAttribute("optionFromTariff") EditTariffOptionList optionFromTariff)
            throws DatabaseException, MyException {

        tariffService.delOption(optionFromTariff);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/tariffInfo");
        mav.addObject("profile", dataService.getTariffPage(optionFromTariff.getTariffId()));
        return mav;
    }

    @PostMapping("/deleteOptionFromContract")
    public ModelAndView delOptionFromContract(@ModelAttribute("optionFromContract") EditContractDto editContractDto) throws DatabaseException, MyException {
        contractService.deleteOptionFromContract(editContractDto);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/contractInfo");
        mav.addObject("profile", dataService.getContractPage(editContractDto.getContractId()));
        return mav;
    }

    @PostMapping("/addOptionToContract")
    public ModelAndView addOptionToContract(@ModelAttribute("optionToContract") EditContractDto editContractDto) throws DatabaseException, MyException {
        contractService.addOptionToContract(editContractDto);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/contractInfo");
        mav.addObject("profile", dataService.getContractPage(editContractDto.getContractId()));
        return mav;
    }
}
