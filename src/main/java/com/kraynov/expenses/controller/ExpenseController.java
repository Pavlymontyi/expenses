package com.kraynov.expenses.controller;

import com.kraynov.expenses.dao.ExpenseRepo;
import com.kraynov.expenses.domain.Expense;
import com.kraynov.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

//    @GetMapping
//    @ResponseBody
//    public Iterable<Expense> getExpenses() {
//        System.out.println("getExpenses start");
//        return expenseRepo.findAll();
//    }

    @GetMapping
    public String index(Map<String, Object> model) {
        model.put("expenses", expenseService.getAll());
        Expense expense = new Expense(0);
        //expense.setExpenseDate(new Date());
        model.put("expense", expense);
        return "index";
    }

    @PostMapping("/add")
    public String addExpense(@ModelAttribute @Valid Expense expense,
                             @RequestParam String categoryId,
                             BindingResult bindingResult) {
        System.out.println(categoryId);
        if (!bindingResult.hasErrors()) {
            expenseService.save(expense);
        } else {
            System.out.println("Incorrected data: "+bindingResult.getAllErrors());
        }
        return "redirect:/expenses";
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
