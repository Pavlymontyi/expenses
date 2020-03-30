package com.kraynov.expenses.controller;

import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.service.DepositService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class MainController {

    private final DepositService service;

    public MainController(DepositService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Map<String, Object> model) {
        model.put("persons", service.getDepositInfoForAll());
        return "index";
    }

    public void init() {
        //        Person pasha = new Person("Паша");
//        Person lenka = new Person("Лена");
//        Person kolya = new Person("Коля");
//        Person mama = new Person("Мама");
//
//        Card pashaCard = new Card(pasha, "Совкомбанк", "29.12.2018");
//        pasha.addCard(pashaCard);
//        Card lenaCard = new Card(lenka, "Совкомбанк", "23.03.2019");
//        lenka.addCard(lenaCard);
//        Card kolyaCard1 = new Card(kolya, "Совкомбанк", "22.11.2019");
//        lenka.addCard(kolyaCard1);
//        Card kolyaCard2 = new Card(kolya, "Хоум кредит", "02.02.2020");
//        lenka.addCard(kolyaCard2);
//
//        Deposit first = new Deposit(pashaCard, "27.01.2020", 7.1, pasha);
//        first.addIncome(new Income(323000, "30.01.2020", first));
//        first.addIncome(new Income(100000, "27.02.2020", first));
//        first.addIncome(new Income(100000, "27.02.2020", first, mama));
//        pashaCard.addDeposit(first);
//
//        Deposit second = new Deposit(pashaCard, "27.02.2020", 6.6, pasha);
//        second.addIncome(new Income(500000, "27.02.2020", second));
//        pashaCard.addDeposit(second);
//
//        Deposit third = new Deposit(lenaCard, "22.03.2019", 8.8, lenka);
//        third.addIncome(new Income(700000, "22.03.2019", third));
//        lenaCard.addDeposit(third);

        List<Card> cards = new ArrayList<>();
//        cards.add(pashaCard); //new CardView(pashaCard, service));
//        cards.add(lenaCard);
////        clients.add(kolya);
////        clients.add(mama);
        List<Deposit> deps = new ArrayList<>();
//        deps.add(first);
//        deps.add(second);
//        deps.add(third);
    }
}
