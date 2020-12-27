package com.magenta.controller;


import com.magenta.myexception.MyException;
import com.magenta.model.Contract;
import com.magenta.model.Option;
import com.magenta.model.Tariff;
import com.magenta.model.User;
import com.magenta.service.OptionService;
import com.magenta.service.TariffService;
import com.magenta.service.UserService;
import com.magenta.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    UserService userService;
    ContractService contractService;
    TariffService tariffService;
    OptionService optionService;

    @Autowired
    public AdminController(UserService userService, ContractService contractService, TariffService tariffService, OptionService optionService) {
        this.userService = userService;
        this.contractService = contractService;
        this.tariffService = tariffService;
        this.optionService = optionService;
    }

    @GetMapping()
    public String adminPanel(){
        return "admin/adminPanel";
    }

    @GetMapping("/allUsers")
    public String allClients(Model model){
        model.addAttribute("users", userService.findAllUsers());
        return "admin/allUsers";
    }
    @GetMapping("/allContracts")
    public String allContracts(Model model) {
        model.addAttribute("contracts",contractService.findAllContracts());
        return "admin/allContracts";
    }
    @GetMapping("/allTariffs")
    public String allTariffs(Model model) {
        model.addAttribute("tariffs", tariffService.findAllTariff());
        return "admin/allTariffs";
    }
    @GetMapping("/allOptions")
    public String allOptions(Model model) {
        model.addAttribute("options", optionService.findAllOptions());
        return "admin/allOptions";
    }

    @GetMapping("/user{id}")
    public String userInfo(@PathVariable("id") int id, Model model) throws Exception {
        model.addAttribute("user", userService.findById(id));
        return "admin/userInfo";
    }

    @GetMapping("/newFormUser")
    public String newFormUser(@ModelAttribute("user") User user){
        return "admin/addUser";
    }

    @GetMapping("/newFormTariff")
    public String newFormTariff(@ModelAttribute("tariff") Tariff tariff){
        return "admin/addTariff";
    }

    @GetMapping("/newFormContract")
    public String newFormContract(@ModelAttribute("contract")Contract contract){
        return "admin/addContract";
    }

    @GetMapping("/newFormOption")
    public String newFormOption(@ModelAttribute("option")Option option){
        return "admin/addOption";
    }

    @PostMapping("/user")
    public String addUser(@ModelAttribute("user") User user, Model model){
        userService.save(user);
        model.addAttribute("user",user);
        return "admin/userInfo";
    }
    @PostMapping("/tariff")
    public String addTariff(@ModelAttribute("tariff") Tariff tariff){
        tariffService.createNewTariff(tariff);
        return "redirect:/admin";
    }

    @PostMapping("/option")
    public String addOption(@ModelAttribute("option") Option option){
        optionService.createNewOption(option);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/deleteUser")
    public String deleteUser(@PathVariable("id") int id, Model model){
        userService.deleteById(id);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/{id}/deleteTariff")
    public String deleteTariff(@PathVariable("id") int id, Model model) throws MyException {
        Tariff tariff = tariffService.findTariffById(id);
        tariffService.deleteExistTariff(tariff);
        model.addAttribute("tariffs", tariffService.findAllTariff());
        return "redirect:/admin/allTariffs";
    }

    @GetMapping("/{id}/deleteOption")
    public String deleteOption(@PathVariable("id") int id) throws MyException {
        Option option = optionService.findOptionById(id);
        optionService.deleteExistOption(option);
        return "redirect:/admin/allOptions";
    }
    @GetMapping("/{id}/updateUser")
    public String editUser(@PathVariable("id") int id, Model model) throws Exception {
        model.addAttribute("user", userService.findById(id));
        return "admin/updateUser";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, Model model){
        userService.update(user);
        model.addAttribute("user",user);
        return "admin/userInfo";
    }
}
