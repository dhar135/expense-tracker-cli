package io.github.dhar135.expense_tracker.repository;

import io.github.dhar135.expense_tracker.model.Expense;
import io.github.dhar135.expense_tracker.model.ExpenseCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ExpenseRepositoryTest {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    TestEntityManager testEntityManager;

}
