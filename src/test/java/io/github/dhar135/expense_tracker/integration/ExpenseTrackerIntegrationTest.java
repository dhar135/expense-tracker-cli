package io.github.dhar135.expense_tracker.integration;

import io.github.dhar135.expense_tracker.expense.model.Expense;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;
import io.github.dhar135.expense_tracker.expense.repository.ExpenseRepository;
import io.github.dhar135.expense_tracker.expense.service.ExpenseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExpenseTrackerIntegrationTest {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @AfterEach
    void tearDown() {
        expenseRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrieveExpense() {
        // Given
        String description = "Integration Test Expense";
        BigDecimal amount = new BigDecimal("150.00");

        // When
        Expense createdExpense = expenseService.createExpense(description, amount);
        Expense retrievedExpense = expenseService.getExpenseById(createdExpense.getId());

        // Then
        assertNotNull(createdExpense);
        assertNotNull(createdExpense.getId());
        assertEquals(description, createdExpense.getDescription());
        assertEquals(amount, createdExpense.getAmount());
        assertEquals(LocalDate.now(), createdExpense.getDate());

        assertNotNull(retrievedExpense);
        assertEquals(createdExpense.getId(), retrievedExpense.getId());
        assertEquals(createdExpense.getDescription(), retrievedExpense.getDescription());
        assertEquals(createdExpense.getAmount(), retrievedExpense.getAmount());
    }

    @Test
    void testUpdateExpense() {
        // Given
        Expense expense = expenseService.createExpense("Original Description", new BigDecimal("100.00"));
        String updatedDescription = "Updated Description";
        BigDecimal updatedAmount = new BigDecimal("200.00");

        // When
        Expense updatedExpense = expenseService.updateExpense(expense.getId(), updatedDescription, updatedAmount);

        // Then
        assertNotNull(updatedExpense);
        assertEquals(expense.getId(), updatedExpense.getId());
        assertEquals(updatedDescription, updatedExpense.getDescription());
        assertEquals(updatedAmount, updatedExpense.getAmount());
    }

    @Test
    void testDeleteExpense() {
        // Given
        Expense expense = expenseService.createExpense("Expense to delete", new BigDecimal("50.00"));
        Long id = expense.getId();

        // When
        expenseService.deleteExpense(id);

        // Then
        Expense deletedExpense = expenseService.getExpenseById(id);
        assertNull(deletedExpense);
    }

    @Test
    void testGetAllExpenses() {
        // Given
        expenseService.createExpense("Expense 1", new BigDecimal("100.00"));
        expenseService.createExpense("Expense 2", new BigDecimal("200.00"));
        expenseService.createExpense("Expense 3", new BigDecimal("300.00"));

        // When
        List<Expense> allExpenses = expenseService.getAllExpenses();

        // Then
        assertNotNull(allExpenses);
        assertEquals(3, allExpenses.size());
    }

    @Test
    void testGetExpensesByCategory() {
        // Given
        Expense expense1 = expenseService.createExpense("Grocery Expense", new BigDecimal("75.50"));
        expense1.setCategory(ExpenseCategory.GROCERIES);
        expenseRepository.save(expense1);

        Expense expense2 = expenseService.createExpense("Utility Expense", new BigDecimal("120.00"));
        expense2.setCategory(ExpenseCategory.UTILITIES);
        expenseRepository.save(expense2);

        Expense expense3 = expenseService.createExpense("Another Grocery", new BigDecimal("45.25"));
        expense3.setCategory(ExpenseCategory.GROCERIES);
        expenseRepository.save(expense3);

        // When
        List<Expense> groceryExpenses = expenseService.getExpensesByCategory(ExpenseCategory.GROCERIES);

        // Then
        assertNotNull(groceryExpenses);
        assertEquals(2, groceryExpenses.size());
        assertTrue(groceryExpenses.stream().allMatch(e -> e.getCategory() == ExpenseCategory.GROCERIES));
    }

    @Test
    void testGetSummary() {
        // Given
        expenseService.createExpense("Expense 1", new BigDecimal("100.00"));
        expenseService.createExpense("Expense 2", new BigDecimal("200.00"));
        expenseService.createExpense("Expense 3", new BigDecimal("300.00"));
        BigDecimal expectedTotal = new BigDecimal("600.00");

        // When
        BigDecimal summary = expenseService.getSummary();

        // Then
        assertNotNull(summary);
        assertEquals(0, expectedTotal.compareTo(summary));
    }

    @Test
    void testGetSummaryByMonth() {
        // Given
        // Create expense for current month
        Expense currentMonthExpense = expenseService.createExpense("Current Month Expense", new BigDecimal("100.00"));

        // Create and update expense for previous month
        Expense previousMonthExpense = expenseService.createExpense("Previous Month Expense", new BigDecimal("200.00"));
        previousMonthExpense.setDate(LocalDate.now().minusMonths(1));
        expenseRepository.save(previousMonthExpense);

        BigDecimal expectedCurrentMonthTotal = new BigDecimal("100.00");

        // When
        BigDecimal currentMonthSummary = expenseService.getSummaryByMonth(LocalDate.now());

        // Then
        assertNotNull(currentMonthSummary);
        assertEquals(0, expectedCurrentMonthTotal.compareTo(currentMonthSummary));
    }
}