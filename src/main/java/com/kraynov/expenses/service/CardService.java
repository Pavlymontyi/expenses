package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.CardRepo;
import com.kraynov.expenses.domain.dep.Card;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public void closeCard(Card card) throws BusinessException {
        if (card.getDeposits().stream().anyMatch(Deposit::getActive)) {
            throw new BusinessException("Card has deposits. Need to close them firstly.");
        }

        //todo: добавить проверку на то что на карте пусто. Если есть ДС, то заставить пользователя перевести их на другую карту

        card.setActive(false);
        //todo: проверить что
        //card.getDeposits().forEach(d -> d.setCard(null));
//        card.setDeposits(new HashSet<>());
//        card.setBalance(0);
        cardRepo.save(card);
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
     * @return сумму вкладов на карте
     */
    public Integer getTotal(Card card) {
        return card.getDeposits().stream()
                .filter(Deposit::getActive)
                .flatMap(dep -> dep.getIncomes().stream())
                .mapToInt(Income::getValue)
                .sum();
    }

    /**
     * Суммирует ожидаемые доходы от процентов по всем активным вкладам на карте.
     * Внимание: вычисление не привязано к клиенту банка, а только к карте.
     * @param card карта
     * @return сумму доходов по текущим вкладам карты
     */
    public Double getRevenue(Card card) {
        return card.getDeposits().stream()
                .filter(Deposit::getActive)
                .flatMap(dep -> dep.getIncomes().stream())
                .mapToDouble(Income::getRevenue)
                .sum();
    }

    /**
     * Вычисление свободного места на карте, сколько еще можно положить.
     * Расчет производится следующим образом - от застрахованной в АСВ суммы вычитается сумма ДС с активных на данный
     * момент вкладов, а также вычитается ожидаемый процентный доход с этих вкладов.
     *
     * @param card карта
     * @return сумма, которую еще можно положить на данный вклад, не превышая застрахованную в АСВ сумму
     */
    public double calculateFreeSpace(Card card) {
        return 1_400_000 - getRevenue(card) - getTotal(card);
    }

    /**
     * Формируем диапазон дат расчетного периода, отталкиваясь от текущей даты
     * @param card карта
     * @return диапазон дат расчетного периода
     */
    public Date[] calculateDateRange(Card card) {
        return calculateDateRange(card, Calendar.getInstance());
    }

    /**
     * Формируем диапазон дат расчетного периода, отталкиваясь от указанной в параметре даты
     * @param card карта
     * @param when дата, на момент которой расчитываем расчетный период
     * @return диапазон дат расчетного периода
     */
    public Date[] calculateDateRange(Card card, Calendar when) {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(card.getOpenDate());

        int i = 0;
        while (endDate.before(when)) {
            endDate.setTime(card.getOpenDate());
            endDate.add(Calendar.MONTH, ++i);
            endDate.add(Calendar.DAY_OF_MONTH, -1);
        }

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(endDate.getTime());
        startDate.add(Calendar.MONTH, -1);
        startDate.add(Calendar.DAY_OF_MONTH, 1);

        return new Date[]{startDate.getTime(), endDate.getTime()};
    }

    public long getRemainingDaysCount(Card card) {
        long diffInMillies = calculateDateRange(card)[1].getTime() - Calendar.getInstance().getTime().getTime();
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)+1;
    }
}
