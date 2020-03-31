package com.kraynov.expenses.dao;

import com.kraynov.expenses.domain.dep.Income;
import org.springframework.data.repository.CrudRepository;

public interface IncomeRepo extends CrudRepository<Income, Long> {
}
