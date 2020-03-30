package com.kraynov.expenses.controller;

import com.kraynov.expenses.service.DepositService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/card")
public class CardController {
    private static final String INDEX_URL_REDIRECTION = "redirect:/deposits";

    private final DepositService service;

    public CardController(DepositService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public String addCardScreen(@RequestParam Long personId, Map<String, Object> model) {
        System.out.println("pakr work id=" + personId);
        System.out.println("pakr work model=" + model);
        model.put("person", service.getPersonById(personId));
        return "/card/addCard.html";
    }

    @PostMapping("/add")
    public String addCard(@RequestParam Long personId,
                          @RequestParam String cardOpenDate,
                          @RequestParam String bankName,
                          @RequestParam Integer balance) {
        System.out.println("pakr work id=" + personId);
        System.out.println("pakr work cardOpenDate=" + cardOpenDate);
        System.out.println("pakr work bankName=" + bankName);
        System.out.println("pakr work balance=" + balance);

        service.openNewCard(personId, cardOpenDate, bankName, balance);
        return INDEX_URL_REDIRECTION;
    }

    @GetMapping("/delete")
    public String closeCard(@RequestParam Long cardId) {
        System.out.println("deleting card with id=" + cardId);
        service.closeCard(cardId);
        return INDEX_URL_REDIRECTION;
    }

    @GetMapping("/addMoney")
    public String addMoneyToCard(@RequestParam Long cardId,  Map<String, Object> model) {
        System.out.println("service.getCardById(cardId) = "+service.getCardById(cardId).getOpenDate());
        model.put("card", service.getCardById(cardId));
        return "/card/addMoneyToCard.html";
    }

    @PostMapping("/addMoney")
    public String addMoneyToCard(@RequestParam Long cardId,
                                 @RequestParam(defaultValue = "0") Integer newMoney) {
        System.out.println("adding money to card with id=" + cardId);
        service.addMoneyToCard(cardId, newMoney);
        return INDEX_URL_REDIRECTION;
    }

//    @GetMapping("/deposit/add")
//    public String addDeposit(Map<String, Object> model) {
//        System.out.println("pakr work model"+model);
//        return "card";
//    }
//
//    @PostMapping("/deposit/add")
//    public String addDeposit(Map<String, Object> model) {
//        System.out.println("pakr work model"+model);
//        return "redirect:/deposits";
//    }
}
