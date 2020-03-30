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
@Table(name="deposits")
@Setter
@Getter
@ToString
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    private double percent;
    private String startDate;
    private int duration;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deposit")
    @SortComparator(IncomeComparator.class)
    private Set<Income> incomes = new HashSet<>();

    public static class IncomeComparator implements Comparator<Income> {

        @Override
        public int compare(Income o1, Income o2) {
            int res = o1.getDate().compareTo(o2.getDate());
            return res == 0 ? o1.getId().compareTo(o2.getId()) : res;
        }
    }

    public Deposit() {
    }


//
//    public Deposit(Card card, String startDate, double percent) {
//        this.card = card;
//        this.startDate = startDate;
//        this.percent = percent;
//    }
//
//    public Deposit(Card card, String startDate, double percent, Person moneyOwner) {
//        this(card, startDate, percent);
//        this.moneyOwner = moneyOwner;
//    }
//
//    public void addIncome(Income income) {
//        incomes.add(income);
//    }
//
////    public double calculateTotalWithoutRevenue() {
////        double sum = 0;
////        for (Income income : incomes) {
////            sum += income.value;
////        }
////        return sum;
////    }
//
////    public double calculateFreeSpace() {
////        return 0;
////    }
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
