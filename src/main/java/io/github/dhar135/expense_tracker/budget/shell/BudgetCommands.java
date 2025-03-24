package io.github.dhar135.expense_tracker.budget.shell;

import io.github.dhar135.expense_tracker.budget.model.Budget;
import io.github.dhar135.expense_tracker.budget.service.BudgetServiceImpl;
import io.github.dhar135.expense_tracker.expense.model.ExpenseCategory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;
import java.time.Month;
import java.util.stream.Collectors;

@ShellComponent
public class BudgetCommands {

    private final BudgetServiceImpl budgetService;

    public BudgetCommands(BudgetServiceImpl budgetService) {
        this.budgetService = budgetService;
    }

    // create budget
    @ShellMethod(key = "create-budget", value = "Create a budget")
    public String createBudget(@ShellOption(value = "amount", help = "Add an amount greater than zero.") BigDecimal amount,
                               @ShellOption(value = "category", help = "Specify the budget category") ExpenseCategory category,
                               @ShellOption(value = "period", help = "Specify the Month (1-12 || JANUARY ... DECEMBER)") Month period) {

        Budget budget = budgetService.createBudget(amount, category, period);
        return "Budget created successfully: " + budget.getBudgetId();
    }

    // update budget
    @ShellMethod(key = "update-budget", value = "Update a budget")
    public String updateBudget(@ShellOption(value = "id", defaultValue = ShellOption.NULL, help = "Specify the budget id") Long id,
                               @ShellOption(value = "amount", defaultValue = ShellOption.NULL, help = "Add an amount greater than zero.") BigDecimal amount,
                               @ShellOption(value = "category", defaultValue = ShellOption.NULL, help = "Specify the budget category") ExpenseCategory category,
                               @ShellOption(value = "period", defaultValue = ShellOption.NULL, help = "Specify the Month (1-12 || JANUARY ... DECEMBER)") Month period) {

        Budget budget = budgetService.getBudgetById(id);
        if (budget == null) {
            return "Budget not found";
        }

        BigDecimal budgetAmount = (amount != null) ? amount : budget.getBudgetAmount();
        ExpenseCategory budgetCategory = (category != null) ? category : budget.getBudgetCategory();
        Month budgetPeriod = (period != null) ? period : budget.getBudgetPeriod();

        budget = budgetService.updateBudget(id, budgetAmount, budgetCategory, budgetPeriod);

        return "Budget updated successfully: " + budget.getBudgetId();
    }

    // delete budget
    @ShellMethod(key = "delete-budget", value = "Delete a budget")
    public String deleteBudget(@ShellOption(value = "id", help = "Specify the budget id") Long id) {

        budgetService.deleteBudget(id);
        return "Budget deleted successfully - Id:" + id;
    }

    // get budget by id
    @ShellMethod(key = "get-budget", value = "Get a budget by id")
    public String getBudget(@ShellOption(value = "id", help = "Specify the budget id") Long id) {

        Budget budget = budgetService.getBudgetById(id);
        return budget != null ? budget.toString() : null;
    }

    // get all budgets
    @ShellMethod(key = "get-all-budgets", value = "Get all budgets")
    public String getAllBudgets() {

        if (budgetService.getAllBudgets().isEmpty()) {
            return "No budgets found";
        }

        return budgetService.getAllBudgets().stream().map(budget -> budget.toString()).collect(Collectors.joining("\n"));
    }

    // get budgets by category
    @ShellMethod(key = "get-budgets-by-category", value = "Get budgets by category")
    public String getBudgetsByCategory(@ShellOption(value = "category", help = "Specify the budget category") ExpenseCategory category) {

        if (budgetService.getBudgetsByCategory(category).isEmpty()) {
            return "No budgets found";
        }

        return budgetService.getBudgetsByCategory(category).stream().map(budget -> budget.toString()).collect(Collectors.joining("\n"));
    }
}
