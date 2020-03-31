package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.PersonRepo;
import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public Iterable<Person> getDepositInfoForAll() {
        return personRepo.findAll();
    }

    public Person getPersonById(Long id) throws BusinessException {
        return personRepo.findById(id).orElseThrow(() -> new BusinessException("Person was not found by id=" + id));
    }
}
