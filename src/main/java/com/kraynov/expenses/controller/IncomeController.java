package com.kraynov.expenses.controller;

import com.kraynov.expenses.errorhandling.BusinessException;
import com.kraynov.expenses.service.IncomeService;
import com.kraynov.expenses.service.PersonService;
import com.kraynov.expenses.service.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/income")
public class IncomeController {
    private final IncomeService incomeService;
    private final PersonService personService;

    public IncomeController(IncomeService incomeService, PersonService personService) {
        this.incomeService = incomeService;
        this.personService = personService;
    }

    @GetMapping("/edit")
    public String getEditIncomeScreen(@RequestParam Long incomeId, Map<String, Object> model) throws BusinessException {
        model.put("income", incomeService.getById(incomeId));
        model.put("persons", personService.getAll());
        return "income/edit";
    }

    @PostMapping("/edit")
    public String editIncome(@RequestParam Map<String, String> params) throws ParseException, BusinessException {
        Long incomeId = Long.valueOf(params.get("incomeId"));
        Date refillDate = Utils.dateInputFormatter.parse(params.get("incomeDate"));
        int amount = Integer.valueOf(params.get("incomeAmount"));
        Long ownerId = Long.valueOf(params.get("personSelector"));
        incomeService.changeIncomeInfo(incomeId, refillDate, amount, personService.getPersonById(ownerId));
        return MainController.INDEX_URL_REDIRECTION;
    }

}
