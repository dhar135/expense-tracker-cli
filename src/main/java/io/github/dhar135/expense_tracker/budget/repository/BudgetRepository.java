package io.github.dhar135.expense_tracker.budget.repository;

import java.time.Month;
import java.util.List;

import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.dhar135.expense_tracker.budget.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    public List<Budget> findByBudgetPeriod(Month month);

    public List<Budget> findByBudgetCategory(ExpenseCategory category);

    public List<Budget> findByBudgetCategoryAndBudgetPeriod(ExpenseCategory category, Month month);
}
