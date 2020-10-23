package com.kraynov.expenses.domain.dep;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "incomes")
@Setter
@Getter
@ToString
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_SEQUENCE")
    @SequenceGenerator(name="EXPENSE_SEQUENCE", initialValue=100, allocationSize=25)
    private Long id;

    @Column(name="amount")
    private int value;
    @Column(name="revenue")
    private Integer revenue; //фактические проценты
    private Date date;
    @ManyToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    public Income() {
    }

    public Income(int value, Date date, Deposit deposit, Person owner) {
        this.value = value;
        this.date = date;
        this.deposit = deposit;
        this.owner = owner;
    }

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
