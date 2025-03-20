package io.github.dhar135.expense_tracker.shell;

import io.github.dhar135.expense_tracker.model.Expense;
import io.github.dhar135.expense_tracker.model.ExpenseCategory;
import io.github.dhar135.expense_tracker.service.ExpenseServiceImpl;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ExpenseCommands {

    private final ExpenseServiceImpl expenseService;

    public ExpenseCommands(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
    }

    // Add expense
    @ShellMethod(key = "add", value = "Add an expense")
    public String addExpense(@ShellOption(value = "description", defaultValue = ShellOption.NULL) String description,
            @ShellOption(value = "amount", defaultValue = ShellOption.NULL) BigDecimal amount,
            @ShellOption(value = "category", defaultValue = ShellOption.NULL) ExpenseCategory category) {

        Expense expense;
        if (category != null) {
            expense = expenseService.createExpense(description, amount, category);
        } else {
            expense = expenseService.createExpense(description, amount);
        }
        return "Expense added successfully (Id: " + expense.getId() + ")";
    }

    // Update expense description and amount
    @ShellMethod(key = "update", value = "Update an expense's description and amount")
    public String updateExpense(
            @ShellOption Long id,
            @ShellOption String description,
            @ShellOption BigDecimal amount,
            @ShellOption(defaultValue = ShellOption.NULL) ExpenseCategory category) {

        Expense expense;
        if (category != null) {
            expense = expenseService.updateExpense(id, description, amount, category);
        } else {
            expense = expenseService.updateExpense(id, description, amount);
        }
        return expense != null ? "Expense updated successfully (" + expense + ")" : "Expense not found";
    }

    // Delete expense
    @ShellMethod(key = "delete", value = "Delete an expense")
    public String deleteExpense(@ShellOption Long id) {
        expenseService.deleteExpense(id);
        return "Expense deleted successfully";
    }

    // List all expenses
    @ShellMethod(key = "list", value = "List all expenses.")
    public String listExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        if (expenses.isEmpty()) {
            return "No expenses found";
        }

        String header = String.format("%-5s | %-20s | %-10s | %-10s | %-10s", "Id", "Description", "Amount", "Date",
                "Category");
        String line = "-------------------------------------------------------------";
        String body = expenses.stream()
                .map(exp -> String.format("%-5s | %-20s | %-10s | %-10s | %-10s", exp.getId(), exp.getDescription(),
                        exp.getAmount(), exp.getDate(), exp.getCategory()))
                .collect(Collectors.joining("\n"));
        return header + "\n" + line + "\n" + body;
    }

    // Get Expense by Id
    @ShellMethod(key = "get", value = "Get an expense by Id")
    public String getExpense(@ShellOption Long id) {
        Expense expense = expenseService.getExpenseById(id);
        return expense != null ? expense.toString() : "Expense not found";
    }

    @ShellMethod(key = "summary", value = "Get a summary of all expenses or by month")
    public String getSummary(@ShellOption(value = "month", defaultValue = ShellOption.NULL, help = "Enter integer between 1 - 12" ) Integer month) {
        if (month == null) {
            BigDecimal total = expenseService.getSummary();
            return "# Total expenses: $" + total;
        } else {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, 1);
            BigDecimal total = expenseService.getSummaryByMonth(date);
            String monthName = date.getMonth().toString();
            return "# Total expenses for " + monthName + ": $" + total;
        }
    }

    // Get expenses by category
    @ShellMethod(key = "category", value = "Get expenses by category")
    public String getExpensesByCategory(@ShellOption ExpenseCategory category) {
        return expenseService.getExpensesByCategory(category)
                .stream()
                .map(exp -> exp.getDescription() + ": " + exp.getAmount())
                .collect(Collectors.joining("\n"));
    }
}
