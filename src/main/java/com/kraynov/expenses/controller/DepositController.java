package com.kraynov.expenses.controller;

import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.errorhandling.BusinessException;
import com.kraynov.expenses.service.DepositService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/deposit")
public class DepositController {
    private static final String CARD_ID_PARAM_NAME = "cardId";
    private static final String DEPOSIT_PERCENT_PARAM_NAME = "percent";
    private static final String DEPOSIT_OPEN_DATE_PARAM_NAME = "depositOpenDate";
    private static final String DEPOSIT_DURATION_PARAM_NAME = "duration";
    private static final String DEPOSIT_INIT_AMOUNT_PARAM_NAME = "amount";

    private final DepositService service;

    public DepositController(DepositService service) {
        this.service = service;
    }

    @GetMapping("/open")
    public String getOpenDepositScreen(@RequestParam Long cardId, Map<String, Object> model) {
        model.put("card", service.getCardById(cardId));
        return "/deposit/addDeposit.html";
    }

    @PostMapping("/open")
    public String openNewDeposit(@RequestParam Map<String, String> params) throws BusinessException {
        System.out.println("asdasdas = "+params);
        Card card = service.getCardById(Long.valueOf(params.get(CARD_ID_PARAM_NAME)));
        double percent = Double.valueOf(params.get(DEPOSIT_PERCENT_PARAM_NAME));
        String openDate = params.get(DEPOSIT_OPEN_DATE_PARAM_NAME);
        int duration = Integer.valueOf(params.get(DEPOSIT_DURATION_PARAM_NAME));
        int initialAmount = Integer.valueOf(params.get(DEPOSIT_INIT_AMOUNT_PARAM_NAME));

        service.openNewDeposit(card, percent, openDate, duration, initialAmount);
        return MainController.INDEX_URL_REDIRECTION;
    }
}
