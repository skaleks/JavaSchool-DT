package com.magenta.controller.admin;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.option.dto.NewOptionDto;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.NewTariffDto;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.NewUserDto;
import com.magenta.myexception.MyException;
import com.magenta.sessioncart.SessionCart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.logging.Logger;


@Controller
@RequestMapping("/admin/new")
@AllArgsConstructor
public class AdminCreateController {

    private static final Logger LOGGER = Logger.getLogger("Create");

    private final UserService userService;
    private final TariffService tariffService;
    private final OptionService optionService;
    private final ContractService contractService;
    private final SessionCart sessionCart;

//    @Autowired
//    public AdminCreateController(UserService userService, TariffService tariffService, OptionService optionService, ContractService contractService, ModelMapper modelMapper) {
//        this.userService = userService;
//        this.tariffService = tariffService;
//        this.optionService = optionService;
//        this.contractService = contractService;
//        this.modelMapper = modelMapper;
//    }

    @GetMapping("/user")
    public String newFormUser(@ModelAttribute("user") NewUserDto newUserDto){
        return "admin/addUser";
    }

    @PostMapping("/user")
    public String addUser(@Valid @ModelAttribute("user") NewUserDto newUserDto, BindingResult result){
        if (result.hasErrors()){
            return "admin/addUser";
        }
        userService.save(newUserDto);
        return "redirect:/admin";
    }

    @GetMapping("/tariff")
    public String newFormTariff(@ModelAttribute("tariff") NewTariffDto newTariffDto, Model model){
        model.addAttribute("options", optionService.findAllOptions());
//        model.addAttribute("s", sessionCart.getCount());
        return "admin/addTariff";
    }

    @PostMapping("/tariff")
    public String addTariff(@Valid @ModelAttribute("tariff") NewTariffDto newTariffDto, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("options", optionService.findAllOptions());
            return "admin/addTariff";
        }
        tariffService.createNewTariff(newTariffDto);
//        sessionCart.setSize(25);
        return "redirect:/admin";
    }

    @GetMapping("/option")
    public String newFormOption(@ModelAttribute("option")NewOptionDto newOptionDto){
        return "admin/addOption";
    }

    @PostMapping("/option")
    public String addOption(@Valid @ModelAttribute("option") NewOptionDto newOptionDto, BindingResult result){
        if (result.hasErrors()){
            return "admin/addOption";
        }
        optionService.createNewOption(newOptionDto);
        return "redirect:/admin";
    }

    @PostMapping("/contract")
    public String addContract(@ModelAttribute("contract") NewContractDto newContractDto) throws MyException {
        contractService.saveContract(newContractDto);
        return "redirect:/admin";
    }

}
