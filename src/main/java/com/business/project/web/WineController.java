package com.business.project.web;

import com.business.project.model.binding.WineAddBindingModel;
import com.business.project.model.service.WineServiceModel;
import com.business.project.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    private String add(Model model) {
        if (!model.containsAttribute("wineAddBindingModel")) {
            model.addAttribute("wineAddBindingModel", new WineAddBindingModel());
        }

        return "add-wine";
    }

    @PostMapping("/add")
    private String addConfirm(@Valid WineAddBindingModel wineAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

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
