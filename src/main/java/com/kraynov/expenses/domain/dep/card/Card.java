package com.kraynov.expenses.domain.dep.card;

import com.kraynov.expenses.domain.dep.Client;
import com.kraynov.expenses.domain.dep.Deposit;

import java.util.List;

public interface Card {

    Client getOwner();
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
