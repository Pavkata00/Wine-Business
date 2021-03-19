package com.business.project.web;

import com.business.project.model.binding.ReviewAddBindingModel;
import com.business.project.model.service.ReviewServiceModel;
import com.business.project.model.view.ReviewViewModel;
import com.business.project.service.ReviewService;
import com.business.project.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/winesType")
public class WineTypeController {

    private final ModelMapper modelMapper;
    private final WineService wineService;
    private final ReviewService reviewService;

    public WineTypeController(ModelMapper modelMapper, WineService wineService, ReviewService reviewService) {
        this.modelMapper = modelMapper;
        this.wineService = wineService;
        this.reviewService = reviewService;
    }

    @GetMapping("/browse-{type}")
    private String browse(@PathVariable String type, Model model) {

        model.addAttribute("typeWines", this.wineService.getWinesByType(type));

        return "browse-wine";
    }

    //todo logic for these methods + html pages.
    @GetMapping("/getReviews-{name}")
    private String getReviews(@PathVariable String name,Model model) {

        model.addAttribute("reviewsOfWine",this.reviewService.getReviewsOfWine(name));


        return "review";
    }


    @GetMapping("/addReview-{name}")
    private String addReview(@PathVariable String name,Model model) {

        if (!model.containsAttribute("reviewAddBindingModel")) {
            model.addAttribute("reviewAddBindingModel", new ReviewAddBindingModel());
            model.addAttribute("nameOfWine",name);
        }
        return "add-review";
    }

    @PostMapping("/addReview-{name}")
    private String addReviewConfirm(@Valid ReviewAddBindingModel reviewAddBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @PathVariable String name) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewAddBindingModel",reviewAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewAddBindingModel",bindingResult);

            //todo fix the bug with validation errors and path /
            return "redirect:/winesType/addReview-" + name;
        }

        ReviewServiceModel reviewServiceModel = this.modelMapper.map(reviewAddBindingModel,ReviewServiceModel.class);
        this.reviewService.addReviewToWine(reviewServiceModel,name);

        return "redirect:/winesType/getReviews-"+name;
    }









    @PostMapping("/deleteOne/{name}")
    private String deleteOne(@PathVariable String name) {

        return "order-complete";
    }
}
