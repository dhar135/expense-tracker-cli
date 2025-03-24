package io.github.dhar135.expense_tracker.service;

import io.github.dhar135.expense_tracker.expense.model.Expense;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;
import io.github.dhar135.expense_tracker.expense.repository.ExpenseRepository;
import io.github.dhar135.expense_tracker.expense.service.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense testExpense1;
    private Expense testExpense2;

    @BeforeEach
    void setUp() {
        testExpense1 = new Expense("Grocery Shopping", new BigDecimal("75.50"));
        testExpense1.setId(1L);
        testExpense1.setDate(LocalDate.now());
        testExpense1.setCategory(ExpenseCategory.GROCERIES);

        testExpense2 = new Expense("Electricity Bill", new BigDecimal("120.00"));
        testExpense2.setId(2L);
        testExpense2.setDate(LocalDate.now().minusDays(5));
        testExpense2.setCategory(ExpenseCategory.UTILITIES);
    }

    @Test
    void createExpense_ShouldSaveAndReturnExpense() {
        // Given
        String description = "Test Expense";
        BigDecimal amount = new BigDecimal("50.00");

        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> {
            Expense savedExpense = invocation.getArgument(0);
            savedExpense.setId(3L);
            return savedExpense;
        });

        // When
        Expense result = expenseService.createExpense(description, amount);

        // Then
        assertNotNull(result);
        assertEquals(description, result.getDescription());
        assertEquals(amount, result.getAmount());
        assertEquals(3L, result.getId());
        assertNotNull(result.getDate());

        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void updateExpense_WithDescriptionAndAmount_ShouldUpdateAndReturnExpense() {
        // Given
        Long id = 1L;
        String newDescription = "Updated Description";
        BigDecimal newAmount = new BigDecimal("100.00");

        when(expenseRepository.findById(id)).thenReturn(Optional.of(testExpense1));
        when(expenseRepository.save(any(Expense.class))).thenReturn(testExpense1);

        // When
        Expense result = expenseService.updateExpense(id, newDescription, newAmount);

        // Then
        assertNotNull(result);
        assertEquals(newDescription, result.getDescription());
        assertEquals(newAmount, result.getAmount());

        verify(expenseRepository, times(1)).findById(id);
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void updateExpense_WithNonExistingId_ShouldReturnNull() {
        // Given
        Long nonExistingId = 99L;
        String newDescription = "Updated Description";
        BigDecimal newAmount = new BigDecimal("100.00");

        when(expenseRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When
        Expense result = expenseService.updateExpense(nonExistingId, newDescription, newAmount);

        // Then
        assertNull(result);

        verify(expenseRepository, times(1)).findById(nonExistingId);
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    @Test
    void deleteExpense_ShouldCallRepositoryDeleteById() {
        // Given
        Long id = 1L;

        doNothing().when(expenseRepository).deleteById(id);

        // When
        expenseService.deleteExpense(id);

        // Then
        verify(expenseRepository, times(1)).deleteById(id);
    }

    @Test
    void getExpenseById_WithExistingId_ShouldReturnExpense() {
        // Given
        Long id = 1L;

        when(expenseRepository.findById(id)).thenReturn(Optional.of(testExpense1));

        // When
        Expense result = expenseService.getExpenseById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(expenseRepository, times(1)).findById(id);
    }

    @Test
    void getAllExpenses_ShouldReturnAllExpenses() {
        // Given
        List<Expense> expectedExpenses = Arrays.asList(testExpense1, testExpense2);

        when(expenseRepository.findAll()).thenReturn(expectedExpenses);

        // When
        List<Expense> result = expenseService.getAllExpenses();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedExpenses, result);

        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void getExpensesByCategory_ShouldReturnExpensesWithSpecifiedCategory() {
        // Given
        ExpenseCategory category = ExpenseCategory.GROCERIES;
        List<Expense> expectedExpenses = List.of(testExpense1);

        when(expenseRepository.findByCategory(category)).thenReturn(expectedExpenses);

        // When
        List<Expense> result = expenseService.getExpensesByCategory(category);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedExpenses, result);

        verify(expenseRepository, times(1)).findByCategory(category);
    }

    @Test
    void getSummary_ShouldReturnTotalAmount() {
        // Given
        List<Expense> expenses = Arrays.asList(testExpense1, testExpense2);
        BigDecimal expectedTotal = new BigDecimal("195.50"); // 75.50 + 120.00

        when(expenseRepository.findAll()).thenReturn(expenses);

        // When
        BigDecimal result = expenseService.getSummary();

        // Then
        assertNotNull(result);
        assertEquals(0, expectedTotal.compareTo(result));

        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void getSummaryByMonth_ShouldReturnTotalAmountForMonth() {
        // Given
        LocalDate month = LocalDate.now();
        List<Expense> expenses = List.of(testExpense1); // Only testExpense1 is in the current month
        BigDecimal expectedTotal = new BigDecimal("75.50");

        when(expenseRepository.findByMonth(month)).thenReturn(expenses);

        // When
        BigDecimal result = expenseService.getSummaryByMonth(month);

        // Then
        assertNotNull(result);
        assertEquals(0, expectedTotal.compareTo(result));

        verify(expenseRepository, times(1)).findByMonth(month);
    }
}