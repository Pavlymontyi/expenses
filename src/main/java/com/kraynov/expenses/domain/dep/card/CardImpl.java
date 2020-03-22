package com.kraynov.expenses.domain.dep.card;

import com.kraynov.expenses.domain.dep.Client;
import com.kraynov.expenses.domain.dep.Deposit;

import java.util.ArrayList;
import java.util.List;

public class CardImpl implements Card {

    private Client owner;
    private String bank;
    private String openDate;
    private List<Deposit> deposits = new ArrayList<>();

    public CardImpl(Client owner, String bankName, String openDate) {
        this.owner = owner;
        this.bank = bankName;
        this.openDate = openDate;
    }

    @Override
    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    public Client getOwner() {
        return owner;
    }

    public String getBank() {
        return bank;
    }

    public String getOpenDate() {
        return openDate;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    @Override
    public double getTotal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getRevenue() {
        throw new UnsupportedOperationException();
    }
}
