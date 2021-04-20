package com.j6512.stayfocused.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "STAY FOCUSED");

        return "index";
    }

//    @GetMapping("todo")
//    public String displayCalendar(Model model) {
//        model.addAttribute("title", "THINGS TO DO");
//
//        return "todo";
//    }
}
