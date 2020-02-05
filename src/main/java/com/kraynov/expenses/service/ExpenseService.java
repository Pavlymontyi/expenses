package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.ExpenseRepo;
import com.kraynov.expenses.domain.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepo;

    public Expense getById(Long id) {
        return expenseRepo.findById(id).orElse(null);
    }

    public Iterable<Expense> getAll() {
        return expenseRepo.findAll();
    }

    public Expense save(Expense expense) {
        return expenseRepo.save(expense);
    }


}
