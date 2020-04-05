package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.CategoryRepo;
import com.kraynov.expenses.dao.ExpenseRepo;
import com.kraynov.expenses.domain.Category;
import com.kraynov.expenses.domain.Expense;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final ExpenseRepo expenseRepo;
    private final CategoryRepo categoryRepo;

    public ExpenseService(ExpenseRepo expenseRepo, CategoryRepo categoryRepo) {
        this.expenseRepo = expenseRepo;
        this.categoryRepo = categoryRepo;
    }

    public Expense getExpenseById(Long id) {
        return expenseRepo.findById(id).orElse(null);
    }

    public Iterable<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    public Expense saveExpense(Expense expense) {
        return expenseRepo.save(expense);
    }

    public void deleteExpense(Expense expense) {
        expenseRepo.delete(expense);
    }

    public Category getExpenseCategoryById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }

    public Iterable<Category> getAllCategories() {
        return categoryRepo.findAll();
    }
}
