package com.magenta.controller.admin;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.AllContractsDto;
import com.magenta.crud.global.DataService;
import com.magenta.crud.global.dto.AdminMainDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.option.dto.AllOptionsDto;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.AllTariffDto;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.AllUsersDto;
import com.magenta.crud.user.dto.UserProfileDto;
import com.magenta.myexception.DatabaseException;
import com.magenta.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
        AllOptionsDto allOptionsDto = new AllOptionsDto();
        allOptionsDto.setOptionDtoList(optionService.findAllOptions());
        model.addAttribute("options", allOptionsDto);
        return "admin/allOptions";
    }

    @GetMapping("/user{id}")
    public String userInfo(@PathVariable("id") int id, Model model) throws DatabaseException {
        model.addAttribute("profile", dataService.getUserProfileById(id));
        return "admin/userInfo";
    }

    @GetMapping("/tariff{id}")
    public String tariffInfo(@PathVariable("id") int id, Model model) throws DatabaseException {
        model.addAttribute("profile", dataService.getTariffPage(id));
        return "admin/tariffInfo";
    }

    @GetMapping("/option{id}")
    public String optionInfo(@PathVariable("id") int id, Model model) throws DatabaseException {
        model.addAttribute("profile", dataService.getOptionPage(id));
        return "admin/optionInfo";
    }

    @GetMapping("/contract{id}")
    public String contractInfo(@PathVariable("id") int id, Model model) throws Exception {
        model.addAttribute("profile", dataService.getContractPage(id));
        return "admin/contractInfo";
    }

    @GetMapping("/searchByNumberOrEmail")
    public String searchByNumberOrEmail(@RequestParam("search") String request, Model model) throws DatabaseException {
        model.addAttribute("profile", dataService.getUserProfileByNumberOrEmail(request));
        return "admin/userInfo";
    }
}
