package com.bydefault.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("")
    public  String home(Model model) {
        model.addAttribute("message", "Hello World!");
        return "index";
    }
}
