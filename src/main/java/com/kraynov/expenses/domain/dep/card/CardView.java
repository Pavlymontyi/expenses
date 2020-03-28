package com.kraynov.expenses.domain.dep.card;

import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.service.DepositService;

import java.util.List;

public class CardView implements Card {
    private Card card;
    //todo: переделать на использование спринга для создания этого объекта
    private DepositService service;

    public CardView(Card card, DepositService service) {
        this.card = card;
        this.service = service;
    }

    @Override
    public double getTotal() {
       return service.calculateCurrentTotal(card);
    }

    public double getRevenue() {
        return service.calculateExpectedRevenue(card);
    }

    public double getFreeSpace() {
        return service.calculateFreeSpace(card);
    }

    @Override
    public Person getOwner() {
        return card.getOwner();
    }

    @Override
    public String getBank() {
        return card.getBank();
    }

    @Override
    public String getOpenDate() {
        return card.getOpenDate();
    }

    @Override
    public List<Deposit> getDeposits() {
        return card.getDeposits();
    }

    @Override
    public void addDeposit(Deposit deposit) {
        throw new UnsupportedOperationException();
    }

    public String getShortInfo() {
        return String.format("%s %s от %s", getOwner().getName(), getBank(), getOpenDate());
    }

    public String getTotalInfo() {
        return String.format("Сумма(проценты): %.1f (%.1f). Осталось места: %.1f",
                getTotal()/1000,
                getRevenue()/1000,
                getFreeSpace()/1000);
    }
}
