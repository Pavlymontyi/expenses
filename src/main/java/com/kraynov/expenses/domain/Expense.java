package com.kraynov.expenses.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="expenses")
@Setter
@Getter
@ToString
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    @NotNull
    @Positive
    private double amount;
    private Date expenseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    public Expense() {
    }

    public Expense(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(expense.amount, amount) == 0 &&
                Objects.equals(id, expense.id) &&
                Objects.equals(description, expense.description) &&
                Objects.equals(expenseDate, expense.expenseDate) &&
                Objects.equals(category, expense.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, amount, expenseDate, category);
    }
}
