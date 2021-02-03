package com.magenta.controller.admin;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.AllContractsDto;
import com.magenta.crud.contract.dto.ContractPageDto;
import com.magenta.crud.contract.dto.EditContractDto;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.global.DataService;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.SwitchTariffDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/contract")
@AllArgsConstructor
public class AdminContractHandler {

    private static final Logger LOGGER = Logger.getLogger("ContractHandler");
    private final TariffService tariffService;
    private final ContractService contractService;
    private final DataService dataService;

    @GetMapping("/list")
    public String all(Model model) {
        AllContractsDto allContractsDto = new AllContractsDto();
        allContractsDto.setContractDtoList(contractService.findAllContracts());
        model.addAttribute("contracts",allContractsDto);
        return "admin/allContracts";
    }

    @GetMapping("/{id}")
    public ModelAndView info(@PathVariable("id") int id) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/contractInfo");
        mav.addObject("profile", dataService.getContractPage(id));
        return mav;
    }

    @GetMapping("/new")
    public String newForm(){
        return "admin/newContract";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("contract") NewContractDto newContractDto) throws DatabaseException, MyException {
        contractService.saveContract(newContractDto);
        return "redirect:/admin";
    }

    @GetMapping("/delete{id}")
    public String delete(@PathVariable("id") int id) throws DatabaseException {
        contractService.deleteContract(id);
        return "redirect:/admin/allContracts";
    }

    @PostMapping("/status")
    public ModelAndView changeStatus(@ModelAttribute("status") ChangeStatusDto changeStatusDto) throws DatabaseException, AuthorizationException {

        contractService.setStatus(changeStatusDto);
        ContractPageDto contractPage = dataService.getContractPage(changeStatusDto.getEntityId());
        return new ModelAndView("admin/contractInfo","profile", contractPage);
    }

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

    @PostMapping("/deleteOption")
    public ModelAndView delOption(@ModelAttribute("optionFromContract") EditContractDto editContractDto) throws DatabaseException, MyException {
        contractService.deleteOptionFromContract(editContractDto);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/contractInfo");
        mav.addObject("profile", dataService.getContractPage(editContractDto.getContractId()));
        return mav;
    }

    @PostMapping("/addOption")
    public ModelAndView addOption(@ModelAttribute("optionToContract") EditContractDto editContractDto) throws DatabaseException, MyException {
        contractService.addOptionToContract(editContractDto);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/contractInfo");
        mav.addObject("profile", dataService.getContractPage(editContractDto.getContractId()));
        return mav;
    }
}
