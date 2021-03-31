package com.business.project.web;

import com.business.project.model.view.WineViewModel;
import com.business.project.service.WineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wine-rest")
public class WineRestController {

    private final WineService wineService;

    public WineRestController(WineService wineService) {
        this.wineService = wineService;
    }

    @GetMapping("/api/{id}")
    public WineViewModel wineViewModel(@PathVariable Long id) {
        return this.wineService.getWineViewBydId(id);
    }

    @GetMapping("/api/all")
    public List<WineViewModel> allWines() {
        return this.wineService.getAllWines();
    }
}
