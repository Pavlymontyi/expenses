package com.kraynov.expenses.dao;

import com.kraynov.expenses.domain.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends CrudRepository<Expense, Long> {

    List<Expense> findByCardId(Long id);
}
