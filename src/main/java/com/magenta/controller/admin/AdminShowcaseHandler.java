package com.magenta.controller.admin;


import com.magenta.crud.showcase.ShowcaseService;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.AddTariffToShowcase;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.myexception.DatabaseException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/showcase")
@AllArgsConstructor
public class AdminShowcaseHandler {

    private final ShowcaseService showcaseService;
    private final TariffService tariffService;


    @PostMapping("/add")
    public String addTariffToShowcase(@ModelAttribute("addTariff") AddTariffToShowcase tariffToShowcase) throws DatabaseException {
        TariffDto tariff = tariffService.findTariffById(tariffToShowcase.getTariffId());
        showcaseService.initTariff(tariff, tariffToShowcase.getDescription());
        return "admin/Showcase";
    }
}
