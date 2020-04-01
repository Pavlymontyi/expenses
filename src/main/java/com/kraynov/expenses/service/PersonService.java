package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.PersonRepo;
import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    //todo: переписать нафиг, голова не варит совсем
    public Map<Long, Integer> getPersonsTotal(Iterable<Person> persons) {
        Map<Long, Integer> personsTotal = new HashMap<>();
        for (Person person : persons) {
            personsTotal.put(person.getId(), 0);
        }

        for (Person person : persons) {
            for (Card card : person.getCards()) {
                for (Deposit deposit : card.getDeposits()) {
                    if (deposit.getActive()) {
                        for (Income income : deposit.getIncomes()) {
                            personsTotal.put(income.getOwner().getId(), personsTotal.get(income.getOwner().getId()) + income.getValue());
                        }
                    }
                }
            }
        }

        return personsTotal;
    }
}
