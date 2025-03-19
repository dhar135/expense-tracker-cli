package io.github.dhar135.expense_tracker.service;

import io.github.dhar135.expense_tracker.model.Expense;
import io.github.dhar135.expense_tracker.model.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    // Save Expense by description, amount
    Expense createExpense(String description, BigDecimal amount);

    // Update Expense
    Expense updateExpense(Long id, String description, BigDecimal amount);
    Expense updateExpense(Long id, String description);
    Expense updateExpense(Long id, BigDecimal amount);

    // Delete Expense
    void deleteExpense(Long id);

    // Get Expense by ID
    Expense getExpenseById(Long id);

    // Get All Expenses
    List<Expense> getAllExpenses();

    // Get Expenses by Category
    List<Expense> getExpensesByCategory(ExpenseCategory category);

    // View Summary of Expenses
    BigDecimal getSummary();

    // View Summary of Expenses by Month
    BigDecimal getSummaryByMonth(LocalDate month);
}
