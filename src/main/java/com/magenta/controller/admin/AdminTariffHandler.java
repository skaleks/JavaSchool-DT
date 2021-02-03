package com.magenta.controller.admin;

import com.magenta.crud.global.DataService;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.option.dto.EditTariffOptionList;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.AllTariffDto;
import com.magenta.crud.tariff.dto.NewTariffDto;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/tariff")
@AllArgsConstructor
public class AdminTariffHandler {

    private static final Logger LOGGER = Logger.getLogger("TariffHandler");
    private final TariffService tariffService;
    private final OptionService optionService;
    private final DataService dataService;

    @GetMapping("/list")
    public String allTariffs(Model model) {
        AllTariffDto allTariffDto = new AllTariffDto();
        allTariffDto.setTariffDtoList(tariffService.findAllTariff());
        model.addAttribute("tariffs", allTariffDto);
        return "admin/allTariffs";
    }

    @GetMapping("/{id}")
    public String tariffInfo(@PathVariable("id") int id, Model model) throws DatabaseException {

        model.addAttribute("profile", dataService.getTariffPage(id));
        return "admin/tariffInfo";
    }

    @GetMapping("/new")
    public String newFormTariff(@ModelAttribute("tariff") NewTariffDto newTariffDto, Model model){
        model.addAttribute("options", optionService.findAllOptions());
        return "admin/addTariff";
    }

    @PostMapping("/new")
    public String addTariff(@Valid @ModelAttribute("tariff") NewTariffDto newTariffDto, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("options", optionService.findAllOptions());
            return "admin/addTariff";
        }
        tariffService.createNewTariff(newTariffDto);
        return "redirect:/admin";
    }

    @GetMapping("/delete{id}")
    public String deleteTariff(@PathVariable("id") int id) throws DatabaseException, MyException {
        tariffService.deleteExistTariff(id);
        return "redirect:/admin/allTariffs";
    }

    @PostMapping("/addOption")
    public ModelAndView addOptionToTariff(@ModelAttribute("optionToTariff") EditTariffOptionList optionToTariff)
            throws DatabaseException, MyException {

        tariffService.addOption(optionToTariff);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/tariffInfo");
        mav.addObject("profile", dataService.getTariffPage(optionToTariff.getTariffId()));
        return mav;
    }

    @PostMapping("/delOption")
    public ModelAndView delOptionFromTariff(@ModelAttribute("optionFromTariff") EditTariffOptionList optionFromTariff)
            throws DatabaseException, MyException {

        tariffService.delOption(optionFromTariff);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/tariffInfo");
        mav.addObject("profile", dataService.getTariffPage(optionFromTariff.getTariffId()));
        return mav;
    }
}
