package io.github.dhar135.expense_tracker.shell;

import io.github.dhar135.expense_tracker.model.Expense;
import io.github.dhar135.expense_tracker.model.ExpenseCategory;
import io.github.dhar135.expense_tracker.service.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseCommandsTest {

    @Mock
    private ExpenseServiceImpl expenseService;

    @InjectMocks
    private ExpenseCommands expenseCommands;

    private Expense testExpense;

    @BeforeEach
    void setUp() {
        testExpense = new Expense("Test Expense", new BigDecimal("50.00"));
        testExpense.setId(1L);
        testExpense.setDate(LocalDate.now());
        testExpense.setCategory(ExpenseCategory.OTHER);
    }

    @Test
    void addExpense_ShouldReturnSuccessMessage() {
        // Given
        String description = "Test Expense";
        BigDecimal amount = new BigDecimal("50.00");

        when(expenseService.createExpense(description, amount)).thenReturn(testExpense);

        // When
        String result = expenseCommands.addExpense(description, amount, null);

        // Then
        assertTrue(result.contains("Expense added successfully"));
        assertTrue(result.contains("1")); // ID should be in the message

        verify(expenseService, times(1)).createExpense(description, amount);
    }

    @Test
    void addExpense_WithCategory_ShouldReturnSuccessMessage() {
        // Given
        String description = "Test Expense";
        BigDecimal amount = new BigDecimal("50.00");
        ExpenseCategory category = ExpenseCategory.OTHER;

        when(expenseService.createExpense(description, amount, category)).thenReturn(testExpense);

        // When
        String result = expenseCommands.addExpense(description, amount, category);

        // Then
        assertTrue(result.contains("Expense added successfully"));
        assertTrue(result.contains("1")); // ID should be in the message

        verify(expenseService, times(1)).createExpense(description, amount, category);
    }

    @Test
    void updateExpense_WithDescriptionAndAmount_ShouldReturnSuccessMessage() {
        // Given
        Long id = 1L;
        String description = "Updated Expense";
        BigDecimal amount = new BigDecimal("75.00");

        Expense existingExpense = new Expense("Test Expense", new BigDecimal("50.00"));
        existingExpense.setId(id);
        existingExpense.setCategory(ExpenseCategory.OTHER);

        Expense updatedExpense = new Expense(description, amount);
        updatedExpense.setId(id);
        updatedExpense.setCategory(ExpenseCategory.OTHER);

        when(expenseService.getExpenseById(id)).thenReturn(existingExpense);
        when(expenseService.updateExpense(id, description, amount, ExpenseCategory.OTHER)).thenReturn(updatedExpense);

        // When
        String result = expenseCommands.updateExpense(id, description, amount, null);

        // Then
        assertTrue(result.contains("Expense updated successfully"));

        verify(expenseService, times(1)).getExpenseById(id);
        verify(expenseService, times(1)).updateExpense(id, description, amount, ExpenseCategory.OTHER);
    }

    @Test
    void updateExpense_WithNonExistingId_ShouldReturnNotFoundMessage() {
        // Given
        Long id = 99L;
        String description = "Updated Expense";
        BigDecimal amount = new BigDecimal("75.00");

        when(expenseService.getExpenseById(id)).thenReturn(null);

        // When
        String result = expenseCommands.updateExpense(id, description, amount, null);

        // Then
        assertEquals("Expense not found", result);

        verify(expenseService, times(1)).getExpenseById(id);
        verify(expenseService, never()).updateExpense(anyLong(), anyString(), any(BigDecimal.class));
        verify(expenseService, never()).updateExpense(anyLong(), anyString(), any(BigDecimal.class),
                any(ExpenseCategory.class));
    }

    @Test
    void updateExpense_WithCategory_ShouldReturnSuccessMessage() {
        // Given
        Long id = 1L;
        String description = "Updated Expense";
        BigDecimal amount = new BigDecimal("75.00");
        ExpenseCategory category = ExpenseCategory.GROCERIES;

        Expense existingExpense = new Expense("Test Expense", new BigDecimal("50.00"));
        existingExpense.setId(id);
        existingExpense.setCategory(ExpenseCategory.OTHER);

        Expense updatedExpense = new Expense(description, amount, category);
        updatedExpense.setId(id);

        when(expenseService.getExpenseById(id)).thenReturn(existingExpense);
        when(expenseService.updateExpense(id, description, amount, category)).thenReturn(updatedExpense);

        // When
        String result = expenseCommands.updateExpense(id, description, amount, category);

        // Then
        assertTrue(result.contains("Expense updated successfully"));

        verify(expenseService, times(1)).getExpenseById(id);
        verify(expenseService, times(1)).updateExpense(id, description, amount, category);
    }

    @Test
    void deleteExpense_ShouldReturnSuccessMessage() {
        // Given
        Long id = 1L;

        doNothing().when(expenseService).deleteExpense(id);

        // When
        String result = expenseCommands.deleteExpense(id);

        // Then
        assertEquals("Expense deleted successfully", result);

        verify(expenseService, times(1)).deleteExpense(id);
    }

    @Test
    void listExpenses_WithExpenses_ShouldReturnFormattedList() {
        // Given
        List<Expense> expenses = Arrays.asList(testExpense);

        when(expenseService.getAllExpenses()).thenReturn(expenses);

        // When
        String result = expenseCommands.listExpenses();

        // Then
        assertTrue(result.contains("Id"));
        assertTrue(result.contains("Description"));
        assertTrue(result.contains("Amount"));
        assertTrue(result.contains("Test Expense"));
        assertTrue(result.contains("50.00"));

        verify(expenseService, times(1)).getAllExpenses();
    }

    @Test
    void listExpenses_WithNoExpenses_ShouldReturnNotFoundMessage() {
        // Given
        when(expenseService.getAllExpenses()).thenReturn(Collections.emptyList());

        // When
        String result = expenseCommands.listExpenses();

        // Then
        assertEquals("No expenses found", result);

        verify(expenseService, times(1)).getAllExpenses();
    }

    @Test
    void getExpense_WithExistingId_ShouldReturnExpenseDetails() {
        // Given
        Long id = 1L;

        when(expenseService.getExpenseById(id)).thenReturn(testExpense);

        // When
        String result = expenseCommands.getExpense(id);

        // Then
        assertTrue(result.contains("Test Expense"));
        assertTrue(result.contains("50.00"));

        verify(expenseService, times(1)).getExpenseById(id);
    }

    @Test
    void getExpense_WithNonExistingId_ShouldReturnNotFoundMessage() {
        // Given
        Long id = 99L;

        when(expenseService.getExpenseById(id)).thenReturn(null);

        // When
        String result = expenseCommands.getExpense(id);

        // Then
        assertEquals("Expense not found", result);

        verify(expenseService, times(1)).getExpenseById(id);
    }

    @Test
    void getSummary_WithNoMonth_ShouldReturnTotalExpenses() {
        // Given
        BigDecimal total = new BigDecimal("150.00");

        when(expenseService.getSummary()).thenReturn(total);

        // When
        String result = expenseCommands.getSummary(null);

        // Then
        assertTrue(result.contains("Total expenses: $150.00"));

        verify(expenseService, times(1)).getSummary();
    }

    @Test
    void getSummary_WithMonth_ShouldReturnMonthlyExpenses() {
        // Given
        Integer month = 3; // March
        BigDecimal total = new BigDecimal("100.00");

        when(expenseService.getSummaryByMonth(any(LocalDate.class))).thenReturn(total);

        // When
        String result = expenseCommands.getSummary(month);

        // Then
        assertTrue(result.contains("Total expenses for MARCH: $100.00"));

        verify(expenseService, times(1)).getSummaryByMonth(any(LocalDate.class));
    }

    @Test
    void getExpensesByCategory_ShouldReturnCategoryExpenses() {
        // Given
        ExpenseCategory category = ExpenseCategory.OTHER;
        List<Expense> expenses = List.of(testExpense);

        when(expenseService.getExpensesByCategory(category)).thenReturn(expenses);

        // When
        String result = expenseCommands.getExpensesByCategory(category);

        // Then
        assertTrue(result.contains("Test Expense: 50.00"));

        verify(expenseService, times(1)).getExpensesByCategory(category);
    }

    @Test
    void updateExpense_WithOnlyAmount_ShouldPreserveOtherFields() {
        // Given
        Long id = 1L;
        BigDecimal amount = new BigDecimal("75.00");

        // Original expense with description and category
        Expense existingExpense = new Expense("Test Expense", new BigDecimal("50.00"));
        existingExpense.setId(id);
        existingExpense.setCategory(ExpenseCategory.OTHER);

        // Updated expense with same description but new amount
        Expense updatedExpense = new Expense("Test Expense", amount);
        updatedExpense.setId(id);
        updatedExpense.setCategory(ExpenseCategory.OTHER);

        when(expenseService.getExpenseById(id)).thenReturn(existingExpense);
        when(expenseService.updateExpense(id, "Test Expense", amount, ExpenseCategory.OTHER))
                .thenReturn(updatedExpense);

        // When - only update the amount
        String result = expenseCommands.updateExpense(id, null, amount, null);

        // Then
        assertTrue(result.contains("Expense updated successfully"));

        verify(expenseService, times(1)).getExpenseById(id);
        // Verify that the original description and category were preserved
        verify(expenseService, times(1)).updateExpense(id, "Test Expense", amount, ExpenseCategory.OTHER);
    }
}