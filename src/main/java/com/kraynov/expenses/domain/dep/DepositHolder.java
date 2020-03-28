package com.kraynov.expenses.domain.dep;

import com.kraynov.expenses.domain.dep.card.Card;
import lombok.Data;

import java.util.List;

@Data
public class DepositHolder {

    private List<Person> persons;
    private List<Card> cards;
    private List<Deposit> deposits;
    private List<Deposit> incomes;

   // public

}
