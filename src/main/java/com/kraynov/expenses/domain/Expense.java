package com.kraynov.expenses.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Data
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
}
