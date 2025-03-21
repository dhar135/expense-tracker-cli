package io.github.dhar135.expense_tracker.budget.model;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;

import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    private BigDecimal budgetAmount;

    private ExpenseCategory budgetCategory;

    private Month budgetPeriod;

    public Budget(){}


    public Budget(BigDecimal budgetAmount, ExpenseCategory budgetCategory, Month budgetPeriod) {
        this.budgetAmount = budgetAmount;
        this.budgetCategory = budgetCategory;
        this.budgetPeriod = budgetPeriod;
    }
    
}
