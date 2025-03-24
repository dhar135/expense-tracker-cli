package io.github.dhar135.expense_tracker.expense.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import io.github.dhar135.expense_tracker.expense.model.Expense;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;

public interface ExpenseService {

    // Save Expense by description, amount
    Expense createExpense(String description, BigDecimal amount);

    // Save Expense by description, amount, and category
    Expense createExpense(String description, BigDecimal amount, ExpenseCategory category);

    // Update Expense
    Expense updateExpense(Long id, String description, BigDecimal amount);

    // Update Expense with category
    Expense updateExpense(Long id, String description, BigDecimal amount, ExpenseCategory category);

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
