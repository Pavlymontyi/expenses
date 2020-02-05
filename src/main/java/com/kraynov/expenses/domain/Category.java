package com.kraynov.expenses.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Category {

    @Id
    Long id;

    @NotNull
    String name;

    public Category() {
    }
}
