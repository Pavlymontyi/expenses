package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.IncomeRepo;
import com.kraynov.expenses.domain.dep.Deposit;
import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.domain.dep.Person;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class IncomeService {
    private final IncomeRepo incomeRepo;

    public IncomeService(IncomeRepo incomeRepo) {
        this.incomeRepo = incomeRepo;
    }

    public Income getById(Long id) throws BusinessException {
        return incomeRepo.findById(id).orElseThrow(() -> new BusinessException("Income was not found by id=" + id));
    }

    public void changeIncomeInfo(Long incomeId, Date refillDate, int amount, Person owner) throws BusinessException {
        Income income = getById(incomeId);
        income.setDate(refillDate);
        income.setValue(amount);
        income.setOwner(owner);
        incomeRepo.save(income);
    }

    /**
     * Вычисление суммы: пополнение вклада + набежавшие по нему проценты
     * @param income пополнение вклада
     * @return пополнение вклада + проценты
     */
    public double calculateTotal(Income income) {
        return income.getValue() + calculateRevenue(income);
    }

    /**
     * Вычисление дохода по конкретному пополнению на вклад
     * @param income пополнение на вклад
     * @return сумма дохода по данному
     */
    public double calculateRevenue(Income income) {
        Deposit deposit = income.getDeposit();
        LocalDate depositEndDate = DateUtils.asLocalDate(deposit.getEndDate());
        LocalDate incomeAdditionDate = DateUtils.asLocalDate(income.getDate());
        long diff = ChronoUnit.DAYS.between(incomeAdditionDate, depositEndDate);

        return deposit.getPercent() / 100 * income.getValue() * diff / 365;
    }

    /**
     * Получение пояснения по алгоритму вычисления дохода по конкретному пополнению (see {@link #calculateRevenue})
     * @param income пополнение на вклад
     * @return пояснение по доходу
     */
    public String getRevenueClarification(Income income) {
        Deposit deposit = income.getDeposit();
        LocalDate depositEndDate = DateUtils.asLocalDate(deposit.getEndDate());
        LocalDate incomeAdditionDate = DateUtils.asLocalDate(income.getDate());
        long diff = ChronoUnit.DAYS.between(incomeAdditionDate, depositEndDate);
        return income.getValue() + "*" + (deposit.getPercent() / 100) + "*" + diff + "/" + "365";
    }
}
