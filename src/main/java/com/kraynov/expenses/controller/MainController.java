package com.kraynov.expenses.controller;

import com.kraynov.expenses.service.DepositService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/index")
public class MainController {
    public static final String INDEX_PAGE_URL = "index";
    public static final String INDEX_URL_REDIRECTION = "redirect:/"+INDEX_PAGE_URL;

    private final DepositService service;

    public MainController(DepositService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Map<String, Object> model) {
        model.put("persons", service.getDepositInfoForAll());
        return INDEX_PAGE_URL;
    }
}
