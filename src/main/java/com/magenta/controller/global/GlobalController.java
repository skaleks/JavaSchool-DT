package com.magenta.controller.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GlobalController {

    @GetMapping()
    public String mainPage(){
        return "index";
    }
}
