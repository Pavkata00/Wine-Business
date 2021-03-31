package com.business.project.web;

import com.business.project.model.binding.WineAddBindingModel;
import com.business.project.model.service.WineServiceModel;
import com.business.project.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/wine")
public class WineController {

    private final WineService wineService;
    private final ModelMapper modelMapper;

    public WineController(WineService wineService, ModelMapper modelMapper) {
        this.wineService = wineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(Model model) {
        if (!model.containsAttribute("wineAddBindingModel")) {
            model.addAttribute("wineAddBindingModel", new WineAddBindingModel());
        }

        return "add-wine";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid WineAddBindingModel wineAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("wineAddBindingModel", wineAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wineAddBindingModel", bindingResult);
            return "redirect:add";
        }

        WineServiceModel wineServiceModel = this.modelMapper.map(wineAddBindingModel,WineServiceModel.class);

        this.wineService.addWine(wineServiceModel);

        return "redirect:/";
    }

}
