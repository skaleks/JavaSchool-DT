package com.magenta.controller.user;


import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractPageDto;
import com.magenta.crud.contract.dto.EditContractDto;
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
import com.magenta.sessioncart.SessionCart;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final DataService dataService;
    private final UserService userService;
    private final TariffService tariffService;
    private final ContractService contractService;
    private final SecurityService securityService;
    private final SessionCart sessionCart;

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

    @PostMapping("/block-account")
    public ModelAndView blockAccount(@ModelAttribute("status") ChangeStatusDto statusDto) throws DatabaseException, AuthorizationException {
        userService.setStatus(statusDto);
        UserProfileDto userProfile = dataService.getUserProfileByLogin(securityService.getPrincipal());
        return new ModelAndView("user/profile","profile", userProfile);
    }

    @PostMapping("/block-contract")
    public ModelAndView blockContract(@ModelAttribute("status") ChangeStatusDto statusDto) throws DatabaseException, AuthorizationException {
        contractService.setStatus(statusDto);
        ContractPageDto contractPage = dataService.getContractPage(statusDto.getEntityId());
        return new ModelAndView("user/contractInfo","profile", contractPage);
    }

    @GetMapping("/contract{id}")
    public ModelAndView contractInfo(@PathVariable("id") int id) throws DatabaseException {
        ModelAndView mav = new ModelAndView();
        ContractPageDto contractPage = dataService.getContractPage(id);

        mav.addObject("profile",contractPage);
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
    public ModelAndView addFunds(@Valid @ModelAttribute("funds") AddFundsDto fundsDto, BindingResult result) throws DatabaseException, MyException {

        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()){
            mav.setViewName("user/addFundsPage");
            mav.addObject("funds", new AddFundsDto());
            mav.addObject("user", userService.findById(fundsDto.getUserId()));
            return mav;
        }

        userService.addFunds(fundsDto);
        mav.setViewName("user/userPanel");
        mav.addObject("success",true);
        mav.addObject("profile", dataService.getUserProfileById(fundsDto.getUserId()));
        mav.addObject("tariffs", tariffService.findAllTariff());
        return mav;
    }

    @GetMapping("/cart")
    public String cart(){
        return "user/cart";
    }

    @PostMapping("/contract/addOption")
    public ModelAndView addItemToCart(@ModelAttribute("editContractDto") EditContractDto editContractDto) throws DatabaseException, MyException {
//        if (result.hasErrors()){
//            return "Please, choose option to add";
//        }
        sessionCart.addOptionsToCart(editContractDto);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/contractInfo");
        mav.addObject("response", "Success");
        mav.addObject("profile", dataService.getContractPage(editContractDto.getContractId()));
        return mav;
    }

    @PostMapping("/contract/deleteOption")
    public ModelAndView deleteOptionFromContract(@ModelAttribute("editContractDto") EditContractDto editContractDto) throws DatabaseException, MyException {
        contractService.deleteOptionFromContract(editContractDto);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/contractInfo");
        mav.addObject("profile", dataService.getContractPage(editContractDto.getContractId()));
        return mav;
    }
}
