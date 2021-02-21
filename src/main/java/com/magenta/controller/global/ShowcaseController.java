package com.magenta.controller.global;


import com.magenta.crud.global.DataService;
import com.magenta.crud.showcase.dto.ShowcaseProfileDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/showcase")
@AllArgsConstructor
public class ShowcaseController {

    private final DataService dataService;

    @GetMapping("/initial")
    public ShowcaseProfileDto getShowcaseToAnotherApp(){
        return dataService.getShowcaseProfile();
    }
}
