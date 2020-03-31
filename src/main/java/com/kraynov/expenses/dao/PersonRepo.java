package com.kraynov.expenses.dao;

import com.kraynov.expenses.domain.dep.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepo extends CrudRepository<Person, Long> {
}
