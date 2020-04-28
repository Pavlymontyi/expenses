package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.CardRepo;
import com.kraynov.expenses.dao.CategoryRepo;
import com.kraynov.expenses.dao.ExpenseRepo;
import com.kraynov.expenses.domain.Category;
import com.kraynov.expenses.domain.Expense;
import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepo expenseRepo;
    private final CategoryRepo categoryRepo;
    private final CardRepo cardRepo;

    public ExpenseService(ExpenseRepo expenseRepo, CategoryRepo categoryRepo, CardRepo cardRepo) {
        this.expenseRepo = expenseRepo;
        this.categoryRepo = categoryRepo;
        this.cardRepo = cardRepo;
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

    //todo:
    public Iterable<Category> getAllCategoriesSorted() {
        return categoryRepo.findAll();
    }

    @Transactional
    public void addExpenseToCard(Card card, double amount, Date expenseDate, Category category, String description) throws BusinessException {
        if (card.getBalance() < amount) throw new BusinessException("Card doesn't have sufficient money amount to add expense");
        Expense expense = new Expense(card, amount, expenseDate, category);
        expense.setDescription(description);
        card.setBalance(card.getBalance() - (int) amount);
        expenseRepo.save(expense);
    }

    public List<Expense> getExpensesByCard(Card card) {
        //todo: добавить вычисление за последний месяц, начиная с нижней даты (чтобы включить траты когда их нельзя было делать)
        return expenseRepo.findByCardId(card.getId());
    }
}
