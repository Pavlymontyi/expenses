package com.kraynov.expenses.service;

import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.domain.dep.card.Card;
import org.springframework.stereotype.Service;

@Service
public class DepositService {

    /**
     * Суммирует деньги, присутствующие на данный момент на карте
     * @param card карта
     * @return сумму на карте
     */
    public double calculateCurrentTotal(Card card) {
        return card.getDeposits()
                .stream()
                .flatMap(t -> t.getIncomes().stream())
                .map(Income::getValue)
                .reduce(0, Integer::sum);
    }

    /**
     * Суммирует ожидаемые доходы от процентов по всем активным вкладам
     * @param card карта
     * @return сумму доходов по текущим вкладам карты
     */
    public double calculateExpectedRevenue(Card card) {
        return card.getDeposits()
                .stream()
                .flatMap(t -> t.getIncomes().stream())
                .map(Income::getRevenue)
                .reduce((double) 0, Double::sum);
    }

//    public double calculateTotal(Card card, boolean withRevenue) {
//        return card.getDeposits()
//                .stream()
//                .flatMap(t -> t.getIncomes().stream())
//                .map(income -> withRevenue ? income.getValue()+income.getRevenue() : income.getValue())
//                .reduce((double) 0, Double::sum);
//    }

    public double calculateFreeSpace(Card card) {
        return 1_400_000 - calculateExpectedRevenue(card) - calculateCurrentTotal(card);
    }
}
