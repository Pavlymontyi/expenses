package com.kraynov.expenses.dao;

import com.kraynov.expenses.domain.dep.Deposit;
import org.springframework.data.repository.CrudRepository;

public interface DepositRepo extends CrudRepository<Deposit, Long> {
}
