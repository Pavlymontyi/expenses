package com.kraynov.expenses.controller;

import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.errorhandling.BusinessException;
import com.kraynov.expenses.service.CardService;
import com.kraynov.expenses.service.DepositService;
import com.kraynov.expenses.service.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/deposit")
public class DepositController {
    private static final String CARD_ID_PARAM_NAME = "cardId";
    private static final String DEPOSIT_PERCENT_PARAM_NAME = "percent";
    private static final String DEPOSIT_OPEN_DATE_PARAM_NAME = "depositOpenDate";
    private static final String DEPOSIT_DURATION_PARAM_NAME = "duration";
    private static final String DEPOSIT_INIT_AMOUNT_PARAM_NAME = "amount";
    private static final String DEPOSIT_REFILLABLE_PARAM_NAME = "refillable";

    private static final String DEPOSIT_ID_AMOUNT_PARAM_NAME = "depositId";
    private static final String DEPOSIT_REFILL_AMOUNT_PARAM_NAME = "refillAmount";
    private static final String DEPOSIT_REFILL_DATE_PARAM_NAME = "refillDate";
    private static final String DEPOSIT_CARD_PARAM_NAME = "cardSelector";

    private static final String DEPOSIT_PERCENT_AMOUNT_PARAM_NAME = "percentAmount";


    private final DepositService depositService;
    private final CardService cardService;

    public DepositController(DepositService depositService, CardService cardService) {
        this.depositService = depositService;
        this.cardService = cardService;
    }

    @GetMapping("/open")
    public String getOpenDepositScreen(@RequestParam Long cardId, Map<String, Object> model) throws BusinessException {
        model.put("card", cardService.getCardById(cardId));
        return "/deposit/add.html";
    }

    @PostMapping("/open")
    public String openNewDeposit(@RequestParam Map<String, String> params) throws BusinessException, ParseException {
        Card card = cardService.getCardById(Long.valueOf(params.get(CARD_ID_PARAM_NAME)));
        double percent = Double.valueOf(params.get(DEPOSIT_PERCENT_PARAM_NAME));
        Date openDate = Utils.dateInputFormatter.parse(params.get(DEPOSIT_OPEN_DATE_PARAM_NAME));
        int duration = Integer.valueOf(params.get(DEPOSIT_DURATION_PARAM_NAME));
        int initialAmount = Integer.valueOf(params.get(DEPOSIT_INIT_AMOUNT_PARAM_NAME));
        boolean refillable = "on".equals(params.get(DEPOSIT_REFILLABLE_PARAM_NAME));

        depositService.openNewDeposit(card, percent, openDate, duration, initialAmount, refillable);
        return MainController.INDEX_URL_REDIRECTION;
    }

    @GetMapping("/refill")
    public String getRefillDepositScreen(@RequestParam Long depositId, Map<String, Object> model) throws BusinessException {
        Deposit deposit = depositService.getDepositById(depositId);
        model.put("deposit", deposit);
        return "/deposit/addMoney.html";
    }

    @PostMapping("/refill")
    public String refillDeposit(@RequestParam Map<String, String> params) throws BusinessException, ParseException {
        Deposit deposit = depositService.getDepositById(Long.valueOf(params.get(DEPOSIT_ID_AMOUNT_PARAM_NAME)));
        int refillAmount = Integer.valueOf(params.get(DEPOSIT_REFILL_AMOUNT_PARAM_NAME));
        Date refillDate = Utils.dateInputFormatter.parse(params.get(DEPOSIT_REFILL_DATE_PARAM_NAME));
        String cardIdParam = params.get(DEPOSIT_CARD_PARAM_NAME);
        Card card = StringUtils.isEmpty(cardIdParam) ? deposit.getCard() : cardService.getCardById(Long.valueOf(cardIdParam));

        depositService.refillDeposit(deposit, card, refillDate, refillAmount);
        return MainController.INDEX_URL_REDIRECTION;
    }

    @GetMapping("/close")
    public String getCloseDepositScreen(@RequestParam Long depositId, Map<String, Object> model) throws BusinessException {
        Deposit deposit = depositService.getDepositById(depositId);
        model.put("deposit", deposit);
        //todo: добавить таблицу долгов? до того допускаем что если деньги на закрываемом вкладе принадлежат разным людям, то вручную эту ситуацию разруливаем
        return "/deposit/close";
    }

    @PostMapping("/close")
    public String closeDeposit(@RequestParam Map<String, String> params) throws BusinessException {
        Deposit deposit = depositService.getDepositById(Long.valueOf(params.get(DEPOSIT_ID_AMOUNT_PARAM_NAME)));
        Integer amount = Integer.valueOf(params.get(DEPOSIT_PERCENT_AMOUNT_PARAM_NAME));
        depositService.closeDeposit(deposit, amount);
        return MainController.INDEX_URL_REDIRECTION;
    }

    @GetMapping("/delete")
    public String getCloseDepositScreen(@RequestParam Long depositId) throws BusinessException {
        Deposit deposit = depositService.getDepositById(depositId);
        depositService.delete(deposit);
        return MainController.INDEX_URL_REDIRECTION;
    }

    @GetMapping("/edit")
    public String getEditScreen(@RequestParam Long depositId, Map<String, Object> model) throws BusinessException {
        model.put("deposit", depositService.getDepositById(depositId));
        return "/deposit/edit";
    }

    @PostMapping("/edit")
    public String editDepositInfo(@RequestParam Map<String, String> params) throws BusinessException, ParseException {
        Long depositId = Long.valueOf(params.get(DEPOSIT_ID_AMOUNT_PARAM_NAME));
        double percent = Double.valueOf(params.get(DEPOSIT_PERCENT_PARAM_NAME));
        Date openDate = Utils.dateInputFormatter.parse(params.get(DEPOSIT_OPEN_DATE_PARAM_NAME));
        int duration = Integer.valueOf(params.get(DEPOSIT_DURATION_PARAM_NAME));
        boolean refillable = "on".equals(params.get(DEPOSIT_REFILLABLE_PARAM_NAME));

        depositService.editDepositInfo(depositId, percent, openDate, duration, refillable);
        return MainController.INDEX_URL_REDIRECTION;
    }
}
