package com.magenta.controller.admin;


import com.magenta.crud.global.DataService;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.option.dto.AddRelativeToOption;
import com.magenta.crud.option.dto.AllOptionsDto;
import com.magenta.crud.option.dto.DelRelativeFromOption;
import com.magenta.crud.option.dto.NewOptionDto;
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
@RequestMapping("/admin/option")
@AllArgsConstructor
public class AdminOptionHandler {

    private static final Logger LOGGER = Logger.getLogger("OptionHandler");
    private final OptionService optionService;
    private final DataService dataService;

    @GetMapping("/list")
    public String all(Model model) {
        AllOptionsDto allOptionsDto = new AllOptionsDto();
        allOptionsDto.setOptionDtoList(optionService.findAllOptions());
        model.addAttribute("options", allOptionsDto);
        return "admin/allOptions";
    }

    @GetMapping("/{id}")
    public String info(@PathVariable("id") int id, Model model) throws DatabaseException {
        model.addAttribute("profile", dataService.getOptionPage(id));
        return "admin/optionInfo";
    }

    @GetMapping("/new")
    public ModelAndView newForm(@ModelAttribute("option") NewOptionDto newOptionDto){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/addOption");
        mav.addObject("allOptions", optionService.findAllOptions());
        return mav;
    }

    @PostMapping("/new")
    public String add(@Valid @ModelAttribute("option") NewOptionDto newOptionDto, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("allOptions", optionService.findAllOptions());
            return "admin/addOption";
        }
        optionService.createNewOption(newOptionDto);
        return "redirect:/admin";
    }

    @GetMapping("/delete{id}")
    public String delete(@PathVariable("id") int id) throws DatabaseException, MyException {
        optionService.deleteExistOption(id);
        return "redirect:/admin/allOptions";
    }

    @PostMapping("/relativeAdd")
    public ModelAndView addRelativeToOption(@ModelAttribute("relAdd") AddRelativeToOption addRelativeToOption)
            throws DatabaseException, MyException {

        optionService.addRelativeToOption(addRelativeToOption);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/optionInfo");
        mav.addObject("profile", dataService.getOptionPage(addRelativeToOption.getTargetOptionId()));
        return mav;
    }

    @PostMapping("/relativeDel")
    public ModelAndView delRelativeFromOption(@ModelAttribute("relDel") DelRelativeFromOption delRelativeFromOption)
            throws DatabaseException, MyException {

        optionService.delRelativeToOption(delRelativeFromOption);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/optionInfo");
        mav.addObject("profile", dataService.getOptionPage(delRelativeFromOption.getTargetOptionId()));
        return mav;
    }
}
