package com.kraynov.expenses.controller;

import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.errorhandling.BusinessException;
import com.kraynov.expenses.service.CardService;
import com.kraynov.expenses.service.DepositService;
import com.kraynov.expenses.service.ExpenseService;
import com.kraynov.expenses.service.PersonService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

import static com.kraynov.expenses.controller.MainController.INDEX_URL_REDIRECTION;

@Controller
@RequestMapping("/card")
public class CardController {
    private final PersonService personService;
    private final CardService cardService;
    private final DepositService depositService;
    private final ExpenseService expenseService;

    public CardController(PersonService personService, CardService cardService, DepositService depositService, ExpenseService expenseService) {
        this.personService = personService;
        this.cardService = cardService;
        this.depositService = depositService;
        this.expenseService = expenseService;
    }

    @GetMapping("/add")
    public String getOpenCardScreen(@RequestParam Long personId, Map<String, Object> model) throws BusinessException {
        model.put("person", personService.getPersonById(personId));
        return "/card/add.html";
    }

    @PostMapping("/add")
    public String openNewCard(@RequestParam Long personId,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date cardOpenDate,
                              @RequestParam String bankName,
                              @RequestParam Integer balance) throws BusinessException {
        Person person = personService.getPersonById(personId);
        cardService.openNewCard(person, cardOpenDate, bankName, balance);
        return INDEX_URL_REDIRECTION;
    }

    @GetMapping("/delete")
    public String deleteCard(@RequestParam Long cardId) throws BusinessException {
        Card card = cardService.getCardById(cardId);
        card.getDeposits().forEach(depositService::delete);
        cardService.deleteCard(cardId);
        return INDEX_URL_REDIRECTION;
    }

    @GetMapping("/close")
    public String closeCard(@RequestParam Long cardId) throws BusinessException {
        Card card = cardService.getCardById(cardId);
        cardService.closeCard(card);
        return INDEX_URL_REDIRECTION;
    }

    @GetMapping("/addMoney")
    public String addMoneyToCard(@RequestParam Long cardId, Map<String, Object> model) throws BusinessException {
        model.put("card", cardService.getCardById(cardId));
        return "/card/addMoney.html";
    }

    @PostMapping("/addMoney")
    public String addMoneyToCard(@RequestParam Long cardId,
                                 @RequestParam(defaultValue = "0") Integer newMoney) throws BusinessException {
        cardService.addMoneyToCard(cardId, newMoney);
        return INDEX_URL_REDIRECTION;
    }

    @GetMapping("/view")
    public String view(@RequestParam Long cardId, Map<String, Object> model) throws BusinessException {
        Card card = cardService.getCardById(cardId);
        model.put("card", card);
        model.put("expenses", expenseService.getExpensesByCard(card));
        return "/card/view.html";
    }
}
