package com.business.project.web;

import com.business.project.service.ReviewService;
import com.business.project.service.WineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/winesType")
public class WineTypeController {

    private final WineService wineService;
    private final ReviewService reviewService;

    public WineTypeController(WineService wineService, ReviewService reviewService) {
        this.wineService = wineService;
        this.reviewService = reviewService;
    }

    @GetMapping("/browse-{type}")
    private String browse(@PathVariable String type, Model model) {

        model.addAttribute("typeWines", this.wineService.getWinesByType(type));

        return "browse-wine";
    }

    //todo logic for these methods + html pages.
    @GetMapping("/getReviews/{name}")
    private String getReviews(@PathVariable String name,Model model) {

        model.addAttribute("reviewsOfWine",this.reviewService.getReviewsOfWine(name));


        return "review";
    }


    @GetMapping("/addReview/{name}")
    private String addReview(@PathVariable String name) {

        return "add-review";
    }

    @PostMapping("/deleteOne/{name}")
    private String deleteOne(@PathVariable String name) {

        return "order-complete";
    }
}
