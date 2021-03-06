package com.kraynov.expenses.controller;

import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.service.CardService;
import com.kraynov.expenses.service.DepositService;
import com.kraynov.expenses.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping({"/", "/index"})
public class MainController {
    public static final String INDEX_PAGE_URL = "index";
    public static final String INDEX_URL_REDIRECTION = "redirect:/" + INDEX_PAGE_URL;

    private final PersonService personService;
    private final CardService cardService;
    private final DepositService depositService;

    public MainController(PersonService personService, CardService cardService, DepositService depositService) {
        this.personService = personService;
        this.cardService = cardService;
        this.depositService = depositService;
    }

    @GetMapping
    public String index(Map<String, Object> model) {
        Iterable<Person> persons = personService.getAll();
        Map<Integer, List<Person>> groupAndPersons = personService.getDepositInfoForAll();

        //todo: вынести в сервисы
        Map<Long, Integer> cardsTotal = StreamSupport.stream(cardService.getAllCards().spliterator(), false)
                .collect(Collectors.toMap(Card::getId, cardService::getTotal));
        Map<Long, Integer> personToMoneyTotal = personService.getDepositMoneyTotal(persons);
        Map<Long, Integer> personToCardBalance = personService.getCardsBalanceTotal(persons);
        Map<Long, Integer> depositFreeSpaces = depositService.calculateFreeSpace(depositService.getActiveDeposits());

        model.put("groupAndPersons", groupAndPersons);
        model.put("cardsTotal", cardsTotal);
        model.put("personToMoneyTotal", personToMoneyTotal);
        model.put("personToCardsBalanceTotal", personToCardBalance);
        model.put("depositFreeSpaces", depositFreeSpaces);

        return INDEX_PAGE_URL;
    }
}
