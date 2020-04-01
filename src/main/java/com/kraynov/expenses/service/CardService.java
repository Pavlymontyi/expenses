package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.CardRepo;
import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CardService {
    private final CardRepo cardRepo;
    private final DepositService depositService;

    public CardService(CardRepo cardRepo, DepositService depositService) {
        this.cardRepo = cardRepo;
        this.depositService = depositService;
    }

    public Iterable<Card> getAllCards() {
        return cardRepo.findAll();
    }

    public void openNewCard(Person person, Date cardOpenDate, String bankName, Integer balance) throws BusinessException {
        Card newCard = new Card(person, bankName, cardOpenDate);
        newCard.setBalance(balance == null ? 0 : balance);
        cardRepo.save(newCard);
    }

    public Card getCardById(Long id) throws BusinessException {
        return cardRepo.findById(id).orElseThrow(() -> new BusinessException("Card was not found by id=" + id));
    }

    public void deleteCard(Long id) {
        cardRepo.deleteById(id);
    }

    public void closeCard(Card card) {
        //todo: добавить проверку на то что на карте пусто. Если есть ДС, то заставить пользователя перевести их на другую карту
//        card.getDeposits().forEach(depositService::delete);
//        card.setDeposits(new HashSet<>());
//        card.setBalance(0);
//        cardRepo.save(card);
    }

    public void addMoneyToCard(Long cardId, Integer newMoney) throws BusinessException {
        Card card = getCardById(cardId);
        card.setBalance(card.getBalance() + newMoney);
        cardRepo.save(card);
    }


    /**
     * Суммирует деньги, присутствующие на данный момент на карте.
     * Полезно, когда нужно проверить нарушение ограничений суммы в АСВ.
     * @param card карта
     * @return сумму на карте
     */
    public double calculateCurrentTotal(Card card) {
        return card.getDeposits()
                .stream()
                .flatMap(t -> t.getIncomes().stream())
                .map(Income::getValue)
                .reduce(0, Integer::sum);
    }


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
//    public double calculateFreeSpace(Card card) {
//        return 1_400_000 - calculateExpectedRevenue(card) - calculateCurrentTotal(card);
//    }

}
