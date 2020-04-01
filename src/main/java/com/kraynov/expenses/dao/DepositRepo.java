package com.kraynov.expenses.dao;

import com.kraynov.expenses.domain.dep.Deposit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepositRepo extends CrudRepository<Deposit, Long> {

    List<Deposit> findByActiveTrue();
}
