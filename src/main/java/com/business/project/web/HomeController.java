package com.business.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    private String home() {

        //todo add home page and implement logic for redirect at /home.
        return "/index";
    }
}
