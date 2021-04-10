package com.business.project.web;

import com.business.project.model.binding.UserCommandBindingModel;
import com.business.project.model.binding.WineUpdateBindingModel;
import com.business.project.model.service.UserServiceModel;
import com.business.project.model.service.WineServiceModel;
import com.business.project.service.LogService;
import com.business.project.service.RecordService;
import com.business.project.service.UserService;
import com.business.project.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final LogService logService;
    private final WineService wineService;
    private final RecordService recordService;

    public AdminController(UserService userService, ModelMapper modelMapper, LogService logService, WineService wineService, RecordService recordService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.logService = logService;
        this.wineService = wineService;
        this.recordService = recordService;
    }

    @GetMapping("/control")
    private String control(Model model) {
        if (!model.containsAttribute("userCommandBindingModel")) {
            model.addAttribute("userCommandBindingModel", new UserCommandBindingModel());
            model.addAttribute("isAlreadyAdmin",false);
            model.addAttribute("isAlreadyGuestUser",false);
            model.addAttribute("userFound",true);

        }

        return "control";
    }

    @PostMapping("/control")
    public String controlConfirm(@Valid UserCommandBindingModel userCommandBindingModel,
                                  BindingResult  bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userCommandBindingModel",userCommandBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userCommandBindingModel",bindingResult);
            return "redirect:control";
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userCommandBindingModel,UserServiceModel.class);

        if (!this.userService.usernameExists(userServiceModel)) {
            redirectAttributes.addFlashAttribute("userCommandBindingModel",userCommandBindingModel);
            redirectAttributes.addFlashAttribute("userFound",false);
            redirectAttributes.addFlashAttribute("successCommand",false);
            return "redirect:control";
        }

        if (this.userService.isDemotingHimself(userServiceModel)) {
            redirectAttributes.addFlashAttribute("userCommandBindingModel",userCommandBindingModel);
            redirectAttributes.addFlashAttribute("isSameUser",true);
            setAttributes(redirectAttributes);
            return "redirect:control";
        }

        if (this.userService.isAdmin(userServiceModel) && userCommandBindingModel.getCommand().equals("Promote")) {

            redirectAttributes.addFlashAttribute("userCommandBindingModel",userCommandBindingModel);
            redirectAttributes.addFlashAttribute("isAlreadyAdmin",true);

            setAttributes(redirectAttributes);

            return "redirect:control";

        } else if (!this.userService.isAdmin(userServiceModel) && userCommandBindingModel.getCommand().equals("Demote") ){
            redirectAttributes.addFlashAttribute("userCommandBindingModel",userCommandBindingModel);
            redirectAttributes.addFlashAttribute("isAlreadyGuestUser",true);

            setAttributes(redirectAttributes);
            return "redirect:control";
        }

        this.userService.executeCommand(userServiceModel);
        redirectAttributes.addFlashAttribute("successCommand",true);

        return "redirect:control";
    }

    private void setAttributes(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("userFound",true);
        redirectAttributes.addFlashAttribute("successCommand",false);
    }

    @GetMapping("/logs")
    public String logs(Model model) {

        model.addAttribute("allLogs", this.logService.getAllLogs());
        model.addAttribute("mostBoughtWine", this.recordService.getMostBoughtWine());

        return "log";

    }

    @GetMapping("/update/{name}")
    public String update(@PathVariable String name, Model model) {
        model.addAttribute("nameOfWine", name);

        if (!model.containsAttribute("wineUpdateBindingModel")) {
            model.addAttribute("wineUpdateBindingModel",new WineUpdateBindingModel());
        }
        return "update";
    }

    @PatchMapping("/fill/{wineName}")
    public String updateConfirm(@Valid WineUpdateBindingModel wineUpdateBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @PathVariable String wineName) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("wineUpdateBindingModel", wineUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wineUpdateBindingModel", bindingResult);
            return "redirect:/admin/update/" + wineName;
        }

        WineServiceModel wineServiceModel = this.modelMapper.map(wineUpdateBindingModel,WineServiceModel.class);
        this.wineService.update(wineServiceModel, wineName);
        return "redirect:/";

    }
}
