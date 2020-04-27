package com.kraynov.expenses.domain.dep;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="cards")
@Setter
@Getter
@ToString
public class Card {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE")
    @SequenceGenerator(name="SEQUENCE", initialValue=100, allocationSize=25)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;
    @Column(nullable = false)
    private String bank;
    @Column(nullable = false)
    private Date openDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card")
    @SortComparator(DepositComparator.class)
    private Set<Deposit> deposits;
    @Column(columnDefinition = "integer default 0")
    private Integer balance;

    public Card() {
    }

    public Card(Person owner, String bank, Date openDate) {
        this.owner = owner;
        this.bank = bank;
        this.openDate = openDate;
    }

    public static class DepositComparator implements Comparator<Deposit>{

        @Override
        public int compare(Deposit left, Deposit right) {
            int res = left.getStartDate().compareTo(right.getStartDate());
            return res == 0 ? left.getId().compareTo(right.getId()) : res;
        }
    }

}
