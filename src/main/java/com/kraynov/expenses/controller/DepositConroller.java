package com.kraynov.expenses.controller;

import com.kraynov.expenses.domain.dep.card.CardImpl;
import com.kraynov.expenses.domain.dep.Client;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.service.DepositService;
import com.kraynov.expenses.domain.dep.card.CardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/deposites")
public class DepositConroller {

    @Autowired
    DepositService service;

    @GetMapping
    public String index(Map<String, Object> model) {
        Client pasha = new Client("Паша");
        Client lenka = new Client("Лена");
        Client kolya = new Client("Коля");
        Client mama = new Client("Мама");

        CardImpl pashaCardImpl = new CardImpl(pasha, "Совкомбанк", "29.12.2018");
        pasha.addCard(pashaCardImpl);
        CardImpl lenaCardImpl  = new CardImpl(lenka, "Совкомбанк", "23.03.2019");
        lenka.addCard(lenaCardImpl);
        CardImpl kolyaCard1 = new CardImpl(kolya, "Совкомбанк", "22.11.2019");
        lenka.addCard(kolyaCard1);
        CardImpl kolyaCard2 = new CardImpl(kolya, "Хоум кредит", "02.02.2020");
        lenka.addCard(kolyaCard2);

        Deposit first = new Deposit(pashaCardImpl, "27.01.2020", 7.1, pasha);
        first.addIncome(new Income(323000, "30.01.2020", first));
        first.addIncome(new Income(100000, "27.02.2020", first));
        first.addIncome(new Income(100000, "27.02.2020", first, mama));
        pashaCardImpl.addDeposit(first);

        Deposit second = new Deposit(pashaCardImpl, "27.02.2020", 6.6);
        second.addIncome(new Income(500000, "27.02.2020", second));
        pashaCardImpl.addDeposit(second);

        Deposit third = new Deposit(lenaCardImpl, "22.03.2019", 8.8);
        third.addIncome(new Income(700000, "22.03.2019", third));
        lenaCardImpl.addDeposit(third);

        List<CardView> cards = new ArrayList<>();
        cards.add(new CardView(pashaCardImpl, service));
        cards.add(new CardView(lenaCardImpl, service));
////        clients.add(kolya);
////        clients.add(mama);
        List<Deposit> deps = new ArrayList<>();
        deps.add(first);
        deps.add(second);
        deps.add(third);

        model.put("cards", cards);
        model.put("deposits", deps);

        return "deposits";
    }
}
