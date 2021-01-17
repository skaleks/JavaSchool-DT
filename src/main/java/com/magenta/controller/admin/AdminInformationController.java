package com.magenta.controller.admin;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.AllContractsDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.AllTariffDto;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.AllUsersDto;
import com.magenta.myexception.MyException;
import com.magenta.sessioncart.SessionCart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminInformationController {

    private static final Logger LOGGER = Logger.getLogger("Info");

    private final UserService userService;
    private final ContractService contractService;
    private final TariffService tariffService;
    private final OptionService optionService;
    private final SessionCart sessionCart;

//    @Autowired
//    public AdminInformationController(UserService userService, ContractService contractService, TariffService tariffService, OptionService optionService) {
//        this.userService = userService;
//        this.contractService = contractService;
//        this.tariffService = tariffService;
//        this.optionService = optionService;
//    }

    @GetMapping()
    public String adminPanel(Model model){
//        model.addAttribute("s", sessionCart.getCount());
        return "admin/adminPanel";
    }

    @GetMapping("/allUsers")
    public String allUsers(Model model){
        AllUsersDto allUsersDto = new AllUsersDto();
        allUsersDto.setListOfUsers(userService.findAllUsers());
        model.addAttribute("users", allUsersDto);
        return "admin/allUsers";
    }
    @GetMapping("/allContracts")
    public String allContracts(Model model) {
        AllContractsDto allContractsDto = new AllContractsDto();
        allContractsDto.setContractDtoList(contractService.findAllContracts());
        model.addAttribute("contracts",allContractsDto);
        return "admin/allContracts";
    }
    @GetMapping("/allTariffs")
    public String allTariffs(Model model) {
        AllTariffDto allTariffDto = new AllTariffDto();
        allTariffDto.setTariffDtoList(tariffService.findAllTariff());
        model.addAttribute("tariffs", allTariffDto);
        return "admin/allTariffs";
    }
    @GetMapping("/allOptions")
    public String allOptions(Model model) {
        model.addAttribute("options", optionService.findAllOptions());
        return "admin/allOptions";
    }

    @GetMapping("/user{id}")
    public String userInfo(@PathVariable("id") int id, Model model) throws MyException {
        model.addAttribute("user", userService.findById(id));
        return "admin/userInfo";
    }

    @GetMapping("/searchByNumber")
    public String searchByNumber(@RequestParam("search") String number, Model model) throws MyException {
        model.addAttribute("user", userService.findByNumber(number));
        return "admin/userInfo";
    }
}
