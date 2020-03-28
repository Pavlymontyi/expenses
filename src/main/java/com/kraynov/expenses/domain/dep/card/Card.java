package com.kraynov.expenses.domain.dep.card;

import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.domain.dep.Deposit;

import java.util.List;

public interface Card {

    Person getOwner();
    String getBank();
    String getOpenDate();
    List<Deposit> getDeposits();
    double getTotal();
    double getRevenue();

    /**
     * Добавить вклад на карту
     * @param deposit вклад
     */
    void addDeposit(Deposit deposit);
}
