package io.github.dhar135.expense_tracker.repository;

import io.github.dhar135.expense_tracker.model.Expense;
import io.github.dhar135.expense_tracker.model.ExpenseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExpenseRepository expenseRepository;

    private Expense januaryExpense;
    private Expense februaryExpense;
    private Expense marchExpense;
    private Expense groceryExpense;
    private Expense utilityExpense;

    @BeforeEach
    void setUp() {
        // Create expenses for different months
        januaryExpense = new Expense("January Rent", new BigDecimal("1000.00"));
        januaryExpense.setDate(LocalDate.of(2023, 1, 15));
        januaryExpense.setCategory(ExpenseCategory.RENT);
        entityManager.persist(januaryExpense);

        februaryExpense = new Expense("February Rent", new BigDecimal("1000.00"));
        februaryExpense.setDate(LocalDate.of(2023, 2, 15));
        februaryExpense.setCategory(ExpenseCategory.RENT);
        entityManager.persist(februaryExpense);

        marchExpense = new Expense("March Rent", new BigDecimal("1050.00"));
        marchExpense.setDate(LocalDate.of(2023, 3, 15));
        marchExpense.setCategory(ExpenseCategory.RENT);
        entityManager.persist(marchExpense);

        // Create expenses for different categories
        groceryExpense = new Expense("Weekly Groceries", new BigDecimal("85.75"));
        groceryExpense.setDate(LocalDate.of(2023, 3, 10));
        groceryExpense.setCategory(ExpenseCategory.GROCERIES);
        entityManager.persist(groceryExpense);

        utilityExpense = new Expense("Electric Bill", new BigDecimal("120.50"));
        utilityExpense.setDate(LocalDate.of(2023, 3, 5));
        utilityExpense.setCategory(ExpenseCategory.UTILITIES);
        entityManager.persist(utilityExpense);

        entityManager.flush();
    }

    @Test
    void findByMonth_ShouldReturnExpensesForSpecifiedMonth() {
        // Given
        LocalDate marchDate = LocalDate.of(2023, 3, 1);

        // When
        List<Expense> marchExpenses = expenseRepository.findByMonth(marchDate);

        // Then
        assertEquals(3, marchExpenses.size());
        assertTrue(marchExpenses.contains(marchExpense));
        assertTrue(marchExpenses.contains(groceryExpense));
        assertTrue(marchExpenses.contains(utilityExpense));
    }

    @Test
    void findByYear_ShouldReturnExpensesForSpecifiedYear() {
        // Given
        LocalDate year2023 = LocalDate.of(2023, 1, 1);

        // When
        List<Expense> expenses2023 = expenseRepository.findByYear(year2023);

        // Then
        assertEquals(5, expenses2023.size());
        assertTrue(expenses2023.contains(januaryExpense));
        assertTrue(expenses2023.contains(februaryExpense));
        assertTrue(expenses2023.contains(marchExpense));
        assertTrue(expenses2023.contains(groceryExpense));
        assertTrue(expenses2023.contains(utilityExpense));
    }

    @Test
    void findByMonthAndYear_ShouldReturnExpensesForSpecifiedMonthAndYear() {
        // Given
        LocalDate february2023 = LocalDate.of(2023, 2, 1);

        // When
        List<Expense> february2023Expenses = expenseRepository.findByMonthAndYear(february2023, february2023);

        // Then
        assertEquals(1, february2023Expenses.size());
        assertTrue(february2023Expenses.contains(februaryExpense));
    }

    @Test
    void findByCategory_ShouldReturnExpensesWithSpecifiedCategory() {
        // Given
        ExpenseCategory rentCategory = ExpenseCategory.RENT;

        // When
        List<Expense> rentExpenses = expenseRepository.findByCategory(rentCategory);

        // Then
        assertEquals(3, rentExpenses.size());
        assertTrue(rentExpenses.contains(januaryExpense));
        assertTrue(rentExpenses.contains(februaryExpense));
        assertTrue(rentExpenses.contains(marchExpense));

        // Test another category
        ExpenseCategory groceryCategory = ExpenseCategory.GROCERIES;
        List<Expense> groceryExpenses = expenseRepository.findByCategory(groceryCategory);

        assertEquals(1, groceryExpenses.size());
        assertTrue(groceryExpenses.contains(groceryExpense));
    }
}
