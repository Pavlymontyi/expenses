package com.kraynov.expenses.domain.dep;

import com.kraynov.expenses.domain.dep.card.CardImpl;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Person {

    private String name;
    private List<CardImpl> cards;

    public Person(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    public void addCard(CardImpl card) {
        cards.add(card);
    }
}
