package io.github.dhar135.expense_tracker.expense.service;

import io.github.dhar135.expense_tracker.expense.model.Expense;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;
import io.github.dhar135.expense_tracker.expense.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense createExpense(String description, BigDecimal amount) {
        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setDate(LocalDate.now());
        return expenseRepository.save(expense);
    }

    @Override
    public Expense createExpense(String description, BigDecimal amount, ExpenseCategory category) {
        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setDate(LocalDate.now());
        expense.setCategory(category);
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, String description, BigDecimal amount) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense != null) {
            expense.setDescription(description);
            expense.setAmount(amount);
            return expenseRepository.save(expense);
        }
        return null;
    }

    @Override
    public Expense updateExpense(Long id, String description, BigDecimal amount, ExpenseCategory category) {
        Expense expense = expenseRepository.findById(id).orElse(null);
        if (expense != null) {
            expense.setDescription(description);
            expense.setAmount(amount);
            expense.setCategory(category);
            return expenseRepository.save(expense);
        }
        return null;
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getExpensesByCategory(ExpenseCategory category) {
        return expenseRepository.findByCategory(category);
    }

    @Override
    public BigDecimal getSummary() {
        return expenseRepository.findAll()
                .stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getSummaryByMonth(LocalDate month) {
        return expenseRepository.findByMonth(month)
                .stream().map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
