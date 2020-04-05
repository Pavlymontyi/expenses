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
@Table(name="deposits")
@Setter
@Getter
@ToString
public class Deposit {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE")
    @SequenceGenerator(name="SEQUENCE", initialValue=100, allocationSize=25)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    private Boolean active;
    private double percent;
    private Date startDate;
    private Date endDate;
    private int duration;
    private int revenue;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deposit")
    @SortComparator(IncomeComparator.class)
    private Set<Income> incomes;

    public static class IncomeComparator implements Comparator<Income> {

        @Override
        public int compare(Income o1, Income o2) {
            int res = o1.getDate().compareTo(o2.getDate());
            return res == 0 ? o1.getId().compareTo(o2.getId()) : res;
        }
    }

    public Deposit() {
    }

    public Deposit(Card card, double percent, Date startDate, int duration) {
        this.card = card;
        this.percent = percent;
        this.startDate = startDate;
        this.duration = duration;
        this.active = true;
        this.endDate = new Date(startDate.getTime() + ((long) duration * 1000 * 60 * 60 * 24));
        System.out.println(endDate);
    }

//
//    public String getInfo() {
//        return new StringBuilder()
//                .append(card.getOwner().getName())
//                .append(", ")
//                .append(percent)
//                .append("%, ")
//                .append(startDate)
////                .append(", Сумма: ")
////                .append(calculateTotalWithoutRevenue())
//                .toString();
//    }
}
