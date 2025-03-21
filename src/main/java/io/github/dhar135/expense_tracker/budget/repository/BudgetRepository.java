package io.github.dhar135.expense_tracker.budget.repository;

import java.time.Month;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.dhar135.expense_tracker.budget.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long>{

    public Budget findByMonth(Month month);
}
