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

    /**
     * Подсчитывает сумму по дс, заложенным на вклады, в том числе на чужих вкладах.
     * @param persons клиенты, для которых нужно вычислить сумму вложенных дс
     * @return мапу, где ключом является id клиента, а значением сумма дс на вкладах.
     */
    public Map<Long, Integer> getDepositMoneyTotal(Iterable<Person> persons) {
        //todo: переписать нафиг, голова не варит совсем
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

    /**
     * Подсчитывает сумму остатков по картам.
     * @param persons клиенты, для которых нужно вычислить сумму остатков по картам
     * @return мапу, где ключом является id клиента, а значением сумму остатков по картам.
     */
    public Map<Long, Integer> getCardsBalanceTotal(Iterable<Person> persons) {
        //todo: переписать на java8
        Map<Long, Integer> result = new HashMap<>();

        for (Person person : persons) {
            for (Card card : person.getCards()) {
                result.put(person.getId(), result.getOrDefault(person.getId(), 0) + card.getBalance());
            }
        }

        return result;
    }
}
