package com.kraynov.expenses.domain.dep;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="persons")
@Setter
@Getter
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private int groupId;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @SortComparator(CardComparator.class)
    private Set<Card> cards;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
        cards = new HashSet<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public static class CardComparator implements Comparator<Card> {

        @Override
        public int compare(Card o1, Card o2) {
            int res =o1.getOpenDate().compareTo(o2.getOpenDate());
            return res == 0 ? o1.getId().compareTo(o2.getId()) : res;
        }
    }
}
