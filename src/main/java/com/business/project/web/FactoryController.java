package com.business.project.web;

import com.business.project.service.FactoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/factory")
public class FactoryController {

    private final FactoryService factoryService;

    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @GetMapping("/view")
    private String view(Model model) {
        model.addAttribute("factoryOne", factoryService.getFactoryByName("Naydenovi's Stanimaka EOOD"));
        model.addAttribute("factoryTwo", factoryService.getFactoryByName("Naydenovi's Serdika EOOD"));
        model.addAttribute("factoryThree", factoryService.getFactoryByName("Naydenovi's Pirgos EOOD"));

        return "factories";
    }
}
