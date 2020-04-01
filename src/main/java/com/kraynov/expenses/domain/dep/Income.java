package com.kraynov.expenses.domain.dep;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="incomes")
@Setter
@Getter
@ToString
public class Income {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE")
    @SequenceGenerator(name="SEQUENCE", initialValue=100, allocationSize=25)
    private Long id;

    private int value;
    private Date date;
    @ManyToOne @JoinColumn(name = "deposit_id")
    private Deposit deposit;
    @ManyToOne @JoinColumn(name = "person_id")
    private Person owner;

    public Income() {
    }

    public Income(int value, Date date, Deposit deposit, Person owner) {
        this.value = value;
        this.date = date;
        this.deposit = deposit;
        this.owner = owner;
    }

    //
//    public Income(int value, String incomeDate, Deposit deposit) {
//        this(value, incomeDate, deposit, deposit.getMoneyOwner());
//    }
//
//    public Income(int value, String incomeDate, Deposit deposit, Person owner) {
//        this.value = value;
//        this.date = incomeDate;
//        this.deposit = deposit;
//        this.owner = owner;
//    }
//
//    //todo: добавить учет даты
//    public double getRevenue() {
//        return deposit.percent/100 * this.value;
//    }
//
//    //todo: добавить учет даты
//    public String getRevenueClarification() {
//
//        return value+"*"+(deposit.percent/100)+"*"+"365"+"/"+"365";
//    }
//
//    public double calculateTotal() {
//        return value+getRevenue();
//    }
//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return id.equals(income.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
