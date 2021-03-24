package com.business.project.web;

import com.business.project.model.binding.UserCommandBindingModel;
import com.business.project.model.service.UserServiceModel;
import com.business.project.service.LogService;
import com.business.project.service.UserService;
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
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final LogService logService;

    public AdminController(UserService userService, ModelMapper modelMapper, LogService logService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.logService = logService;
    }

    @GetMapping("/control")
    private String control(Model model) {
        if (!model.containsAttribute("userCommandBindingModel")) {
            model.addAttribute("userCommandBindingModel", new UserCommandBindingModel());
            model.addAttribute("isAlreadyAdmin",false);
            model.addAttribute("isAlreadyGuestUser",false);
            model.addAttribute("userFound",true);
            model.addAttribute("isSameUser", false);

        }

        return "control";
    }

    @PostMapping("/control")
    private String controlConfirm(@Valid UserCommandBindingModel userCommandBindingModel,
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

        return "log";

    }
}
