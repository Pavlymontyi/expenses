package com.kraynov.expenses.dao;

import com.kraynov.expenses.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Long> {
}
