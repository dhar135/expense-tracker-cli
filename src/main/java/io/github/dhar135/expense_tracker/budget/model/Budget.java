package io.github.dhar135.expense_tracker.budget.model;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;

import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"budgetCategory", "budgetPeriod"})
})
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    private BigDecimal budgetAmount;

    private ExpenseCategory budgetCategory;

    private Month budgetPeriod;

    public Budget(BigDecimal budgetAmount, ExpenseCategory budgetCategory, Month budgetPeriod) {
        setBudgetAmount(budgetAmount);
        setBudgetCategory(budgetCategory);
        setBudgetPeriod(budgetPeriod);
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        if (budgetAmount == null || budgetAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Budget amount must be greater than 0");
        }
        this.budgetAmount = budgetAmount;
    }

    public void setBudgetCategory(ExpenseCategory budgetCategory) {
        if (budgetCategory == null) {
            throw new IllegalArgumentException("Budget category cannot be null");
        }
        this.budgetCategory = budgetCategory;
    }

    public void setBudgetPeriod(Month budgetPeriod) {
        if (budgetPeriod == null) {
            throw new IllegalArgumentException("Budget period cannot be null");
        }
        this.budgetPeriod = budgetPeriod;
    }
}