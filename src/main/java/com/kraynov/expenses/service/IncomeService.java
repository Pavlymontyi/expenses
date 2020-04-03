package com.kraynov.expenses.service;

import com.kraynov.expenses.dao.IncomeRepo;
import com.kraynov.expenses.domain.dep.Income;
import com.kraynov.expenses.errorhandling.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IncomeService {
    private final IncomeRepo incomeRepo;

    public IncomeService(IncomeRepo incomeRepo) {
        this.incomeRepo = incomeRepo;
    }

    public Income getById(Long id) throws BusinessException {
        return incomeRepo.findById(id).orElseThrow(() -> new BusinessException("Income was not found by id="+id));
    }

    public void changeIncomeInfo(Long incomeId, Date refillDate, int amount) throws BusinessException {
        Income income = getById(incomeId);
        income.setDate(refillDate);
        income.setValue(amount);
        incomeRepo.save(income);
    }

}
