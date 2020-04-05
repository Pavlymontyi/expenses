package com.kraynov.expenses.dao;

import com.kraynov.expenses.domain.dep.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepo extends CrudRepository<Card, Long> {
}
