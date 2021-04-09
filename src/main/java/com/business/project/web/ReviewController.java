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
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ModelMapper modelMapper;
    private final WineService wineService;
    private final ReviewService reviewService;

    public ReviewController(ModelMapper modelMapper, WineService wineService, ReviewService reviewService) {
        this.modelMapper = modelMapper;
        this.wineService = wineService;
        this.reviewService = reviewService;
    }

    @GetMapping("/browse-{type}")
    public String browse(@PathVariable String type, Model model) {

        if (wineService.getWinesByType(type).size() == 0) {
            return "type-empty";
        }
        model.addAttribute("typeWines", wineService.getWinesByType(type));

        return "browse-wine";
    }

    @GetMapping("/addReview")
    public String addReview(Model model) {

        if (!model.containsAttribute("reviewAddBindingModel")) {
            model.addAttribute("reviewAddBindingModel", new ReviewAddBindingModel());
            model.addAttribute("allWines",this.wineService.getAllWines());
        }
        return "add-review";
    }

    @PostMapping("/addReview")
    public String addReviewConfirm(@Valid ReviewAddBindingModel reviewAddBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("reviewAddBindingModel",reviewAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reviewAddBindingModel",bindingResult);
            redirectAttributes.addFlashAttribute("allWines", this.wineService.getAllWines());

            return "redirect:addReview";
        }

        ReviewServiceModel reviewServiceModel = this.modelMapper.map(reviewAddBindingModel,ReviewServiceModel.class);
        this.reviewService.addReviewToWine(reviewServiceModel,reviewAddBindingModel.getWineName());

        return "redirect:/";
    }

    @GetMapping("/getReviews-{name}")
    public String getReviews(@PathVariable String name,Model model) {

        List<ReviewViewModel> reviewsOfWine = this.reviewService.getReviewsOfWine(name);
        model.addAttribute("reviewsOfWine", reviewsOfWine);


        return "review";
    }

    @GetMapping("/buyOne/{name}")
    public String buyOne(@PathVariable String name) {

        if (wineService.getWineByName(name).getAmount()>=1) {
            wineService.buyWine(name);
            return "order-success";
        }
        else {
            return "order-fail";
        }
    }
}
