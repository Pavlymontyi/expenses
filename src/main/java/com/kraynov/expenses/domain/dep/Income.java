package com.kraynov.expenses.domain.dep;

import lombok.Data;

import java.util.Objects;

@Data
public class Income {

    int value;
    String date;
    Deposit deposit;
    Person owner;

    public Income(int value, String incomeDate, Deposit deposit) {
        this(value, incomeDate, deposit, deposit.getMoneyOwner());
    }

    public Income(int value, String incomeDate, Deposit deposit, Person owner) {
        this.value = value;
        this.date = incomeDate;
        this.deposit = deposit;
        this.owner = owner;
    }

    //todo: добавить учет даты
    public double getRevenue() {
        return deposit.percent/100 * this.value;
    }

    //todo: добавить учет даты
    public String getRevenueClarification() {

        return value+"*"+(deposit.percent/100)+"*"+"365"+"/"+"365";
    }

    public double calculateTotal() {
        return value+getRevenue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return value == income.value &&
                date.equals(income.date) &&
                deposit.equals(income.deposit) &&
                owner.equals(income.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, date, deposit, owner);
    }
}
