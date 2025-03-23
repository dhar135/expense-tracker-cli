package io.github.dhar135.expense_tracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Month;

import org.junit.jupiter.api.Test;

import io.github.dhar135.expense_tracker.budget.model.Budget;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;

public class BudgetTest {

    @Test
    void testCreateBudget() {
        // Given
        BigDecimal budgetAmount = new BigDecimal(1010);
        ExpenseCategory expenseCategory = ExpenseCategory.RENT;
        Month january = Month.JANUARY;

        // When
        Budget budget = new Budget(budgetAmount, expenseCategory, january);

        // Then
        assertEquals(budgetAmount, budget.getBudgetAmount());
        assertEquals(expenseCategory, budget.getBudgetCategory());
        assertEquals(january, budget.getBudgetPeriod());

    }
}
