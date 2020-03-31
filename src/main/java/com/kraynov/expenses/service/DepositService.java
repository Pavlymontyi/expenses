package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.CardRepo;
import com.kraynov.expenses.dao.DepositRepo;
import com.kraynov.expenses.dao.IncomeRepo;
import com.kraynov.expenses.dao.PersonRepo;
import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class DepositService {
    private final PersonRepo personRepo;
    private final CardRepo cardRepo;
    private final DepositRepo depositRepo;
    private final IncomeRepo incomeRepo;

    public DepositService(PersonRepo personRepo, CardRepo cardRepo, DepositRepo depositRepo, IncomeRepo incomeRepo) {
        this.personRepo = personRepo;
        this.cardRepo = cardRepo;
        this.depositRepo = depositRepo;
        this.incomeRepo = incomeRepo;
    }

    public Iterable<Person> getDepositInfoForAll() {
        return personRepo.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepo.findById(id).orElseThrow(() -> new RuntimeException("person was not found by id="+id));
    }

    public void openNewCard(Long personId, String cardOpenDate, String bankName, Integer balance) {
        Card newCard = new Card(getPersonById(personId), bankName, cardOpenDate);
        newCard.setBalance(balance == null ? 0 : balance);
        cardRepo.save(newCard);
    }

    public void closeCard(Long id) {
        cardRepo.deleteById(id);
    }

    public Card getCardById(Long id) {
        return cardRepo.findById(id).orElseThrow(() -> new RuntimeException("card was not found by id="+id));
    }

    public void addMoneyToCard(Long cardId, Integer newMoney) {
        Card card = getCardById(cardId);
        card.setBalance(card.getBalance()+newMoney);
        cardRepo.save(card);
    }

    public void openNewDeposit(Card card, double percent, String openDate, int duration, int initialAmount) throws BusinessException {
        if (card.getBalance() <= initialAmount) {
            throw new BusinessException("Card doesn't have sufficient money amount to open deposit");
        }
        Deposit deposit = new Deposit(card, percent, openDate, duration);
        deposit = depositRepo.save(deposit);
        Income initIncome = new Income(initialAmount, openDate, deposit, card.getOwner());
        incomeRepo.save(initIncome);
        card.setBalance(card.getBalance()-initialAmount);
        cardRepo.save(card);
    }

//    /**
//     * Суммирует деньги, присутствующие на данный момент на карте
//     * @param card карта
//     * @return сумму на карте
//     */
//    public double calculateCurrentTotal(Card card) {
//        return card.getDeposits()
//                .stream()
//                .flatMap(t -> t.getIncomes().stream())
//                .map(Income::getValue)
//                .reduce(0, Integer::sum);
//    }
//
//    /**
//     * Суммирует ожидаемые доходы от процентов по всем активным вкладам
//     * @param card карта
//     * @return сумму доходов по текущим вкладам карты
//     */
//    public double calculateExpectedRevenue(Card card) {
//        return card.getDeposits()
//                .stream()
//                .flatMap(t -> t.getIncomes().stream())
//                .map(Income::getRevenue)
//                .reduce((double) 0, Double::sum);
//    }
//
////    public double calculateTotal(Card card, boolean withRevenue) {
////        return card.getDeposits()
////                .stream()
////                .flatMap(t -> t.getIncomes().stream())
////                .map(income -> withRevenue ? income.getValue()+income.getRevenue() : income.getValue())
////                .reduce((double) 0, Double::sum);
////    }
//
//    public double calculateFreeSpace(Card card) {
//        return 1_400_000 - calculateExpectedRevenue(card) - calculateCurrentTotal(card);
//    }
}
