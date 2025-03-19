package io.github.dhar135.expense_tracker.repository;

import io.github.dhar135.expense_tracker.model.Expense;
import io.github.dhar135.expense_tracker.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE MONTH(e.date) = MONTH(:month)")
    public List<Expense>findByMonth(LocalDate month);

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = YEAR(:year)")
    public List<Expense>findByYear(LocalDate year);

    @Query("SELECT e FROM Expense e WHERE MONTH(e.date) = MONTH(:month) AND YEAR(e.date) = YEAR(:year)")
    public List<Expense>findByMonthAndYear(LocalDate month, LocalDate year);

    public List<Expense>findByCategory(ExpenseCategory category);
}
