package com.kraynov.expenses.controller;

import com.kraynov.expenses.domain.Category;
import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.errorhandling.BusinessException;
import com.kraynov.expenses.service.CardService;
import com.kraynov.expenses.service.ExpenseService;
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
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CardService cardService;

    public ExpenseController(ExpenseService expenseService, CardService cardService) {
        this.expenseService = expenseService;
        this.cardService = cardService;
    }

//    @GetMapping
//    @ResponseBody
//    public Iterable<Expense> getExpenses() {
//        System.out.println("getExpenses start");
//        return expenseRepo.findAll();
//    }

    @GetMapping("/add")
    public String getAddExpenseScreen(@RequestParam Long cardId, Map<String, Object> model) throws BusinessException {
        model.put("categories", expenseService.getAllCategoriesSorted());
        model.put("card", cardService.getCardById(cardId));
        return "expenses/add";
    }

    @PostMapping("/add")
    public String addExpense(@RequestParam Map<String, String> params) throws BusinessException, ParseException {
        String cardId = params.get("cardId");
        Card card = cardService.getCardById(Long.valueOf(cardId));
        double amount = Double.valueOf(params.get("amount"));
        Date date = Utils.dateInputFormatter.parse(params.get("expenseDate"));
        String categoryId = params.get("categorySelector");
        Category category = StringUtils.isEmpty(categoryId) ? null : expenseService.getExpenseCategoryById(Long.valueOf(categoryId));
        String description = params.get("description");

        expenseService.addExpenseToCard(card, amount, date, category, description);
        return "redirect:/card/view?cardId="+cardId;
    }

    //    @GetMapping
//    @ResponseBody
//    public ModelAndView getExpenses1() {
//        System.out.println("getExpenses start2");
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("index");
//        mv.getModel().put("data", "Welcome home man");
//
//        return mv;
//        //return expenseRepo.findAll();
//    }
}
