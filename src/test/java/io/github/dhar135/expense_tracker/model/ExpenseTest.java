package io.github.dhar135.expense_tracker.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseTest {

    @Test
    public void shouldCreateExpenseWithCorrectValues(){
        Expense expense = new Expense();
        expense.setDescription("test");
        expense.setAmount(new BigDecimal(100));
        expense.setDate(LocalDate.now());
        expense.setCategory(ExpenseCategory.GROCERIES);

        assertEquals("test", expense.getDescription());
        assertEquals(new BigDecimal(100), expense.getAmount());
        assertEquals(LocalDate.now(), expense.getDate());
        assertEquals(ExpenseCategory.GROCERIES, expense.getCategory());
    }
}
