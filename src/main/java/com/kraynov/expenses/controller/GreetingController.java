package com.kraynov.expenses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Test Controller which needed to test application succesfully work after some changes with web templates engine
 */
@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required = false, defaultValue = "World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/m")
    public String greeting() {
        return "main111";
    }

    @GetMapping("/main")
    public String greeting2() {
        return "deposits_mockup";
    }
}
