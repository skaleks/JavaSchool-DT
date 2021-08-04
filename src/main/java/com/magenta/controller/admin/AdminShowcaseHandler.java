package com.magenta.controller.admin;


import com.magenta.crud.global.DataService;
import com.magenta.crud.showcase.ShowcaseService;
import com.magenta.crud.showcase.dto.ShowcaseProfileDto;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.AddTariffToShowcase;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.myexception.DatabaseException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/showcase")
@AllArgsConstructor
public class AdminShowcaseHandler {

    private final ShowcaseService showcaseService;
    private final TariffService tariffService;
    private final DataService dataService;


    @GetMapping
    public String showcaseProfile(Model model){
        ShowcaseProfileDto profile = dataService.getShowcaseProfile();
        model.addAttribute("profile", profile);
        return "admin/showcase";
    }

    @PostMapping("/add")
    public String addTariffToShowcase(@ModelAttribute("addTariff") AddTariffToShowcase tariffToShowcase) throws DatabaseException {
        TariffDto tariff = tariffService.findTariffById(tariffToShowcase.getTariffId());
        showcaseService.initTariff(tariff, tariffToShowcase.getDescription());
        return "admin/showcase";
    }
}
