package io.github.dhar135.expense_tracker.budget.service;

import io.github.dhar135.expense_tracker.budget.model.Budget;
import io.github.dhar135.expense_tracker.budget.repository.BudgetRepository;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }


    @Override
    public Budget createBudget(BigDecimal budgetAmount, ExpenseCategory budgetCategory, Month budgetPeriod) {
        return budgetRepository.save(new Budget(budgetAmount, budgetCategory, budgetPeriod));
    }

    @Override
    public Budget updateBudget(Long budgetId, BigDecimal budgetAmount, ExpenseCategory budgetCategory, Month budgetPeriod) {
        Budget budget = budgetRepository.findById(budgetId).orElse(null);
        if (budget != null) {
            budget.setBudgetAmount(budgetAmount);
            budget.setBudgetCategory(budgetCategory);
            budget.setBudgetPeriod(budgetPeriod);
            return budgetRepository.save(budget);
        }
        return null;
    }

    @Override
    public Budget getBudgetById(Long budgetId) {
        return budgetRepository.findById(budgetId).orElse(null);
    }

    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    public void deleteBudget(Long budgetId) {
        budgetRepository.deleteById(budgetId);
    }

    @Override
    public List<Budget> getBudgetsByCategory(ExpenseCategory category) {
        return budgetRepository.findByBudgetCategory(category);
    }

    @Override
    public List<Budget> getBudgetsByPeriod(Month period) {
        return budgetRepository.findByBudgetPeriod(period);
    }

    @Override
    public List<Budget> getBudgetsByCategoryAndPeriod(ExpenseCategory category, Month period) {
        return budgetRepository.findByBudgetCategoryAndBudgetPeriod(category, period);
    }

    @Override
    public BigDecimal getBudgetedAmountByCategory(ExpenseCategory category) {
        return null;
    }

    @Override
    public BigDecimal getBudgetedAmountByPeriod(Month period) {
        return null;
    }

    @Override
    public BigDecimal getBudgetedAmountByCategoryAndPeriod(ExpenseCategory category, Month period) {
        return null;
    }

    @Override
    public BigDecimal getSpentAmountByCategory(ExpenseCategory category) {
        return null;
    }

    @Override
    public BigDecimal getSpentAmountByPeriod(Month period) {
        return null;
    }

    @Override
    public BigDecimal getSpentAmountByCategoryAndPeriod(ExpenseCategory category, Month period) {
        return null;
    }

    @Override
    public BigDecimal getRemainingAmountByCategory(ExpenseCategory category) {
        return null;
    }

    @Override
    public BigDecimal getRemainingAmountByPeriod(Month period) {
        return null;
    }

    @Override
    public BigDecimal getRemainingAmountByCategoryAndPeriod(ExpenseCategory category, Month period) {
        return null;
    }

    @Override
    public BigDecimal getSpentPercentageByCategory(ExpenseCategory category) {
        return null;
    }

    @Override
    public BigDecimal getSpentPercentageByPeriod(Month period) {
        return null;
    }

    @Override
    public BigDecimal getSpentPercentageByCategoryAndPeriod(ExpenseCategory category, Month period) {
        return null;
    }

    @Override
    public BigDecimal getRemainingPercentageByCategory(ExpenseCategory category) {
        return null;
    }

    @Override
    public BigDecimal getRemainingPercentageByPeriod(Month period) {
        return null;
    }

    @Override
    public BigDecimal getRemainingPercentageByCategoryAndPeriod(ExpenseCategory category, Month period) {
        return null;
    }

    @Override
    public BigDecimal getSpentVsBudgetedPercentageByCategory(ExpenseCategory category) {
        return null;
    }

    @Override
    public BigDecimal getSpentVsBudgetedPercentageByPeriod(Month period) {
        return null;
    }

    @Override
    public BigDecimal getSpentVsBudgetedPercentageByCategoryAndPeriod(ExpenseCategory category, Month period) {
        return null;
    }
}
