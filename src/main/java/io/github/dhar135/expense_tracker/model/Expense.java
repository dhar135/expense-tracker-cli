package io.github.dhar135.expense_tracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    public Expense() {
    }

    public Expense(String description, BigDecimal amount) {
        this.description = description;
        this.amount = amount;
    }

    public Expense(String description, BigDecimal amount, ExpenseCategory category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

}
