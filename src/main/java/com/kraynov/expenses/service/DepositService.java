package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.CardRepo;
import com.kraynov.expenses.dao.DepositRepo;
import com.kraynov.expenses.dao.IncomeRepo;
import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class DepositService {
    private final CardRepo cardRepo;
    private final DepositRepo depositRepo;
    private final IncomeRepo incomeRepo;

    public DepositService(CardRepo cardRepo, DepositRepo depositRepo, IncomeRepo incomeRepo) {
        this.cardRepo = cardRepo;
        this.depositRepo = depositRepo;
        this.incomeRepo = incomeRepo;
    }

    public void openNewDeposit(Card card, double percent, String openDate, int duration, int initialAmount) throws BusinessException {
        if (card.getBalance() <= initialAmount) {
            throw new BusinessException("Card doesn't have sufficient money amount to open deposit");
        }
        Deposit deposit = new Deposit(card, percent, openDate, duration);
        deposit = depositRepo.save(deposit);
        Income initIncome = new Income(initialAmount, openDate, deposit, card.getOwner());
        incomeRepo.save(initIncome);
        card.setBalance(card.getBalance() - initialAmount);
        cardRepo.save(card);
    }

    public Deposit getDepositById(Long id) throws BusinessException {
        return depositRepo.findById(id).orElseThrow(() -> new BusinessException("Deposit was not found by id=" + id));
    }

    public int calculateTotal(Deposit deposit) {
        return deposit.getIncomes().stream().mapToInt(Income::getValue).sum();
    }

    public void refillDeposit(Deposit deposit, Card card, String refillDate, int refillAmount) throws BusinessException {
        if (refillAmount > card.getBalance()) {
            throw new BusinessException("Card doesn't have sufficient money amount to refill deposit");
        }

        //todo: сделать выбор человека-владельца дс вносимых на вклад - до тех пор сначала пополняем карту, а затем с нее пополняем вклад
        Income initIncome = new Income(refillAmount, refillDate, deposit, card.getOwner());
        incomeRepo.save(initIncome);
        card.setBalance(card.getBalance() - refillAmount);
        cardRepo.save(card);
    }

    public void closeDeposit(Deposit deposit, Integer factPercentAmount) {
        deposit.setActive(false);
        depositRepo.save(deposit);
        Card card = deposit.getCard();
        card.setBalance(card.getBalance() + calculateTotal(deposit) + factPercentAmount);
        cardRepo.save(card);
    }

    public void delete(Deposit deposit) {
        incomeRepo.deleteAll(deposit.getIncomes());
        depositRepo.delete(deposit);
    }
}
