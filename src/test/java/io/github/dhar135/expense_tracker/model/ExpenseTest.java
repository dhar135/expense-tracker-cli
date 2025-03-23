package io.github.dhar135.expense_tracker.model;

import io.github.dhar135.expense_tracker.expense.model.Expense;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseTest {

    @Test
    void testCreateExpense() {
        // Given
        String description = "Groceries";
        BigDecimal amount = new BigDecimal("50.00");
        ExpenseCategory category = ExpenseCategory.GROCERIES;

        // When
        Expense expense = new Expense(description, amount, category);

        // Then
        assertEquals(description, expense.getDescription());
        assertEquals(amount, expense.getAmount());
        assertEquals(category, expense.getCategory());
        assertNull(expense.getId());
    }

    @Test
    void testExpenseSettersAndGetters() {
        // Given
        Expense expense = new Expense();
        String description = "Utility Bill";
        BigDecimal amount = new BigDecimal("100.00");
        LocalDate date = LocalDate.now();
        ExpenseCategory category = ExpenseCategory.UTILITIES;
        Long id = 1L;

        // When
        expense.setId(id);
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setDate(date);
        expense.setCategory(category);

        // Then
        assertEquals(id, expense.getId());
        assertEquals(description, expense.getDescription());
        assertEquals(amount, expense.getAmount());
        assertEquals(date, expense.getDate());
        assertEquals(category, expense.getCategory());
    }
}
