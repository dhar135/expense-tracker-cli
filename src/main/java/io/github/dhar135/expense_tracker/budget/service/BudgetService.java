package io.github.dhar135.expense_tracker.budget.service;

import io.github.dhar135.expense_tracker.budget.model.Budget;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

public interface BudgetService {

    public Budget createBudget(BigDecimal budgetAmount, ExpenseCategory budgetCategory, Month budgetPeriod);

    public Budget updateBudget(Long budgetId, BigDecimal budgetAmount, ExpenseCategory budgetCategory, Month budgetPeriod);

    public Budget getBudgetById(Long budgetId);

    public List<Budget> getAllBudgets();

    public void deleteBudget(Long budgetId);

    public List<Budget> getBudgetsByCategory(ExpenseCategory category);

    public List<Budget> getBudgetsByPeriod(Month period);

    public List<Budget> getBudgetsByCategoryAndPeriod(ExpenseCategory category, Month period);

    public BigDecimal getBudgetedAmountByCategory(ExpenseCategory category);

    public BigDecimal getBudgetedAmountByPeriod(Month period);

    public BigDecimal getBudgetedAmountByCategoryAndPeriod(ExpenseCategory category, Month period);

    public BigDecimal getSpentAmountByCategory(ExpenseCategory category);

    public BigDecimal getSpentAmountByPeriod(Month period);

    public BigDecimal getSpentAmountByCategoryAndPeriod(ExpenseCategory category, Month period);

    public BigDecimal getRemainingAmountByCategory(ExpenseCategory category);

    public BigDecimal getRemainingAmountByPeriod(Month period);

    public BigDecimal getRemainingAmountByCategoryAndPeriod(ExpenseCategory category, Month period);

    public BigDecimal getSpentPercentageByCategory(ExpenseCategory category);

    public BigDecimal getSpentPercentageByPeriod(Month period);

    public BigDecimal getSpentPercentageByCategoryAndPeriod(ExpenseCategory category, Month period);

    public BigDecimal getRemainingPercentageByCategory(ExpenseCategory category);

    public BigDecimal getRemainingPercentageByPeriod(Month period);

    public BigDecimal getRemainingPercentageByCategoryAndPeriod(ExpenseCategory category, Month period);

    public BigDecimal getSpentVsBudgetedPercentageByCategory(ExpenseCategory category);

    public BigDecimal getSpentVsBudgetedPercentageByPeriod(Month period);

    public BigDecimal getSpentVsBudgetedPercentageByCategoryAndPeriod(ExpenseCategory category, Month period);
}
