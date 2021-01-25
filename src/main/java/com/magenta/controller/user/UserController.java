package com.magenta.controller.user;


import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractPageDto;
import com.magenta.crud.global.DataService;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.SwitchTariffDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.AddFundsDto;
import com.magenta.crud.user.dto.UserProfileDto;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import com.magenta.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final DataService dataService;
    private final UserService userService;
    private final TariffService tariffService;
    private final ContractService contractService;
    private final SecurityService securityService;

    @GetMapping
    public ModelAndView userPage() throws DatabaseException {
        String login = securityService.getPrincipal();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/userPanel");
        mav.addObject("profile", dataService.getUserProfileByLogin(login));
        mav.addObject("tariffs", tariffService.findAllTariff());
        return mav;
    }

    @GetMapping("/profile")
    public ModelAndView userProfile() throws DatabaseException {
        UserProfileDto userProfile = dataService.getUserProfileByLogin(securityService.getPrincipal());
        return new ModelAndView("user/profile","profile", userProfile);
    }

    @PostMapping("/block")
    public ModelAndView blockAccount(@ModelAttribute("status") ChangeStatusDto statusDto) throws DatabaseException, AuthorizationException {
        userService.setStatus(statusDto);
        UserProfileDto userProfile = dataService.getUserProfileByLogin(securityService.getPrincipal());
        return new ModelAndView("user/profile","profile", userProfile);
    }

    @GetMapping("/contract{id}")
    public ModelAndView contractInfo(@PathVariable("id") int id) throws DatabaseException {
        ModelAndView mav = new ModelAndView();
        ContractPageDto contractPage = dataService.getContractPage(id);
        List<TariffDto> tariffs = tariffService.findAllTariff();

        mav.addObject("profile",contractPage);
        mav.addObject("tariffs", tariffs);
        mav.setViewName("user/contractInfo");
        return mav;
    }

    @PostMapping("/switchTariff")
    public ModelAndView switchTariff(@ModelAttribute("switch") SwitchTariffDto newTariff) throws DatabaseException, MyException {

        contractService.switchTariff(newTariff);
        ModelAndView mav = new ModelAndView();
        ContractPageDto contractPage = dataService.getContractPage(newTariff.getContractId());
        List<TariffDto> tariffs = tariffService.findAllTariff();

        mav.addObject("profile",contractPage);
        mav.addObject("tariffs", tariffs);
        mav.setViewName("user/contractInfo");
        return mav;
    }

    @GetMapping("/{id}/balance")
    public ModelAndView addFundsForm(@PathVariable("id") int id) throws DatabaseException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/addFundsPage");
        mav.addObject("funds", new AddFundsDto());
        mav.addObject("user", userService.findById(id));
        return mav;
    }

    @PostMapping("/balance")
    public ModelAndView addFunds(@ModelAttribute("funds") AddFundsDto fundsDto) throws DatabaseException {
        userService.addFunds(fundsDto);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/userPanel");
        mav.addObject("success",true);
        mav.addObject("profile", dataService.getUserProfileById(fundsDto.getUserId()));
        mav.addObject("tariffs", tariffService.findAllTariff());
        return mav;
    }
}
