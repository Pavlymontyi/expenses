package com.kraynov.expenses.dao;

import com.kraynov.expenses.domain.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepo extends CrudRepository<Expense, Long> {


}
