package com.kraynov.expenses.domain.dep;

import com.kraynov.expenses.domain.dep.card.CardImpl;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Deposit {

    CardImpl card;
    Client moneyOwner;
    double percent;
    String startDate;
    List<Income> incomes = new ArrayList<>();

    public Deposit(CardImpl card, String startDate, double percent) {
        this.card = card;
        this.startDate = startDate;
        this.percent = percent;
    }

    public Deposit(CardImpl card, String startDate, double percent, Client moneyOwner) {
        this(card, startDate, percent);
        this.moneyOwner = moneyOwner;
    }

    public void addIncome(Income income) {
        incomes.add(income);
    }

//    public double calculateTotalWithoutRevenue() {
//        double sum = 0;
//        for (Income income : incomes) {
//            sum += income.value;
//        }
//        return sum;
//    }

//    public double calculateFreeSpace() {
//        return 0;
//    }

    public String getInfo() {
        return new StringBuilder()
                .append(card.getOwner().getName())
                .append(", ")
                .append(percent)
                .append("%, ")
                .append(startDate)
//                .append(", Сумма: ")
//                .append(calculateTotalWithoutRevenue())
                .toString();
    }
}
